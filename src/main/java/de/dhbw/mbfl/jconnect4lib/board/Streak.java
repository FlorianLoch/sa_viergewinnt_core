/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import java.util.ArrayList;

/**
 * A streak is one counter witch counts the stones.
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Streak
{
    private int endStreak;
    private ArrayList<Position> streak;
    
    public Streak(int endStreak, Position position)
    {
        this.streak = new ArrayList<Position>();
        this.streak.add(position);
        this.endStreak = endStreak;
    }
    
    /**
     * countet streak
     * @return int streak
     */
    public int getStreakLength()
    {
        return streak.size();
    }

    /**
     * counts up the Streak
     */
    public void countUp(Position position)
    {
        this.streak.add(position);
    }
    
    /**
     * tests if the counted streak ends the game.
     * @return boolean
     */
    public boolean isGameWon()
    {
        return (this.streak.size() >= this.endStreak);
    }

    public ArrayList<Position> getStreakItems() {
        return this.streak;
    }
}
