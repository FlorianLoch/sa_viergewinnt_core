package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class NoChangeValidator implements Validator {

    public static final String MSG = "Es wurden keine Änderungen vorgenommen.";
    
    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException {
        if(differences.size() < 1)
        {
            throw new ValidationException(MSG);
        }
    }
    
}
