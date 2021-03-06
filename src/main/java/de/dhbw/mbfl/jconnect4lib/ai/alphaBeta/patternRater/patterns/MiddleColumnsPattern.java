/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class MiddleColumnsPattern extends PatternDetector {

    @Override
    protected RatingResult searchPatternImpl(Board board) {
        int center = Size.BOARD.column() / 2;
        int ratingPlayerOne = 0;
        int ratingPlayerTwo = 0;

        for (Position p : board) {
            int distanceToCenter = p.getColumn() - center;
            distanceToCenter = Math.abs(distanceToCenter);

            Stone s = board.getStone(p);
            if (s == Stone.YELLOW) ratingPlayerOne += center - distanceToCenter;
            if (s == Stone.RED) ratingPlayerTwo += center - distanceToCenter;
        }

        return new RatingResult(ratingPlayerOne, ratingPlayerTwo);
    }

}
