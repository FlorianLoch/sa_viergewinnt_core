package de.dhbw.mbfl.jconnect4lib.board;

import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Board {
    public static final int COLUMN_COUNT = 7;
    public static final int ROW_COUNT = 6;
    
    private Stone[][] board = new Stone[ROW_COUNT][COLUMN_COUNT];

    public Board() {
    }
    
    private Board(Stone[][] board) {
        this.board = board;
    }

    public Board clone() {
        return new Board(this.board);
    }
    
    public void addStone(Position pos, Stone stone) {
        this.board[pos.getRow()][pos.getColumn()] = stone;
    }
    
    public Stone getStone(Position pos) {
        return this.board[pos.getRow()][pos.getColumn()];
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
}
