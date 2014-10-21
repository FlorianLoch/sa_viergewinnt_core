package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author Maurice Busch & Florian Loch
 */

public class Position {
    private int pos;
    
    public Position(int position) {
        this.pos = position;
    }
    
    public Position(int col, int row) {
        this.pos = row * Board.COLUMN_COUNT + col;
    }
    
    public int getRow() {
        return (int) this.pos / Board.COLUMN_COUNT;
    }
    
    public int getColumn() {
        return this.pos % Board.COLUMN_COUNT;
    }
    
    public int getPosition() {
        return this.pos;
    }
}
