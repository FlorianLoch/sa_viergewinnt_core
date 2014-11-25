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

    public AlphaBetaResult(Position computedTurn, int value) {
        this.computedTurn = computedTurn;
        this.value = value;
    }
    
    public Position getComputedTurn() {
        return this.computedTurn;
    }

    public int getValue() {
        return this.value;
    }
}
