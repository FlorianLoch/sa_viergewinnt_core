/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author florian
 */
public abstract class AlphaBetaNode extends Node<AlphaBetaNode> {
    protected Board board;
    protected int value;
    protected int depthLevel;

    public AlphaBetaNode(int depthLevel, Board board) {
        this.board = board;
    }
    
    public AlphaBetaNode(int depthLevel, Board board, int value) {
        this(depthLevel, board);
        this.setValue(value);
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }
    
    public int getDepthLevel() {
        return this.depthLevel;
    }
    
    //alpha is the best way to the root currently found from of a maximizers point of view. The maximizer will not let a smaller value then alpha pass up the hierachy.
    //beta is the best way out of the minimizers perspective. The minimizer placed upper in the hierachy will not let a higher value the beta pass.
    protected int computeValue(int alpha, int beta) {
        if (this.isLeaf()) {
            return this.value;
        }
        
        this.value = this.recursionStep(alpha, beta);
        return this.value;
    }
    
    int computeLeafValue() {
        return this.value;
    }
    
    abstract protected int recursionStep(int alpha, int beta);
    
    public int computeBestTurnFromNode(int depth) {
        int value = this.computeValue(Integer.MIN_VALUE, Integer.MAX_VALUE);
        
        System.out.println("Value of current node: " + value);
        
        return value;
    }
}
