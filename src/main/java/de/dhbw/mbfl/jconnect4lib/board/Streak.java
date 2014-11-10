/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 * A streak is one counter witch counts the stones.
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Streak
{
    private int streak;
    private int endStreak;
    
    public Streak(int endStreak)
    {
        this(endStreak, 0);
    }
    
    public Streak(int endStreak, int streak)
    {
        this.streak = streak;
        this.endStreak = endStreak;
    }
    
    /**
     * countet streak
     * @return int streak
     */
    public int getStreak()
    {
        return streak;
    }
    
    /**
     * counts up the Streak
     */
    public void countUp()
    {
        this.streak++;
    }
    
    /**
     * tests if the counted streak ends the game.
     * @return boolean
     */
    public boolean isEnd()
    {
        return (this.streak >= this.endStreak);
    }
}
