/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
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

    public int getStreak()
    {
        return streak;
    }

    public void setStreak(int streak)
    {
        this.streak = streak;
    }
    
    public void countUp()
    {
        this.streak++;
    }
    
    public boolean isEnd()
    {
        return (this.streak >= this.endStreak);
    }
}
