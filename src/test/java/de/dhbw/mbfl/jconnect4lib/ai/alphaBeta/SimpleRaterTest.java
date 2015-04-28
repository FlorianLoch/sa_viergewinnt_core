package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by florian on 28.04.15.
 */
public class SimpleRaterTest {

    @Test
    public void testRateImpl() {
        SimpleRater rater = new SimpleRater();

        RatingResult res = rater.rateImpl(new Board());

        assertEquals(0, res.getRating());
    }

}