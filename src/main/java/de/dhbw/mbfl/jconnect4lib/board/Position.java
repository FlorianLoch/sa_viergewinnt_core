package de.dhbw.mbfl.jconnect4lib.board;

/**
 * The Position repesents the Position on the Board.
 * You have three different ways to access a positon on the board:
 *   - collumn and row (collumn starts with 0 and counts from left to right, row starts
 *     with 0 and counts form bottom to top)
 *   - position int (it's a number witch starts from 0 and starts from left bottom goes to the
 *     right and goes on on the row above from right)
 *       5  35 36 37 38 39 40 41
 *       4  28 29 30 31 32 33 34
 *       3  21 22 23 24 25 26 27
 *       2  14 15 16 17 18 19 20
 *       1  07 08 09 10 11 12 13
 *       0  00 01 02 03 04 05 06
 *           0  1  2  3  4  5  6  <- col
 *   - position string (a combination of string and number, the string represents
 *     the collumn and the number the row)
 *     6  A6 B6 C6 D6 E6 F2 G6
 *     5  A5 B5 C5 D5 E5 F2 G5
 *     4  A4 B4 C4 D4 E4 F2 G4
 *     3  A3 B3 C3 D3 E3 F2 G3
 *     2  A2 B2 C2 D2 E2 F2 G2
 *     1  A1 B1 C1 D1 E1 F1 G1
 *        A  B  C  D  E  F  G
 * 
 * @author Maurice Busch & Florian Loch
 */
public class Position {
    private int col, row;
    
    /**
     * Creates a Position with a column and row
     * @param col
     * @param row 
     */
    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }
    
    /**
     * Creates a position with a number position
     * @param position 
     */
    public Position(int position) {
        this.col = position % Board.COLUMN_COUNT;
        this.row = (int) position / Board.COLUMN_COUNT;
    }
    
    /**
     * Creats a position with a string position
     * @param position 
     */
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
    
    /**
     * Around a position there are eight posible Positions with a Direction you can couse
     * one of this positions.
     * @param direction
     * @return new Position
     */
    public Position newPosition(Direction direction)
    {
        return new Position(this.col + direction.horizontalDirection(), row + direction.verticalDirection());
    }
    
    /**
     * Gives the Positon as a String. For example A1 the letter represents the collumn and
     * the number the row.
     * @return string position
     */
    @Override
    public String toString() {
        return ((char) (65 + this.col)) + String.valueOf(this.row + 1);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) return false;
        
        Position p = (Position) obj;
        
        return (p.col == this.col && p.row == this.row);
    }
    
    public Position clone() {
        return new Position(this.col, this.row);
    }
    
    /**
     * Pars a String position form the format A1 into a position. If the string can't be
     * parsed null is given. 
     * @param posStr
     * @return position
     */
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
}
