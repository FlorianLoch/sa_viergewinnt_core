/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class PatternRaterTest {
    /**
     * Test of rate method, of class PatternRater.
     */
    @Test
    public void testRateWithDefaultWeighting() {
        final Board b = new Board();

        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            protected RatingResult searchPatternImpl(Board board) {
                assertTrue(board == b);
                return new RatingResult(84, 42);
            }
        });

        int rating = instance.rate(b);
        assertTrue(rating == 42);
    }

    @Test
    public void testRateWithCustomWeighting() {
        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            protected RatingResult searchPatternImpl(Board board) {
                assertTrue(board != null);
                return new RatingResult(84, 42);
            }
        }, 3, false);

        Board b = new Board();
        int rating = instance.rate(b);
        assertEquals("Rating should be 3 * 84 - 3 * 42", rating, 126);
    }

    @Test
    public void testRateWithCustomWeightingAndMultiplierSingleRater() {
        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            protected RatingResult searchPatternImpl(Board board) {
                assertTrue(board != null);
                return new RatingResult(84, 42);
            }
        }, 4, true);


        Board b = new Board();
        int rating = instance.rate(b);
        //Rating is initially 0, so multiplying doesn't change anything
        assertEquals("Rating should be 0 * 84 - 0 * 42", 0, rating);
    }

    @Test
    public void testRateWithCustomWeightingAndMultiplierZero() {
        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            protected RatingResult searchPatternImpl(Board board) {
                assertTrue(board != null);
                return new RatingResult(84, 42);
            }
        }, 4, false);
        instance.addPatternDetector(new PatternDetector() {
            @Override
            protected RatingResult searchPatternImpl(Board board) {
                assertTrue(board != null);
                return new RatingResult(4, 2);
            }
        }, 2, true);

        Board b = new Board();
        int rating = instance.rate(b);
        assertEquals("Rating should be 4 * 84 * 4 * 2 - 4 * 42 * 2 * 2", 2016, rating);
    }
}
