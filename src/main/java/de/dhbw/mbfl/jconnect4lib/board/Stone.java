package de.dhbw.mbfl.jconnect4lib.board;

/**
 * Ther are two posible Stones yellow and red.
 * @author Maurice Busch & Florian Loch
 */
public enum Stone {
    YELLOW('Y', "YELLOW stone"), RED('R', "RED stone");

    private String desc = "";
    private char sign = ' ';
    
    private Stone(char sign, String desc) {
        this.sign = sign;
        this.desc = desc;
    }
    
    /**
     * Returns the sign of the Stone Y for yellow and R for red.
     * @return sign
     */
    public char getSign() {
        return sign;
    }
    
    /**
     * Returns the other Stone.
     * @return Stone
     */
    public Stone getOtherStone()
    {
        if(this == YELLOW)
        {
            return RED;
        }
        
        return YELLOW;
    }
    
    /**
     * Returns the discription of the stone.
     * @return discription
     */
    @Override
    public String toString() {
        return this.desc;
    }
}
