/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class RatingResult {

    private int playerOne;
    private int playerTwo;

    public RatingResult(int playerOne, int playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public int getRating() {
        return this.playerOne - this.playerTwo;
    }
    
    public int getRatingPlayerOne() {
        return this.playerOne;
    }
    
    public int getRatingPlayerTwo() {
        return this.playerTwo;
    }
    
    public void addRating(RatingResult rating, int weighting, boolean multiplier) {
        if (multiplier) {
            this.playerOne *= rating.getRatingPlayerOne() * weighting;
            this.playerTwo *= rating.getRatingPlayerTwo() * weighting;
            return;
        }

        this.playerOne += rating.getRatingPlayerOne() * weighting;
        this.playerTwo += rating.getRatingPlayerTwo() * weighting;
    }
}
