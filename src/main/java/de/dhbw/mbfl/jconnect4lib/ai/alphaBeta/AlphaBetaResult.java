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
    
    @Override
    public String toString() {
        return this.toString(1);
    }
    
    public String toString(int step) {
        String s = step + ": Value: " + this.value + " | Turn: " + this.computedTurn;
        s += "\n";
        
        if (this.subsequentTurn == null) return s + "End.";
        
        s += this.subsequentTurn.toString(step + 1);
        
        return s;
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
