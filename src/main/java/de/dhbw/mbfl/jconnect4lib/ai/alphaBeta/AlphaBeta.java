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
    private final AlphaBetaRater rater;
    private final NextTurnsComputer nextTurnsGenerator;
    private final int maxAbsoluteDepth;
    private long ratedBoards;
    private long cutOffs;
    
    private static FileWriter logFile;

    public static void main(String[] args) throws IOException{
        logFile = new FileWriter("alphaBeta.log");
        
        int[] levelsToCheck;
        levelsToCheck = new int[]{7, 8, 9, 30, 42, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        
        for (int levelToCheck : levelsToCheck) {
            Board currentBoard = new Board();
            AlphaBeta.findBestTurn(currentBoard, levelToCheck);
        }
        
        logFile.close();
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
        
        log("Depth: " + maxAbsoluteDepth);
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
            int value = this.rater.rate(currentBoard);
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
