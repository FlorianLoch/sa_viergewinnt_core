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
public final class MaximizingNode extends AlphaBetaNode {

    public MaximizingNode(int depthLevel, Board board) {
        super(depthLevel, board);
    }

    public MaximizingNode(int depthLevel, Board board, int value) {
        super(depthLevel, board, value);
    }    
    
    @Override
    public int recursionStep(int alpha, int beta) {
        int max = alpha;
        
        for (AlphaBetaNode n : this) {
            max = Math.max(max, n.computeValue(max, beta));
            if (max >= beta) break;
        }
        
        return max;
    }
    
}
