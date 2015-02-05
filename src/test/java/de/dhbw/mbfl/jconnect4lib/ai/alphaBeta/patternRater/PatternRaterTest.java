/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater;

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
        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            public int searchPattern(Board board) {
                assertTrue(board == null);
                return 42;
            }
        });
        int rating = instance.rate(null);
        assertTrue(rating == 42);
    }

    @Test
    public void testRateWithCustomWeighting() {
        PatternRater instance = new PatternRater();
        instance.addPatternDetector(new PatternDetector() {
            @Override
            public int searchPattern(Board board) {
                assertTrue(board == null);
                return 42;
            }
        }, 3);
        int rating = instance.rate(null);
        assertTrue(rating == 126);
    }    
}
