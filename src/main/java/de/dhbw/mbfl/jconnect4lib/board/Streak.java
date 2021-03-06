/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A streak is one counter witch counts the stones.
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Streak
{
    private int endStreak;
    private ArrayList<Position> streak;
    private ArrayList<Position> extendableTo;
    private Direction firstDirection;
    private int couldBeExtendedBy; //How many fields could the streak grow on the board? This counting doesn't take opponents moves into account, it just checks whether there MIGHT be space for extending this streak
    
    public Streak(int endStreak, Position position, Direction direction)
    {
        this.couldBeExtendedBy = 0;
        this.streak = new ArrayList<Position>();
        this.extendableTo = new ArrayList<Position>();
        this.streak.add(position);
        this.endStreak = endStreak;
        this.firstDirection = direction;
    }

    public int getStreakLength()
    {
        return streak.size();
    }

    public void countUp(Position position) {
        this.streak.add(position);
    }

    public void increaseMaxiumumPossibleLength(Position position) {
        this.extendableTo.add(position);
        this.couldBeExtendedBy++;
    }

    public boolean couldBeMaximized() {
        return (this.streak.size() + couldBeExtendedBy >= this.endStreak);
    }

    public boolean isStreakEndingGame()
    {
        return (this.streak.size() >= this.endStreak);
    }

    public ArrayList<Position> getStreakItems() {
        return this.streak;
    }

    public Direction getFirstDirection() {
        return this.firstDirection;
    }

    public Direction getSecondDirection() {
        return this.firstDirection.getOpposite();
    }

    public ArrayList<Position> getExtendableTo() {
        return extendableTo;
    }
}
