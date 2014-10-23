package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
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

    public char getSign() {
        return sign;
    }
    
    @Override
    public String toString() {
        return this.desc;
    }
}
