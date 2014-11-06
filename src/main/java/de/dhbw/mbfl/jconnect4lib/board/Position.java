package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author Maurice Busch & Florian Loch
 */

public class Position {
    private int col, row;
    
    public static Position parsePosition(String posStr) {
        posStr = posStr.toUpperCase();
        
        if (posStr.length() != 2) return null;
        
        int asciiFirstChar = posStr.codePointAt(0);
        int digitSecondChar = Integer.parseInt(posStr.substring(1));
        
        // TODO make this check more generic or remove the check here and let it be handled by isOnBoard()
        if (asciiFirstChar > 64 && asciiFirstChar < 72 && digitSecondChar > 0 && digitSecondChar < 7) {
            return new Position((Integer.parseInt(posStr.substring(1)) - 1) * 7 + (posStr.codePointAt(0) - 65));
        }   
        
        return null;
    }
    
    public Position(int position) {
        this.col = position % Board.COLUMN_COUNT;
        this.row = (int) position / Board.COLUMN_COUNT;
    }
    
    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    public Position clone() {
        return new Position(this.col, this.row);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        
        Position p = (Position) obj;
        
        return (p.col == this.col && p.row == this.row);
    }
        
    public Position(String position)
    {
        Position pos = Position.parsePosition(position);
        this.col = pos.getColumn();
        this.row = pos.getRow();
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
    
    public Position getNewPosition(Direction direction)
    {
        return new Position(this.col + direction.verticalDirection(), row + direction.horizontalDirection());
    }

    @Override
    public String toString() {
        return ((char) (65 + this.col)) + String.valueOf(this.row + 1);
    }
}
