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
public class AlphaBetaResult {
    private Position computedTurn;
    private int value;
    private AlphaBetaResult subsequentTurn;

    public AlphaBetaResult(Position computedTurn, int value, AlphaBetaResult subsequentTurn) {
        this.computedTurn = computedTurn;
        this.value = value;
        this.subsequentTurn = subsequentTurn;
    }
    
    public Position getComputedTurn() {
        return this.computedTurn;
    }

    public int getValue() {
        return this.value;
    }
    
    public AlphaBetaResult getSubsequentTurn() {
        return this.subsequentTurn;
    }
}
