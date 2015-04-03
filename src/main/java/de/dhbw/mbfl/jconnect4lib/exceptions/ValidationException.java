package de.dhbw.mbfl.jconnect4lib.exceptions;

/**
 * This exception is thrown when an error is decated on the board, durning the validation
 * progress. For example if the player trys to ceat.
 * @author Maurice Busch & Florian Loch
 */
public class ValidationException extends Exception {
     
    public ValidationException(String msg) {
        super(msg);
    }
}
