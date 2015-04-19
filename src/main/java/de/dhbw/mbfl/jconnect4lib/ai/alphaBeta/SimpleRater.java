package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.BoardRater;
import de.dhbw.mbfl.jconnect4lib.board.Board;

/**
 * Created by florian on 19.04.15.
 */
public class SimpleRater extends BoardRater {

    @Override
    protected RatingResult rateImpl(Board board) {
        return new RatingResult(0, 0);
    }

}
