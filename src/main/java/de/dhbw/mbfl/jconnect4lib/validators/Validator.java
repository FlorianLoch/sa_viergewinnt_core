package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 * The Validator interface, a class with implements this interface shoud test if there are
 * errors or wrong changes on the board. And the most importend part is to check if the
 * gamer is cheating.
 * @author Maurice Busch & Florian Loch
 */
public interface Validator {
    
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException; 

}
