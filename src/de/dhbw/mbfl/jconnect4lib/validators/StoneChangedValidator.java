package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class StoneChangedValidator implements Validator {

    public static final String MSG = "Stone at position $s changed!";
    
    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException {
        for (Difference dif : differences) {
            if (dif.getOldStone() != null && dif.getNewStone() != dif.getOldStone()) {
                throw new ValidationException(String.format(MSG, dif.getPosition()), dif.getPosition());
            }
        }
    }
    
}
