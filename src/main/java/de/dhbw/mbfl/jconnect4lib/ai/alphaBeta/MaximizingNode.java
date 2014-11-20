/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author florian
 */
public final class MaximizingNode extends AlphaBetaNode {

    public MaximizingNode(Position position) {
        super(position);
    }

    public MaximizingNode(Position position, int value) {
        super(position, value);
    }    
    
    @Override
    public int recursionStep(int level, int alpha, int beta) {
        int max = alpha;
        
        for (AlphaBetaNode n : this) {
            max = Math.max(max, n.computeValue(level, max, beta));
            if (max >= beta) break;
        }
        
        return max;
    }
    
}
