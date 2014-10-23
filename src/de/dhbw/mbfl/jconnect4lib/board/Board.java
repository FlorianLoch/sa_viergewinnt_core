package de.dhbw.mbfl.jconnect4lib.board;

import de.dhbw.mbfl.jconnect4lib.validators.StoneChangedValidator;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Board {
    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;
    public static final int STATE_WIN = 2;
    public static final int STATE_REMI = 1;
    public static final int STATE_NOTYETOVER = 0;
    
    private Stone[][] board = new Stone[ROW_COUNT][COLUMN_COUNT];
    private ArrayList<Position> log = new ArrayList<>();
    
    public Board() {
    }
    
    private Board(Stone[][] board) {
        this.board = board;
    }

    public Board clone() {
        Stone[][] tmp = new Stone[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; i++) {
            for (int j = 0; j < COLUMN_COUNT; j++) {
                tmp[i][j] = this.board[i][j];
            }
        }
        
        return new Board(tmp);
    }
    
    public void addStone(Position pos, Stone stone) {
        this.log.add(pos);
        this.board[pos.getRow()][pos.getColumn()] = stone;
    }
    
    public Stone getStone(Position pos) {
        return this.board[pos.getRow()][pos.getColumn()];
    }
    
    public boolean isOnBoard(Position pos) {
        return !(pos.getRow() < 0 || pos.getColumn()< 0 || pos.getRow() >= ROW_COUNT || pos.getColumn() >= COLUMN_COUNT);
    }
    
    public ArrayList<Difference> determineDifferences(Board newBoard) {
        ArrayList<Difference> dif = new ArrayList<>();
        
        for (int i = 0; i < COLUMN_COUNT * ROW_COUNT; i++) {
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
     * Returns 0 in case the game can continue, 1 in case remi occured and 2 in case a win/lose situation happened
     * 
     * @param lastTurn
     * @return state of the game after given turn (0, 1, 2)
     */
    public int turnEndedGame() {
        Position lastTurn = this.log.get(this.log.size() - 1);
        Stone lastStone = this.getStone(lastTurn);
        
        int streakNorth = countStreak(0, 1, lastTurn, 1, lastStone);
        int streakSouth = countStreak(0, -1, lastTurn, streakNorth, lastStone);
        if (streakSouth >= 4) {
            return STATE_WIN;
        }
        
        int streakEast = countStreak(-1, 0, lastTurn, 1, lastStone);
        int streakWest = countStreak(1, 0, lastTurn, streakEast, lastStone);
        if (streakWest >= 4) {
            return STATE_WIN;
        }
        
        int streakNorthEast = countStreak(1, 1, lastTurn, 1, lastStone);
        int streakSouthWest = countStreak(-1, -1, lastTurn, streakNorthEast, lastStone);
        if (streakSouthWest >= 4) {
            return STATE_WIN;
        }
        
        int streakSouthEast = countStreak(1, -1, lastTurn, 1, lastStone);
        int streakNorthWest = countStreak(-1, 1, lastTurn, streakSouthEast, lastStone);        
        if (streakSouthEast >= 4) {
            return STATE_WIN;
        }
        
        if (this.log.size() == COLUMN_COUNT * ROW_COUNT) {
            return STATE_REMI;
        }
        
        return STATE_NOTYETOVER;
    }
    
    private int countStreak(int dCol, int dRow, Position currentPos, int currentStreak, Stone color) {
        if (currentStreak >= 4) {
            return 4;
        }
        
        Position nextPos = new Position(currentPos.getColumn() + dCol, currentPos.getRow() + dRow);
        
        if (!this.isOnBoard(nextPos)) {
            return currentStreak;
        }
        
        if (this.getStone(nextPos) == color) {
            return countStreak(dCol, dRow, nextPos, currentStreak + 1, color);
        }
        
        return currentStreak;
    }
    
    public Position getLastTurn() {
        return this.log.get(this.log.size() - 1);
    }

    @Override
    public String toString() {
        String s = String.format("Board after %d moves.%n", this.log.size());
        
        for (int i = ROW_COUNT - 1; i >= 0; i--) {
            s = s + (i + 1) + " | ";
            for (int j = 0; j < COLUMN_COUNT; j++) {
                Stone stone = this.getStone(new Position(j, i));
                 s = s + ((stone != null) ? stone.getSign() : " ") + " | ";
            }
            s = s + String.format("%n");
        }
        s = s + "    ";
        for (int i = 0; i < COLUMN_COUNT; i++) {
            s = s + (char) (65 + i) + "   ";
        }
        
        return s; //To change body of generated methods, choose Tools | Templates.
    }
}
