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

        // TODO Rate diagonal streaks higher because likelihood of being overseen by human opponent is much higher
        // TODO Also horizontal streaks are much better than vertical ones

        // TODO Decide on how to rate streaks - just the amount of found streaks isn't a good metric

        return new RatingResult(streaksPlayerOne.size(), streaksPlayerTwo.size());
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
