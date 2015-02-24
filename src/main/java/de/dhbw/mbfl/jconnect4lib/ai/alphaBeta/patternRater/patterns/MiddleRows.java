package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 * Created by florian on 24.02.15.
 */
public class MiddleRows extends PatternDetector {

    @Override
    protected RatingResult searchPatternImpl(Board board) {
        int center = (int) Size.BOARD.row() / 2 - 1; //Artificially lower the center by 1, this also acts as workaround for 7*6 default board
        int ratingPlayerOne = 0;
        int ratingPlayerTwo = 0;

        for (Position p : board) {
            int distanceToCenter = p.getRow() - center;
            if (p.getRow() < center) {
                distanceToCenter *= -1;
            }

            int value = center - distanceToCenter;

            //Base row always gives 1 point
            if (p.getRow() == 0) {
                value = 1;
            }

            //Rows below or at the center give twice the amount of points
            if (p.getRow() <= center) {
                value *= 2;
            }

            if (board.getStone(p) == Stone.YELLOW) ratingPlayerOne += value;
            if (board.getStone(p) == Stone.RED) ratingPlayerTwo += value;
        }

        return new RatingResult(ratingPlayerOne, ratingPlayerTwo);
    }
}
