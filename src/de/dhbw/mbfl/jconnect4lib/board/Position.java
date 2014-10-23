package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author Maurice Busch & Florian Loch
 */

public class Position {
    private int col, row;
    
    public Position(int position) {
        this.col = position % Board.COLUMN_COUNT;
        this.row = (int) position / Board.COLUMN_COUNT;
    }
    
    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getColumn() {
        return this.col;
    }
    
    public int getPosition() {
        return this.col + this.row * Board.COLUMN_COUNT;
    }
}
