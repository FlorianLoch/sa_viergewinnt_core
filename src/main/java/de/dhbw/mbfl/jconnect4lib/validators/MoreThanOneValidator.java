package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.exceptions.MoreThenOneException;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import de.dhbw.mbfl.jconnect4lib.utils.Utils;
import java.util.ArrayList;

/**
 * Test if there is more the one change on the board.
 * @author Maurice Busch & Florian Loch
 */
public class MoreThanOneValidator implements Validator {  
    

    /**
     * Test if there is more the one change on the board.
     * @param differences
     * @param board
     * @throws ValidationException 
     */
    @Override
    public void validate(final ArrayList<Difference> differences, Board board) throws ValidationException {
        if(differences.size() > 1)
        {
            throw new MoreThenOneException(differences);
        }
    }
    
}
