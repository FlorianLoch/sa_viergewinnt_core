/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author florian
 */
public class TurnSummary {
    private Position turn;
    private boolean won;
    private boolean remi;

    public TurnSummary(Position turn, boolean won, boolean remi) {
        this.turn = turn;
        this.won = won;
        this.remi = remi;
    }

    /**
     * @return the aiTurn
     */
    public Position getTurn() {
        return turn;
    }

    /**
     * @return the won
     */
    public boolean isWon() {
        return won;
    }

    /**
     * @return the remi
     */
    public boolean isRemi() {
        return remi;
    }

    
}
