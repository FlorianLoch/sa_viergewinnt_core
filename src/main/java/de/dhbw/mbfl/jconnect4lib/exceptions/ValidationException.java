package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 * This exception is thrown when an error is decated on the board, durning the validation
 * progress. For example if the player trys to ceat.
 * @author Maurice Busch & Florian Loch
 */
public class ValidationException extends Exception {
    private Position position;
     
    public ValidationException(String msg, Position position) {
        super(msg);
        this.position = position;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " Position: " + position;
    }
}
