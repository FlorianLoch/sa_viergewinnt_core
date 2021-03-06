package de.dhbw.mbfl.jconnect4lib.board;

import de.dhbw.mbfl.jconnect4lib.exceptions.OutOfBoardException;
import de.dhbw.mbfl.jconnect4lib.exceptions.PositionOccupiedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Board implements Iterable<Position> {
    public static final int STATE_WIN = 2;
    public static final int STATE_REMI = 1;
    public static final int STATE_NOTYETOVER = 0;

    private static final int STREAK_COUNT_END = 4;

    private Stone[][] board;
    private ArrayList<Position> log;

    private GameState turnEndedGameCache = null;

    public Board()
    {
        this(new Stone[Size.BOARD.row()][Size.BOARD.column()], new ArrayList<Position>(), null);
    }

    private Board(Stone[][] board, ArrayList<Position> log, GameState turnEndedGameCache) {
        this.board = board;
        this.log = log;
        this.turnEndedGameCache = turnEndedGameCache;
    }

    public BoardIdentity computeBoardIdentity() {
        return new BoardIdentity(this);
    }

    public boolean areBoardOccupationsEqual(Board secondBoard) {
        if (size() != secondBoard.size() || this.board.length != secondBoard.board.length) return false;

        for (int i = 0; i < this.board.length; i++) {
            if (!Arrays.equals(this.board[i], secondBoard.board[i])) return false;
        }

        return true;
    }

    public int size() {
        return board.length * board[0].length;
    }

    /**
     * Adds a stone to the board.
     * @param pos
     * @throws PositionOccupiedException
     * @throws OutOfBoardException
     * @return Board for chaining
     */
    public Board addStone(Position pos) throws PositionOccupiedException, OutOfBoardException {
        return this.addStone(pos, this.nextStone());
    }

    /**
     * Adds a stone to the board.
     * @param posStr
     * @throws PositionOccupiedException
     * @throws OutOfBoardException
     * @return Board for chaining
     */
    public Board addStone(String posStr) throws PositionOccupiedException, OutOfBoardException {
        Position pos = new Position(posStr);

        if (pos == null) throw new IllegalArgumentException("Given position string cannot be parsed!");

        return this.addStone(pos, this.nextStone());
    }

    /**
     * Adds a stone to the board.
     * @param pos
     * @param stone
     * @throws PositionOccupiedException
     * @throws OutOfBoardException
     * @return Board for chaining
     */
    @Deprecated
    public Board addStone(Position pos, Stone stone) throws PositionOccupiedException, OutOfBoardException {
        if (!this.isOnBoard(pos)) throw new OutOfBoardException(pos);
        if (this.getStone(pos) != null) throw new PositionOccupiedException(pos);

        this.log.add(pos);
        this.board[pos.getRow()][pos.getColumn()] = stone;

        clearCaches();

        return this;
    }

    /**
     * Returns the stone on the given positon, if ther is no stone null is returned.
     * @param pos
     * @return stone
     */
    public Stone getStone(Position pos) {
        return this.board[pos.getRow()][pos.getColumn()];
    }

    /**
     * Tests if the position is on the board.
     * @param pos
     * @return true if pos is on board and fals if not
     */
    public boolean isOnBoard(Position pos) {
        return !(pos.getRow() < 0 || pos.getColumn() < 0 || pos.getRow() >= Size.BOARD.row() || pos.getColumn() >= Size.BOARD.column());
    }

    /**
     * Determinate the differences between the new Board and the current one.
     * @param newBoard
     * @return differences
     */
    public ArrayList<Difference> determineDifferences(Board newBoard) {
        ArrayList<Difference> dif = new ArrayList<Difference>();

        for (int i = 0; i < Size.BOARD.column() * Size.BOARD.row(); i++) {
            Position pos = new Position(i);
            Stone oldStone = this.getStone(pos);
            Stone newStone = newBoard.getStone(pos);

            if (oldStone != newStone) {
                dif.add(new Difference(oldStone, newStone, pos));
            }
        }

        return dif;
    }

    /**
     * Determines the state the game went into by applying the last turn.
     * Returns 0 in case the game can continue, 1 in case remis occurred and 2 in
     * case a win/lose situation happened
     * @return state of the game after given turn (0, 1, 2)
     */
    public int turnEndedGame() {
        if (null == this.turnEndedGameCache) {
            this.turnEndedGameCache = turnEndedGameImpl();
        }

        return this.turnEndedGameCache.getIntRepresentation();
    }

    public GameState turnEndedGameImpl() {
        if (getTurnCount() == 0) return GameState.NOT_YET_OVER; //Otherwise this check will crash when it gets performed on a new board instance (because in startCountingStreak() lastTurn can not be determined)

        Streak longestStreak = this.searchLongestStreak(this.getLastTurn());
        if (longestStreak != null && longestStreak.isStreakEndingGame()) return GameState.WIN;

        if (getTurnCount() == Size.BOARD.column() * Size.BOARD.row()) {
            return GameState.REMIS;
        }

        return GameState.NOT_YET_OVER;
    }

    public Streak searchLongestStreak(Position startPoint) {
        ArrayList<Streak> streaks = this.searchStreaks(startPoint);

        Streak longestStreak = null;
        int longestStreakLength = 1;
        for (Streak s : streaks) {
            if (s.getStreakLength() > longestStreakLength) {
                longestStreak = s;
                longestStreakLength = s.getStreakLength();
            }
        }

        return longestStreak;
    }

    public ArrayList<Streak> searchStreaks(Position startPoint) {
        Direction[] directions = new Direction[]{
                Direction.NORTH,
                Direction.NORTH_EAST,
                Direction.EAST,
                Direction.SOUTH_EAST
        };
        ArrayList<Streak> streaks = new ArrayList<Streak>();

        for (Direction d : directions) {
            Streak s = this.startCountingStreak(startPoint, d, d.getOpposite());
            if (s == null) continue;
            streaks.add(s);
        }

        return streaks;
    }

    public Streak startCountingStreak(Position startPoint, Direction directionOne, Direction directionTwo) {
        Streak streak = new Streak(STREAK_COUNT_END, startPoint, directionOne);
        streak = countStreak(directionOne, startPoint, streak);
        streak = countStreak(directionTwo, startPoint, streak);

        if (streak.getStreakLength() == 1) return null;

        return streak;
    }

    private Streak countStreak(Direction direction, Position pos, Streak streak) {
        return this.countStreak(direction, pos, streak, this.getStone(pos), false);
    }

    private Streak countStreak(Direction direction, Position pos, Streak streak, Stone color, boolean countingMaxPossibleLength) {
        if (streak.isStreakEndingGame()) {
            return streak;
        }

        Position nextPos = pos.newPosition(direction);

        if (!this.isOnBoard(nextPos)) {
            return streak;
        }

        Stone nextStone = this.getStone(nextPos);

        if (color == nextStone) {
            if (!countingMaxPossibleLength) {
                streak.countUp(nextPos);
            }
        }
        else if (null == nextStone) {
            if (streak.couldBeMaximized()) {
                return streak;
            }

            countingMaxPossibleLength = true;
            streak.increaseMaxiumumPossibleLength(nextPos);
         }
        else {
            return streak;
        }

        return countStreak(direction, nextPos, streak, color, countingMaxPossibleLength);
    }

    /**
     * Gives the last made turn.
     * @return position
     */
    public Position getLastTurn() {
        if(getTurnCount() <= 0)
        {
            return null;
        }
        return this.log.get(getTurnCount() - 1);
    }

    /**
     * Returns true if there are no more posible turns and false if there are more.
     * @return
     */
    public boolean isBoardFull()
    {
        return (getTurnCount() == Size.BOARD.column() * Size.BOARD.row());
    }

    /**
     * Returns true if the game can be contiuned. If the Method returns false someone has wone
     * the game or the board is full
     * @return
     */
    public boolean isGameRunning()
    {
        return (! this.isBoardFull() && this.turnEndedGame() == 0);
    }

    /**
     * Gives the count of done turns
     * @return turns
     */
    public int getTurnCount()
    {
        return this.log.size();
    }

    /**
     * Gives back the next stone
     * @return
     */
    public Stone nextStone()
    {
        if(this.getTurnCount() % 2 == 0) {
            return Stone.YELLOW;
        }

        return Stone.RED;
    }

    /**
     * Gives the last added stone.
     * @return stone
     */
    public Stone getLastStone() {
        Position pos = getLastTurn();
        if(pos == null)
        {
            return null;
        }
        return this.board[pos.getRow()][pos.getColumn()];
    }

    /**
     * Undos the last made turn.
     */
    public void undoLastTurn() {
        Position pos = this.getLastTurn();

        if(pos != null)
        {
            this.log.remove(pos);
            this.board[pos.getRow()][pos.getColumn()] = null;

            clearCaches();
        }
    }

    private void clearCaches() {
        this.turnEndedGameCache = null;
    }

    /**
     * Clones the board.
     * @return board
     */
    public Board clone() {
        Stone[][] tmpBoard = new Stone[Size.BOARD.row()][Size.BOARD.column()];
        for (int i = 0; i < Size.BOARD.row(); i++) {
            for (int j = 0; j < Size.BOARD.column(); j++) {
                tmpBoard[i][j] = this.board[i][j];
            }
        }

        ArrayList<Position> tmpLog = new ArrayList<Position>();
        for (Position p : this.log) {
            tmpLog.add(p.clone());
        }

        return new Board(tmpBoard, tmpLog, this.turnEndedGameCache);
    }

    /**
     * Pars the Board into a string.
     * @return Board as string
     */
    @Override
    public String toString() {
        String s = String.format("Board after %d moves.%n", getTurnCount());

        for (int i = Size.BOARD.row() - 1; i >= 0; i--) {
            s = s + (i + 1) + " | ";
            for (int j = 0; j < Size.BOARD.column(); j++) {
                Stone stone = this.getStone(new Position(j, i));
                s = s + ((stone != null) ? stone.getSign() : " ") + " | ";
            }
            s = s + String.format("%n");
        }
        s = s + "    ";
        for (int i = 0; i < Size.BOARD.column(); i++) {
            s = s + (char) (65 + i) + "   ";
        }

        return s; //To change body of generated methods, choose Tools | Templates.
    }

    // TODO actually not needed anymore
    public ArrayList<Position> determinePossiblePositions() {
        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        for (int i = 0; i < Size.BOARD.column(); i++) {
            Position lowestInColumn = this.determineLowestFreeFieldInColumn(i);
            if (lowestInColumn != null) {
                possiblePositions.add(lowestInColumn);
            }
        }
        return possiblePositions;
    }

    /**
     * Returns the lowest possible position in a column. "Possible" means that the field is currently set to null, so not occupied yet.
     * If there is no field left in this column null is returned.
     *
     * @param col
     *
     * @return The lowest possible position
     */
    public Position determineLowestFreeFieldInColumn(int col) {
        for (int i = 0; i < Size.BOARD.row(); i++) {
            Position p = new Position(col, i);
            if (this.getStone(p) == null) {
                return p;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return this.areBoardOccupationsEqual(board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(this.board);
    }

    @Override
    public Iterator<Position> iterator() {
        return new Iterator<Position>() {
            private int index = 0;
            private int size = board.length * board[0].length;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Position next() {
                return new Position(index++);
            }

            @Override
            public void remove() {
                throw new ConcurrentModificationException("Stone cannot be removed from Board. This shall not be possible.");
            }
        };
    }

    public String getGameplayHistory() {
        String history = "";

        for (int i = 0; i < this.log.size(); i++) {
            history += this.log.get(i);

            if (i + 1 == this.log.size()) {
                break;
            }

            history += (i % 2 == 1) ? "$" : ";";
        }

        return history;
    }
}
