/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
public class AlphaBeta {
    private final AlphaBetaRater rater;
    private final NextTurnsComputer nextTurnsGenerator;
    private final int maxAbsoluteDepth;
    private long ratedBoards;
    private long cutOffs;

    public static void main(String[] args) {
        Board currentBoard = new Board();
        //int maxAbsoluteDepth = 7;
        for (int i = 6; i < 30; i++) {
            AlphaBeta.findBestTurn(currentBoard, i);
        }
    }
    
    private AlphaBeta(AlphaBetaRater rater, NextTurnsComputer nextTurnsComputer, int maxAbsoluteDepth) {
        if (rater == null) rater = new DefaultAlphaBetaRater();
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
    
    public static AlphaBetaResult findBestTurn(Board currentBoard, int maxAbsoluteDepth, AlphaBetaRater rater, NextTurnsComputer nextTurnsComputer) {        
        int currentDepth = currentBoard.getTurnCount();
        
        if (currentDepth > maxAbsoluteDepth) throw new IllegalArgumentException("The game's depth is already higher than the maxAbsoluteDepth.");
        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) throw new IllegalArgumentException("The game associated with the given board is already over.");
        if (maxAbsoluteDepth > Size.BOARD.column() * Size.BOARD.row()) throw new IllegalArgumentException("MaxAbosluteDepth can not be greater than the maximal possible turn count.");
        
        AlphaBeta alg = new AlphaBeta(rater, nextTurnsComputer, maxAbsoluteDepth);        
        
        AlphaBetaResult result = alg.alphaBeta(currentBoard, currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        System.out.println("Depth: " + maxAbsoluteDepth);
        System.out.println("Rated boards: " + alg.ratedBoards);
        System.out.println("CutOffs: " + alg.cutOffs);
        
        return result;
    } 
   
    private AlphaBetaResult alphaBeta(Board currentBoard, int currentDepth, int alpha, int beta) {
        if (currentDepth == this.maxAbsoluteDepth) {
            int value = 0;//this.rater.rate(currentBoard);
            this.ratedBoards++;
            return new AlphaBetaResult(null, value, null);
        }
        
        LinkedList<Board> possibleNextBoards = this.nextTurnsGenerator.computeNextTurns(currentBoard);
        
        if (possibleNextBoards.isEmpty()) {
            int value = 0;//this.rater.rate(currentBoard);
            this.ratedBoards++;
            return new AlphaBetaResult(null, value, null);
        }
        
        AlphaBetaResult bestNextTurn = null;
        Board bestNextBoard = null;
        
        //if maximize
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
                    //break;
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
                //break;
            }
        }
        return new AlphaBetaResult((bestNextBoard == null) ? null : bestNextBoard.getLastTurn(), min, bestNextTurn);
    }
}
