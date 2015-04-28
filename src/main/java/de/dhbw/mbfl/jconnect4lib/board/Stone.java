package de.dhbw.mbfl.jconnect4lib.board;


/**
 * Ther are two posible Stones yellow and red.
 * @author Maurice Busch & Florian Loch
 */
public enum Stone {
    YELLOW("\u001B[33m\u25cf\u001B[0m", "YELLOW"), RED("\u001B[31m\u25cf\u001B[0m", "RED");

    private String desc;
    private String sign;

    Stone(String sign, String desc) {
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

    public String getDesc() {
        return desc;
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
        return this.desc + " stone";
    }
}
