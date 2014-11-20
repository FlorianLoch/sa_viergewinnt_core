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
public final class MinimizingNode extends AlphaBetaNode {

    public MinimizingNode(Position position) {
        super(position);
    }
    
    public MinimizingNode(Position position, int value) {
        super(position, value);
    }        
    
    @Override
    protected int recursionStep(int level, int alpha, int beta) {
        int min = beta;
        
        for (AlphaBetaNode n : this) {
            min = Math.min(min, n.computeValue(level, alpha, min));
            if (min <= alpha) break;
        }
        
        return min;
    }
    
}
