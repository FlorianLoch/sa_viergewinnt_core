package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by florian on 01.03.15.
 */
public class StreakPattern extends PatternDetector {
    private Stack<Position> playerOnePositions = new Stack<Position>();
    private Stack<Position> playerTwoPositions = new Stack<Position>();

    @Override
    protected RatingResult searchPatternImpl(Board board) {
        this.fillUpPlayerPositionLists(board);
        ArrayList<Streak> streaksPlayerOne = this.findAllStreaks(board, this.playerOnePositions);
        ArrayList<Streak> streaksPlayerTwo = this.findAllStreaks(board, this.playerTwoPositions);

        streaksPlayerOne = this.filterAllMaximizableStreaks(streaksPlayerOne);
        streaksPlayerTwo = this.filterAllMaximizableStreaks(streaksPlayerTwo);

        int ratingPlayerOne = calculateRating(streaksPlayerOne);
        int ratingPlayerTwo = calculateRating(streaksPlayerTwo);

        return new RatingResult(ratingPlayerOne, ratingPlayerTwo);
    }

    private static int calculateRating(ArrayList<Streak> streaksPlayer) {
        int rating = 1; //Because this rater might be used as multiplier it would be bad to return 0 -> it would destroy the rating;

        for (Streak s: streaksPlayer) {
            int streakRating = 0;
            if (s.getStreakLength() == 3) {
                streakRating = 16;
            }
            else if (s.getStreakLength() == 2) {
                streakRating = 4;
            }

            if (s.getFirstDirection().isVertical()) {
                streakRating /= 2;  //Vertical streaks are not that great... Diagonally and horizontal streaks are much better, because horizontal ones
                                    //might lead to traps (diagonal ones also) and diagonally placed streaks are likeli to be overssen by human opponents
            }

            rating += streakRating;
        }

        return rating;
    }

    public ArrayList<Streak> filterAllMaximizableStreaks(ArrayList<Streak> streaks) {
        ArrayList<Streak> essence = new ArrayList<Streak>();

        for (Streak s : streaks) {
            if (s.couldBeMaximized()) {
                essence.add(s);
            }
        }

        return essence;
    }

    public ArrayList<Streak> findAllStreaks(Board board, Stack<Position> playerPositions) {
        ArrayList<Streak> allStreaks = new ArrayList<Streak>();
        Direction[] directions = new Direction[]{
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST,
                Direction.SOUTH_EAST
        };

        for (Direction d : directions) {
            Stack<Position> playerPositionsCopy = new Stack<Position>();
            copyStack(playerPositions, playerPositionsCopy);

            ArrayList<Streak> foundStreaks = this.findStreaksInDirection(board, d, playerPositionsCopy);
            allStreaks.addAll(foundStreaks);
        }

        return allStreaks;
    }

    private ArrayList<Streak> findStreaksInDirection(Board board, Direction direction, Stack<Position> playerPositions) {
        HashSet<Position> alreadyVisitedPositions = new HashSet<Position>();
        ArrayList<Streak> allStreaks = new ArrayList<Streak>();

        while (!playerPositions.isEmpty()) {
            Position p = playerPositions.pop();

            if (alreadyVisitedPositions.contains(p)) continue;

            Streak streakFound = board.startCountingStreak(p, direction, direction.getOpposite());
            if (streakFound == null) continue;

            allStreaks.add(streakFound);

            for (Position streakItem : streakFound.getStreakItems()) {
                alreadyVisitedPositions.add(streakItem);
            }
        }

        return allStreaks;
    }

    private void fillUpPlayerPositionLists(Board board) {
        this.playerOnePositions.clear();
        this.playerTwoPositions.clear();

        for (Position p : board) {
            Stone s = board.getStone(p);
            if (s == Stone.YELLOW) {
                this.playerOnePositions.add(p);
            }
            else if (s == Stone.RED) {
                this.playerTwoPositions.add(p);
            }
        }
    }

    public static <T> void copyStack(Stack<T> from, Stack<T> to) {
        for (T o : from) {
            to.add(o);
        }
    }
}
