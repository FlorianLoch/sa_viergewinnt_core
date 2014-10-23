package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Difference {
    private Stone oldStone;
    private Stone newStone;
    private Position position;
    
    private static final String TOSTRING_TEMPLATE = "$s at $s changed to $s";

    public Difference(Stone oldStone, Stone newStone, Position position) {
        this.oldStone = oldStone;
        this.newStone = newStone;
        this.position = position;
    }
    
    /**
     * @return the oldStone
     */
    public Stone getOldStone() {
        return this.oldStone;
    }

    /**
     * @return the newStone
     */
    public Stone getNewStone() {
        return this.newStone;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return String.format(TOSTRING_TEMPLATE, this.oldStone, this.position, this.newStone);
    }
}
