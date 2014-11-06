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
        Streak streak = new Streak(4);
        
        int countUpZero = streak.getStreak();
        streak.countUp();
        int countUpOne = streak.getStreak();
        streak.countUp();
        int countUpTwo = streak.getStreak();
        streak.countUp();
        int countUpThree = streak.getStreak();
        streak.countUp();
        int countUpFour = streak.getStreak();
        
        assertEquals("Must be zero after zero counts", 0, countUpZero);
        assertEquals("Must be one after one count", 1, countUpOne);
        assertEquals("Must be two after two count", 2, countUpTwo);
        assertEquals("Must be three after three count", 3, countUpThree);
        assertEquals("Must be fore after fore count", 4, countUpFour);
    }
    
    /**
     * Creats a streak and counts from 2 to 4.
     */
    @Test
    public void testCountUpFromTwo()
    {
        Streak streak = new Streak(4, 2);
        
        int countUpTwo = streak.getStreak();
        streak.countUp();
        int countUpThree = streak.getStreak();
        streak.countUp();
        int countUpFour = streak.getStreak();
        
        assertEquals("Must be two after two count", 2, countUpTwo);
        assertEquals("Must be three after three count", 3, countUpThree);
        assertEquals("Must be fore after fore count", 4, countUpFour);
    }
    
    /**
     * Creats a streak with end on 4. Counts form 0 to 5 and looks if end is fals on 0,1,2,3 and true on 4 and 5.
     */
    @Test
    public void testIsEndFormZero()
    {
        Streak streak = new Streak(4);
        
        boolean countUpZero = streak.isEnd();
        streak.countUp();
        boolean countUpOne = streak.isEnd();
        streak.countUp();
        boolean countUpTwo = streak.isEnd();
        streak.countUp();
        boolean countUpThree = streak.isEnd();
        streak.countUp();
        boolean countUpFour = streak.isEnd();
        streak.countUp();
        boolean countUpFive = streak.isEnd();
        
        assertFalse("Must be false after zero counts", countUpZero);
        assertFalse("Must be false after one counts", countUpOne);
        assertFalse("Must be false after two counts", countUpTwo);
        assertFalse("Must be false after three counts", countUpThree);
        assertTrue("Must be true after four counts", countUpFour);
        assertTrue("Must be true after five counts", countUpFive);
    }
    
    /**
     * Creats a Streak with end by 4 an begins by 2. Tests if end is false on 2, 3 and true by 4, 5.
     */
    @Test
    public void testIsEndFormTwo()
    {
        Streak streak = new Streak(4, 2);
        
        boolean countUpTwo = streak.isEnd();
        streak.countUp();
        boolean countUpThree = streak.isEnd();
        streak.countUp();
        boolean countUpFour = streak.isEnd();
        streak.countUp();
        boolean countUpFive = streak.isEnd();
        
        assertFalse("Must be false after two counts", countUpTwo);
        assertFalse("Must be false after three counts", countUpThree);
        assertTrue("Must be true after four counts", countUpFour);
        assertTrue("Must be true after five counts", countUpFive);
    }
    
}
