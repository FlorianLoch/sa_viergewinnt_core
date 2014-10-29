package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 * Tests if the Stone is on the right position.
 *
 * @author Maurice Busch & Florian Loch
 */
public class InvalidPositionValidator implements Validator
{
    public static final String MSG = "Fehlerhafte Position. Unter dem neuen Stein (%s) muss sich ein weiterer Stein befinden.";  
    
    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException
    {
        for (Difference difference : differences)
        {
            Position difPos = difference.getPosition();
            if (difPos.getRow() >= 1 && board.getStone(new Position(difPos.getColumn(), difPos.getRow() - 1)) == null)
            {
                throw new ValidationException(String.format(MSG, difPos), difPos);
            }
        }
    }

}
