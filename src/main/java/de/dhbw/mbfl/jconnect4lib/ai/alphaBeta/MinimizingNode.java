/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;

/**
 *
 * @author florian
 */
public final class MinimizingNode extends AlphaBetaNode {

    public MinimizingNode(int depthLevel, Board board) {
        super(depthLevel, board);
    }
    
    public MinimizingNode(int depthLevel, Board board, int value) {
        super(depthLevel, board, value);
    }        
    
    @Override
    protected int recursionStep(int alpha, int beta) {
        int min = beta;
        
        for (AlphaBetaNode n : this) {
            min = Math.min(min, n.computeValue(alpha, min));
            if (min <= alpha) break;
        }
        
        return min;
    }
    
}
