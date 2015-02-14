/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

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
    
    private static FileWriter logFile;

    public static void main(String[] args) throws IOException{
        logFile = new FileWriter("alphaBeta.log");
        
        for (String levelToCheck : args) {
            Board currentBoard = new Board();
            AlphaBeta.findBestTurn(currentBoard, Integer.parseInt(levelToCheck));
        }
        
        logFile.close();
    }
    
    private AlphaBeta(BoardRater rater, NextTurnsComputer nextTurnsComputer, int maxAbsoluteDepth) {
        if (rater == null) throw new IllegalArgumentException("AlphaBeta needs a BoardRater-implementation!");
        this.rater = rater;
        
        if (nextTurnsComputer == null) nextTurnsComputer = new DefaultNextTurnsComputer();
        this.nextTurnsGenerator = nextTurnsComputer;
        
        this.maxAbsoluteDepth = maxAbsoluteDepth;
        
        this.ratedBoards = 0;
        this.cutOffs = 0;
    }

    public static AlphaBetaResult findBestTurn(Board currentBoard, int maxAbsoluteDepth) {
        return findBestTurn(currentBoard, maxAbsoluteDepth, null, null);
    }
    
    public static AlphaBetaResult findBestTurn(Board currentBoard, int foresight, BoardRater rater, NextTurnsComputer nextTurnsComputer) {        
        int currentDepth = currentBoard.getTurnCount();
        int maxDepth = currentDepth + foresight;
        if (maxDepth > Size.BOARD.size()) maxDepth = 42; //Actually Size.Board.size()-1 is the last playable level, but Size.Board.size() is needed for evaluating that turn
        
        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) throw new IllegalArgumentException("The game associated with the given board is already over.");
        if (maxDepth > Size.BOARD.column() * Size.BOARD.row()) throw new IllegalArgumentException("MaxAbosluteDepth can not be greater than the maximal possible turn count.");
        
        AlphaBeta alg = new AlphaBeta(rater, nextTurnsComputer, maxDepth);        
        
        AlphaBetaResult result = alg.alphaBeta(currentBoard, currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        log("Depth: " + maxDepth);
        log("Rated boards: " + alg.ratedBoards);
        log("CutOffs: " + alg.cutOffs);
        
        log("Proposed turn:");
        log(result.toString());
        
        return result;
    } 
    
    private static void log(String msg) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.S" );
        String date = dateFormatter.format(new Date());
        
        msg = date + ": " + msg;
        
        System.out.println(msg);
        try {
            logFile.append(msg + "\n");
            logFile.flush();
        } catch (Exception e) {
            System.out.println("Could not write to log file: " + e);
        }
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
        
        AlphaBetaResult bestNextTurn = null;
        Board bestNextBoard = null;
        
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
                
                if (max >= beta) {
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
}
