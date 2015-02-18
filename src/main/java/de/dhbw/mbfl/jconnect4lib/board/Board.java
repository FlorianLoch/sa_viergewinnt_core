package de.dhbw.mbfl.jconnect4lib.board;

import de.dhbw.mbfl.jconnect4lib.exceptions.OutOfBoardException;
import de.dhbw.mbfl.jconnect4lib.exceptions.PositionOccupiedException;
import java.util.ArrayList;
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

    public Board()
    {
        this(new Stone[Size.BOARD.row()][Size.BOARD.column()], new ArrayList<Position>());
    }

    private Board(Stone[][] board, ArrayList<Position> log) {
        this.board = board;
        this.log = log;
    }
    
    public BoardIdentity computeBoardIdentity() {
        return new BoardIdentity(this);
    }
    
    public boolean areBoardOccupationsEqual(Object o) {
        if (!(o instanceof Board)) return false;
            
        Board b = (Board) o;
        
        if (this.size() != b.size()) return false;
        
        Iterator<Position> iter = b.iterator();
        
        for (Position p : this) {
            if (b.getStone(iter.next()) != getStone(p)) return false;
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
     * Returns 0 in case the game can continue, 1 in case remi occured and 2 in
     * case a win/lose situation happened
     * @return state of the game after given turn (0, 1, 2)
     */
    public int turnEndedGame() {
        if (getTurnCount() == 0) return 0; //Otherwise this check will crash when it gets perfomed on a new board instance (because in turnEndGame() lastTurn can not be determined)
        
        if (turnEndGame(Direction.NORTH, Direction.SOUTH).isEnd()) {
            return STATE_WIN;
        }

        if (turnEndGame(Direction.EAST, Direction.WEST).isEnd()) {
            return STATE_WIN;
        }

        if (turnEndGame(Direction.NORTH_EAST, Direction.SOUTH_WEST).isEnd()) {
            return STATE_WIN;
        }

        if (turnEndGame(Direction.NORTH_WEST, Direction.SOUTH_EAST).isEnd()) {
            return STATE_WIN;
        }

        if (getTurnCount() == Size.BOARD.column() * Size.BOARD.row()) {
            return STATE_REMI;
        }

        return STATE_NOTYETOVER;
    }
    
    public Streak turnEndGame(Direction directionOne, Direction directionTwo)
    {
        Position lastTurn = this.log.get(getTurnCount() - 1);
        Streak streak = new Streak(STREAK_COUNT_END, 1);
        streak = countStreak(directionOne, lastTurn, streak);
        streak = countStreak(directionTwo, lastTurn, streak);
        return streak;
    }
    
        
    /**
     * Counts the streak form a position.
     * @param direction
     * @param pos
     * @param streak
     * @return 
     */
    public Streak countStreak(Direction direction, Position pos, Streak streak) {
        return this.countStreak(direction, pos, streak, this.getStone(pos));
    }

    /**
     * Counts the streak from the position with a color.
     * @param direction
     * @param pos
     * @param streak
     * @param color
     * @return the streak
     */
    private Streak countStreak(Direction direction, Position pos, Streak streak, Stone color) {
        if (streak.isEnd()) {
            return streak;
        }

        Position nextPos = pos.newPosition(direction);

        if (!this.isOnBoard(nextPos) || this.getStone(nextPos) != color) {
            return streak;
        }

        streak.countUp();
        return countStreak(direction, nextPos, streak, color);
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
     * Gives the count of done truns
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
        }
    }
    
    /**
     * Colons the board.
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

        return new Board(tmpBoard, tmpLog);
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
   
    
    public ArrayList<Position> determinePossiblePositions() {
        ArrayList<Position> possiblePositions = new ArrayList();
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
}
