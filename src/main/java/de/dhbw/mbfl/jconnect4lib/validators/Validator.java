package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public interface Validator {
    
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException; 

}
