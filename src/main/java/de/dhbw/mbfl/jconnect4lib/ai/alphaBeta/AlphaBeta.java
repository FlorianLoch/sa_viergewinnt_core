/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
public class AlphaBeta {
    private Board bestNextBoard;
    private final AlphaBetaRater rater;
    private final NextTurnsComputer nextTurnsGenerator;
    private final int maxAbsoluteDepth;

    private AlphaBeta(AlphaBetaRater rater, NextTurnsComputer nextTurnsComputer, int maxAbsoluteDepth) {
        if (rater == null) rater = new DefaultAlphaBetaRater();
        this.rater = rater;
        
        if (nextTurnsComputer == null) nextTurnsComputer = new DefaultNextTurnsComputer();
        this.nextTurnsGenerator = nextTurnsComputer;
        
        this.maxAbsoluteDepth = maxAbsoluteDepth;
    }
    
    public static AlphaBetaResult findBestTurn(Board currentBoard, int maxAbsoluteDepth) {
        return findBestTurn(currentBoard, maxAbsoluteDepth, null, null);
    }
    
    public static AlphaBetaResult findBestTurn(Board currentBoard, int maxAbsoluteDepth, AlphaBetaRater rater, NextTurnsComputer nextTurnsComputer) {        
        int currentDepth = currentBoard.getTurnCount() + 1;
        
        if (currentDepth > maxAbsoluteDepth) throw new IllegalArgumentException("The game's depth is already higher than the maxAbsoluteDepth.");
        if (currentBoard.turnEndedGame() != 0) throw new IllegalArgumentException("The game associated with the given board is already over.");
        if (maxAbsoluteDepth > Size.BOARD.column() * Size.BOARD.row()) throw new IllegalArgumentException("MaxAbosluteDepth can not be greater than the maximal possible turn count.");
        
        AlphaBeta alg = new AlphaBeta(rater, nextTurnsComputer, maxAbsoluteDepth);        
        
        int value = alg.alphaBeta(currentBoard, currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        return new AlphaBetaResult(alg.bestNextBoard.getLastTurn(), value);
    }
    
    private int alphaBeta(Board currentBoard, int currentDepth, int alpha, int beta) {
        if (currentDepth == this.maxAbsoluteDepth) return this.rater.rate(currentBoard);
        
        LinkedList<Board> possibleNextBoards = this.nextTurnsGenerator.computeNextTurns(currentBoard);
        
        if (possibleNextBoards.isEmpty()) return this.rater.rate(currentBoard);
        
        if ((currentDepth % 2) == 0) {
            int max = alpha;
            for (Board b : possibleNextBoards) {
                int x = alphaBeta(b, currentDepth + 1, max, beta);
                
                if (x > max) {
                    max = x;
                    this.bestNextBoard = b; //Actually this just needs to be done on the start level, but checking this every time propably is more expensive 
                                            //then changing the target of the reference
                }
                
                if (max >= beta) {
                    break;
                }
            }
            return max;
        }
        
        //if !maximize
        int min = beta;
        for (Board b : possibleNextBoards) {
            int x = alphaBeta(b, currentDepth + 1, alpha, min);

            if (x < min) {
                min = x;
                this.bestNextBoard = b; //Actually this just needs to be done on the start level, but checking this every time propably is more expensive 
                                        //then changing the target of the reference
            }

            if (min <= alpha) {
                break;
            }
        }
        return min;
    }
}
