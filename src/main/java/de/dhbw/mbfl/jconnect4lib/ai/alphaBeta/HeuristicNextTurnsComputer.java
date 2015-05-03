package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleColumnsPattern;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleRowsPattern;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.StreakPattern;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by florian on 02.05.15.
 */
public class HeuristicNextTurnsComputer implements NextTurnsComputer {
    private PatternRater rater;

    {
        this.rater = new PatternRater();
        this.rater.addPatternDetector(new MiddleColumnsPattern());
        this.rater.addPatternDetector(new MiddleRowsPattern());
        //this.rater.addPatternDetector(new StreakPattern(), 1, true);
    }

    @Override
    public LinkedList<Board> computeNextTurns(Board currentBoard) {
        LinkedList<Board> possibleTurns = new LinkedList<Board>();
        LinkedList<BoardRating> worklist = new LinkedList<BoardRating>();
        LinkedList<Board> turnsInOptimizedOrder;

        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) return possibleTurns;

        //Add a stone in each column
        for (int i = 0; i < Size.BOARD.column(); i++) {
            Position lowestFreeFieldInColumn = currentBoard.determineLowestFreeFieldInColumn(i);

            if (null == lowestFreeFieldInColumn) continue;

            Board clonedBoard = currentBoard.clone();
            clonedBoard.addStone(lowestFreeFieldInColumn);

            possibleTurns.addLast(clonedBoard);
        }

        for (Board b : possibleTurns) {
            if (b.turnEndedGame() == Board.STATE_WIN) {
                LinkedList earlyReturned = new LinkedList<Board>();
                earlyReturned.add(b);
                return earlyReturned;
            }

            worklist.add(new BoardRating(b, this.rater.rate(b)));
        }

        if ((currentBoard.getTurnCount() + 1) % 2 == 1) {
            Collections.sort(worklist, new Comparator<BoardRating>() {
                @Override
                public int compare(BoardRating o1, BoardRating o2) {
                    if (o1.rating > o2.rating) return -1;
                    if (o1.rating == o2.rating) return 0;
                    return 1;
                }
            });
        }
        else {
            Collections.sort(worklist, new Comparator<BoardRating>() {
                @Override
                public int compare(BoardRating o1, BoardRating o2) {
                    if (o1.rating > o2.rating) return 1;
                    if (o1.rating == o2.rating) return 0;
                    return -1;
                }
            });
        }

        turnsInOptimizedOrder = new LinkedList<Board>();
        for (BoardRating boardRating : worklist) {
            turnsInOptimizedOrder.add(boardRating.board);
        }

        return turnsInOptimizedOrder;
    }

    private class BoardRating {
        public Board board;
        public int rating;

        public BoardRating(Board board, int rating) {
            this.board = board;
            this.rating = rating;
        }
    }
}
