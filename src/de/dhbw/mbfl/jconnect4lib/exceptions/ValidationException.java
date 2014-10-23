package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
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
