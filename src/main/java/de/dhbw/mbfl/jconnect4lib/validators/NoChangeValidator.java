package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.exceptions.NoChangeException;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 * Tests if ther is one change.
 * @author Maurice Busch & Florian Loch
 */
public class NoChangeValidator implements Validator {

    /**
     * Tests if ther is one change.
     * @param differences
     * @param board
     * @throws ValidationException 
     */
    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException {
        if(differences.isEmpty())
        {
            throw new NoChangeException();
        }
    }
    
}
