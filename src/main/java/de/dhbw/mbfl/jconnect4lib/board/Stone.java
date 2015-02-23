package de.dhbw.mbfl.jconnect4lib.board;


/**
 * Ther are two posible Stones yellow and red.
 * @author Maurice Busch & Florian Loch
 */
public enum Stone {
    YELLOW("\u001B[33m\u25cf\u001B[0m", "YELLOW stone"), RED("\u001B[31m\u25cf\u001B[0m", "RED stone");


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private String desc = "";
    private String sign = "";
    
    private Stone(String sign, String desc) {
        this.sign = sign;
        this.desc = desc;
    }
    
    /**
     * Returns the sign of the Stone Y for yellow and R for red.
     * @return sign
     */
    public String getSign() {
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

    @Override
    public String toString() {
        return this.desc;
    }
}
