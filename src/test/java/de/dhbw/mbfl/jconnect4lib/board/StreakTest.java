/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the Streak calss
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class StreakTest
{
    
    /**
     * Creats a streak and counds up to 4.
     */
    @Test
    public void testCountUpFromZero()
    {
        Streak streak = new Streak(4, null);

        int countUpOne = streak.getStreakLength();
        streak.countUp(null);
        int countUpTwo = streak.getStreakLength();
        streak.countUp(null);
        int countUpThree = streak.getStreakLength();
        streak.countUp(null);
        int countUpFour = streak.getStreakLength();

        assertEquals("Must be one after one count", 1, countUpOne);
        assertEquals("Must be two after two count", 2, countUpTwo);
        assertEquals("Must be three after three count", 3, countUpThree);
        assertEquals("Must be fore after fore count", 4, countUpFour);
    }
    
    /**
     * Creates a streak with end on 4. Counts form 0 to 5 and looks if end is fals on 0,1,2,3 and true on 4 and 5.
     */
    @Test
    public void testIsEndFormZero()
    {
        Streak streak = new Streak(4, null);

        boolean countUpOne = streak.isGameWon();
        streak.countUp(null);
        boolean countUpTwo = streak.isGameWon();
        streak.countUp(null);
        boolean countUpThree = streak.isGameWon();
        streak.countUp(null);
        boolean countUpFour = streak.isGameWon();
        streak.countUp(null);
        boolean countUpFive = streak.isGameWon();

        assertFalse("Must be false after one counts", countUpOne);
        assertFalse("Must be false after two counts", countUpTwo);
        assertFalse("Must be false after three counts", countUpThree);
        assertTrue("Must be true after four counts", countUpFour);
        assertTrue("Must be true after five counts", countUpFive);
    }
    
}
