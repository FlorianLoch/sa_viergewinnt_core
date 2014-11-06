package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 * This exception is thrown when there is already one stone on the Board.
 * @author florian & Maurice Busch
 */
public class PositionOccupiedException extends RuntimeException {

    public PositionOccupiedException(Position pos) {
        super("This position (" + pos + ") is already occupied!");
    }
    
}
