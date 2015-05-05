/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.board.Board;

/**
 *
 * @author florian
 */
public abstract class BoardRater {

    public int rate(Board board) {
        //Consult cache first
//        RatingCache cache = RatingCache.PATTERN_RATER;
//
//        Integer rating = cache.lookupBoard(board);
//        if (rating != null) return rating;

        int state = board.turnEndedGame();

        if (state == Board.STATE_WIN) {
            int currentDepth = board.getTurnCount();
            if ((currentDepth - 1) % 2 == 1) { //-1 actually inverts the result //turn with index 0 is equivalent to turn-count of 1
//                cache.putBoard(board, Integer.MIN_VALUE);
                return Integer.MIN_VALUE; //Win for red, because yellow always starts and therefore depth 1-1=0 is yellows first turn
            } else {
//                cache.putBoard(board, Integer.MAX_VALUE);
                return Integer.MAX_VALUE; //Win for yellow
            }
        } else if (state == Board.STATE_REMI) {
//            cache.putBoard(board, 0);
            return 0;
        }

        int calculatedRating = this.rateImpl(board).getRating();
//        cache.putBoard(board, calculatedRating);
        return calculatedRating;
    }

    protected abstract RatingResult rateImpl(Board board);
}
