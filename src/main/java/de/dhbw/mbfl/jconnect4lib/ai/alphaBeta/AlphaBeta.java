/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.BoardRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleColumnsPattern;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleRowsPattern;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
public class AlphaBeta {
    private final BoardRater rater;
    private final NextTurnsComputer nextTurnsGenerator;
    private final int maxAbsoluteDepth;
    private long ratedBoards;
    private long cutOffs;

    private AlphaBeta(BoardRater rater, NextTurnsComputer nextTurnsComputer, int maxAbsoluteDepth) {
        this.rater = rater;

        if (nextTurnsComputer == null) nextTurnsComputer = new DefaultNextTurnsComputer();
        this.nextTurnsGenerator = nextTurnsComputer;
        
        this.maxAbsoluteDepth = maxAbsoluteDepth;
        
        this.ratedBoards = 0;
        this.cutOffs = 0;
    }

    public static AlphaBetaResult findBestTurn(Board currentBoard, int foresight, BoardRater rater) {
        return findBestTurn(currentBoard, foresight, rater, null);
    }
    
    public static AlphaBetaResult findBestTurn(Board currentBoard, int foresight, BoardRater rater, NextTurnsComputer nextTurnsComputer) {
        int currentDepth = currentBoard.getTurnCount();
        int maxPossibleForesight = Size.BOARD.size() - currentDepth;
        if (foresight > maxPossibleForesight) foresight = maxPossibleForesight;
        int maxDepth = currentDepth + foresight; //Actually Size.Board.size()-1 is the last playable level, but Size.Board.size() is needed for evaluating that turn
        
        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) throw new IllegalArgumentException("The game associated with the given board is already over.");

        AlphaBeta alg = new AlphaBeta(rater, nextTurnsComputer, maxDepth);

        long startTime = System.nanoTime();

        AlphaBetaResult result = alg.alphaBeta(currentBoard, currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);

        if (result.gameCanBeWonBy() != null) {
            log("Game can be won by " + result.gameCanBeWonBy().getDesc());
        }
        else {
            log("Not yet sure who will win...");
        }

        if (result.getComputedTurn() == null) {
            //No turn has been found, instead of taken some (bad) turn try again with lower foresight
            //With this the algorithm is making a bet on mistakes to be done by the opponent
            //This will go down to foresight = 1, then the algorithm won't even detect direct win situations of the opponent anymore,
            //so he will finally suggest a next turn - because he knows, he will loose whatever turn he decides to do (because foresight = 2 found no turn this means,
            //that every turn of the ai will be followed by a leading-to-win turn of the opponent (On the supposition that the opponent will notice he can win with his next turn))
            result = findBestTurn(currentBoard, foresight - 1, rater, nextTurnsComputer);
        }

        long duration = System.nanoTime() - startTime;

        log("Depth: " + maxDepth);
        log("Duration: " + duration / 10E5f + "ms");
        log("Rated boards: " + alg.ratedBoards);
        log("CutOffs: " + alg.cutOffs);
        log("Foresight: " + foresight);
        log("Proposed turn:");
        log(result.toString());
        
        return result;
    } 
    
    private AlphaBetaResult alphaBeta(Board currentBoard, int currentDepth, int alpha, int beta) {
        if (currentDepth == this.maxAbsoluteDepth) {
            int value = this.rater.rate(currentBoard);
            this.ratedBoards++;
            return new AlphaBetaResult(null, value, null);
        }

        LinkedList<Board> possibleNextBoards = this.nextTurnsGenerator.computeNextTurns(currentBoard);

        if (possibleNextBoards.isEmpty()) {
            //This is only called if currentDepth is below this.maxAbsoluteDepth
            //So the only reason left why there are no next boards is that the game is over after this turn (because the BoardGenerator returns an empty list if game is over)
            //If the game is over this is due to the last turn - we do not have to rate the board
            //We just have to check whether the game is drawn or won - this is done by BoardRater
            int value = this.rater.rate(currentBoard);
            this.ratedBoards++;
            return new AlphaBetaResult(null, value, null);
        }

        //Suggest the first item in the list of possible next boards in case no turn is found (i. e. all found turns are equal to the initial value of max)
        Board bestNextBoard = null;//possibleNextBoards.getFirst();
        AlphaBetaResult bestNextTurn = null;

        //if maximize
        //This also means, that (we expect that human player always starts right now) values of the AI
        //are better the lower the number is and vice versa for the human player
        if ((currentDepth % 2) == 0) {
            int max = alpha;
            for (Board b : possibleNextBoards) {
                AlphaBetaResult result = alphaBeta(b, currentDepth + 1, max, beta);

                if (result.getValue() > max) {
                    max = result.getValue();
                    bestNextTurn = result;
                    bestNextBoard = b;
                }

                if (max >= beta) {  //In case this turn achieves a higher score than the best
                                                                //value found by the minimizer node above all further, possible Board
                                                                //derivations will be skipped because the minimizer will the game never reach this situation.
                                                                //This also includes win situations!
                    this.cutOffs++;
                    break;
                }
            }
            return new AlphaBetaResult((bestNextBoard == null) ? null : bestNextBoard.getLastTurn(), max, bestNextTurn);
        }

        //if !maximize
        int min = beta;
        for (Board b : possibleNextBoards) {
            AlphaBetaResult result = alphaBeta(b, currentDepth + 1, alpha, min);

            if (result.getValue() < min) {
                min = result.getValue();
                bestNextTurn = result;
                bestNextBoard = b;
            }

            if (min <= alpha) {
                this.cutOffs++;
                break;
            }
        }
        return new AlphaBetaResult((bestNextBoard == null) ? null : bestNextBoard.getLastTurn(), min, bestNextTurn);
    }

    private static void log(String msg) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
        String date = dateFormatter.format(new Date());

        msg = date + ": " + msg;

        System.out.println("-> " + msg);
    }
}
