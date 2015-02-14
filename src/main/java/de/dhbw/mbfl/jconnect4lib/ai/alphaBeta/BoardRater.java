/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;

/**
 *
 * @author florian
 */
public abstract class BoardRater {

    public int rate(Board board) {
        int currentDepth = board.getTurnCount();
        int state = board.turnEndedGame();

        if (state == Board.STATE_WIN) {
            if ((currentDepth - 1) % 2 == 1) { //-1 actually inverts the result
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        } else if (state == Board.STATE_REMI) {
            return 0;
        }

        return this.rateImpl(board).getRating();
    }

    protected abstract RatingResult rateImpl(Board board);
}
