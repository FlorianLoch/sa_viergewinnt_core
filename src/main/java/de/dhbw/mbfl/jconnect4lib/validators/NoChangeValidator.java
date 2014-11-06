package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 * Tests if ther is one change.
 * @author Maurice Busch & Florian Loch
 */
public class NoChangeValidator implements Validator {

    public static final String MSG = "Es wurden keine Änderungen vorgenommen.";
    public static final String MSG_UNDEFINED_POSITION = "Undefined position";
    
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
            throw new ValidationException(MSG, new Position(0) {
                @Override
                public String toString() {
                    return MSG_UNDEFINED_POSITION; //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }
    
}
