/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

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
    
    @Override
    public String toString() {
        return "Value: " + this.value + " | Turn: " + this.computedTurn;
    }

    /**
     *
     * @return the color that is able to win or null if not yet sure
     */
    public Stone gameCanBeWonBy() {
        if (Integer.MIN_VALUE == this.value) {
            return Stone.RED;
        }
        else if (Integer.MAX_VALUE == this.value) {
            return Stone.YELLOW;
        }

        return null;
    }
}
