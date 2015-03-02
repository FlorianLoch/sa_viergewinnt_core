/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import java.lang.reflect.Field;

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

    private static int computeRatingForPlayer(int currentRating, int ratingToAdd, int weighting, boolean multiplier) {
        if (multiplier) {
            if (currentRating >= 0) {
                if (ratingToAdd < 0) {
                    currentRating /= ratingToAdd * weighting * -1;
                    return currentRating;
                }
                currentRating *= ratingToAdd * weighting;
            }
            else {
                if (ratingToAdd <= 0) {
                    currentRating *= ratingToAdd * weighting * -1;
                    return currentRating;
                }
                currentRating /= ratingToAdd * weighting;
            }

            return currentRating;
        }

        currentRating += ratingToAdd * weighting;
        return currentRating;
    }

    public void addRating(RatingResult rating, int weighting, boolean multiplier) {
        this.playerOne = computeRatingForPlayer(this.playerOne, rating.getRatingPlayerOne(), weighting, multiplier);
        this.playerTwo = computeRatingForPlayer(this.playerTwo, rating.getRatingPlayerTwo(), weighting, multiplier);
    }
}
