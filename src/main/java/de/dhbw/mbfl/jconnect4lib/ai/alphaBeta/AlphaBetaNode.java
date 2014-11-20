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
public abstract class AlphaBetaNode extends Node<AlphaBetaNode> {
    protected Position position;
    protected int value;

    public AlphaBetaNode(Position position) {
        this.position = position;
    }
    
    public AlphaBetaNode(Position position, int value) {
        this.value = value;
    }
    
    //alpha is the best way to the root currently found from of a maximizers point of view. The maximizer will not let a smaller value then alpha pass up the hierachy.
    //beta is the best way out of the minimizers perspective. The minimizer placed upper in the hierachy will not let a higher value the beta pass.
    protected int computeValue(int level, int alpha, int beta) {
        if (level == 0 || this.isLeaf()) {
            return this.value;
        }
        
        return this.recursionStep(level-1, alpha, beta);
    }
    
    abstract protected int recursionStep(int level, int alpha, int beta);
    
    public int computeBestTurnFromNode(int depth) {
        int value = this.computeValue(depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        System.out.println("Value of current node: " + value);
        
        return value;
    }
}
