package de.dhbw.mbfl.jconnect4lib.board;

import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Board
{

    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;
    
    public static final int STATE_WIN = 2;
    public static final int STATE_REMI = 1;
    public static final int STATE_NOTYETOVER = 0;

    private static final int STREAK_COUNT_END = 4;

    private Stone[][] board = new Stone[ROW_COUNT][COLUMN_COUNT];
    private ArrayList<Position> log = new ArrayList<>();

    public Board()
    {
    }

    private Board(Stone[][] board)
    {
        this.board = board;
    }

    public Board clone()
    {
        Stone[][] tmp = new Stone[ROW_COUNT][COLUMN_COUNT];
        for(int i = 0; i < ROW_COUNT; i++)
        {
            for(int j = 0; j < COLUMN_COUNT; j++)
            {
                tmp[i][j] = this.board[i][j];
            }
        }

        return new Board(tmp);
    }

    public void addStone(Position pos, Stone stone)
    {
        this.log.add(pos);
        this.board[pos.getRow()][pos.getColumn()] = stone;
    }

    public Stone getStone(Position pos)
    {
        return this.board[pos.getRow()][pos.getColumn()];
    }

    public boolean isOnBoard(Position pos)
    {
        return !(pos.getRow() < 0 || pos.getColumn() < 0 || pos.getRow() >= ROW_COUNT || pos.getColumn() >= COLUMN_COUNT);
    }
    
    /**
     * Determinate the differences between the new Board and the current one.
     * @param newBoard
     * @return differences
     */
    public ArrayList<Difference> determineDifferences(Board newBoard)
    {
        ArrayList<Difference> dif = new ArrayList<>();

        for(int i = 0; i < COLUMN_COUNT * ROW_COUNT; i++)
        {
            Position pos = new Position(i);
            Stone oldStone = this.getStone(pos);
            Stone newStone = newBoard.getStone(pos);

            if(oldStone != newStone)
            {
                dif.add(new Difference(oldStone, newStone, pos));
            }
        }

        return dif;
    }

    /**
     * Determines the state the game went into by applying the last turn.
     * Returns 0 in case the game can continue, 1 in case remi occured and 2 in
     * case a win/lose situation happened
     *
     * @return state of the game after given turn (0, 1, 2)
     */
    public int turnEndedGame()
    {
        Position lastTurn = this.log.get(this.log.size() - 1);
        Stone lastStone = this.getStone(lastTurn);

        Streak streakNorthSouth = new Streak(STREAK_COUNT_END, 1);
        streakNorthSouth = countStreak(Direction.NORTH, lastTurn, streakNorthSouth, lastStone);
        streakNorthSouth = countStreak(Direction.SOUTH, lastTurn, streakNorthSouth, lastStone);
        if(streakNorthSouth.isEnd())
        {
            return STATE_WIN;
        }

        Streak streakEastWest = new Streak(STREAK_COUNT_END, 1);
        streakEastWest = countStreak(Direction.EAST, lastTurn, streakEastWest, lastStone);
        streakEastWest = countStreak(Direction.WEST, lastTurn, streakEastWest, lastStone);
        if(streakEastWest.isEnd())
        {
            return STATE_WIN;
        }

        Streak streakNortheastSouthwest = new Streak(STREAK_COUNT_END, 1);
        streakNortheastSouthwest = countStreak(Direction.EAST, lastTurn, streakNortheastSouthwest, lastStone);
        streakNortheastSouthwest = countStreak(Direction.WEST, lastTurn, streakNortheastSouthwest, lastStone);
        if(streakNortheastSouthwest.isEnd())
        {
            return STATE_WIN;
        }

        Streak streakSoutheastNorthwest = new Streak(STREAK_COUNT_END, 1);
        streakSoutheastNorthwest = countStreak(Direction.EAST, lastTurn, streakSoutheastNorthwest, lastStone);
        streakSoutheastNorthwest = countStreak(Direction.WEST, lastTurn, streakSoutheastNorthwest, lastStone);
        if(streakSoutheastNorthwest.isEnd())
        {
            return STATE_WIN;
        }

        if(this.log.size() == COLUMN_COUNT * ROW_COUNT)
        {
            return STATE_REMI;
        }

        return STATE_NOTYETOVER;
    }
    
    /**
     * Counts the streak from the position with a color.
     * @param direction
     * @param pos
     * @param streak
     * @param color
     * @return the streak
     */
    public Streak countStreak(Direction direction, Position pos, Streak streak, Stone color)
    {
        if(streak.isEnd())
        {
            return streak;
        }

        Position nextPos = pos.getNewPosition(direction);

        if(!this.isOnBoard(nextPos) || this.getStone(nextPos) != color)
        {
            return streak;
        }

        streak.countUp();
        return countStreak(direction, nextPos, streak, color);
    }

    public Position getLastTurn()
    {
        return this.log.get(this.log.size() - 1);
    }

    public void undoLastTurn()
    {
        Position pos = this.getLastTurn();
        this.log.remove(pos);
        this.board[pos.getRow()][pos.getColumn()] = null;
    }

    @Override
    public String toString()
    {
        String s = String.format("Board after %d moves.%n", this.log.size());

        for(int i = ROW_COUNT - 1; i >= 0; i--)
        {
            s = s + (i + 1) + " | ";
            for(int j = 0; j < COLUMN_COUNT; j++)
            {
                Stone stone = this.getStone(new Position(j, i));
                s = s + ((stone != null) ? stone.getSign() : " ") + " | ";
            }
            s = s + String.format("%n");
        }
        s = s + "    ";
        for(int i = 0; i < COLUMN_COUNT; i++)
        {
            s = s + (char) (65 + i) + "   ";
        }

        return s; //To change body of generated methods, choose Tools | Templates.
    }
}
