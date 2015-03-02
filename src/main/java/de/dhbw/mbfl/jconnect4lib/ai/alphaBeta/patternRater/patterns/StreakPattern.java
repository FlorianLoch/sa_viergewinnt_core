package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by florian on 01.03.15.
 */
public class StreakPattern extends PatternDetector {
    private Board board;
    private Stack<Position> playerOnePositions = new Stack<Position>();
    private Stack<Position> playerTwoPositions = new Stack<Position>();

    @Override
    protected RatingResult searchPatternImpl(Board board) {
        this.board = board;

        ArrayList<Streak> streaksPlayerOne = this.findAllStreaks(this.playerOnePositions);
        ArrayList<Streak> streaksPlayerTwo = this.findAllStreaks(this.playerTwoPositions);

        return new RatingResult(streaksPlayerOne.size(), streaksPlayerTwo.size());
    }

    public ArrayList<Streak> findAllStreaks(Stack<Position> playerPositions) {
        ArrayList<Streak> allStreaks = new ArrayList<Streak>();
        Direction[] directions = new Direction[]{
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST,
                Direction.SOUTH_EAST
        };

        for (Direction d : directions) {
            this.fillUpPlayerPositionLists();
            ArrayList<Streak> foundStreaks = this.findStreaksInDirection(d, playerPositions);
            allStreaks.addAll(foundStreaks);
        }

        return allStreaks;
    }

    private ArrayList<Streak> findStreaksInDirection(Direction direction, Stack<Position> playerPositions) {
        HashSet<Position> alreadyVisitedPositions = new HashSet<Position>();
        ArrayList<Streak> allStreaks = new ArrayList<Streak>();

        while (!playerPositions.isEmpty()) {
            Position p = playerPositions.pop();

            if (alreadyVisitedPositions.contains(p)) continue;

            Streak streakFound = this.board.startCountingStreak(p, direction, direction.getOpposite());
            if (streakFound == null) continue;

            allStreaks.add(streakFound);

            for (Position streakItem : streakFound.getStreakItems()) {
                alreadyVisitedPositions.add(streakItem);
            }
        }

        return allStreaks;
    }

    private void fillUpPlayerPositionLists() {
        this.playerOnePositions.clear();
        this.playerTwoPositions.clear();

        for (Position p : this.board) {
            Stone s = this.board.getStone(p);
            if (s == Stone.YELLOW) {
                this.playerOnePositions.add(p);
            }
            else if (s == Stone.RED) {
                this.playerTwoPositions.add(p);
            }
        }
    }
}
