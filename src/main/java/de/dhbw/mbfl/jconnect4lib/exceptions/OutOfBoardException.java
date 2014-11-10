package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 * This Exception is thrown when a Position is not on the Board.
 * @author florian & Maurice Busch
 */
public class OutOfBoardException extends RuntimeException {
    
    public static final String MSG = "The given position (##pos##) is outside of the boards bounds.";
    
    public OutOfBoardException(Position pos) {
        super(MSG.replace("##pos##", pos.toString()));
    }
    
}
