package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Direction;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.exceptions.InvalidPositionException;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;

/**
 * Tests if the Stone is on the right position.
 *
 * @author Maurice Busch & Florian Loch
 */
public class InvalidPositionValidator implements Validator
{
    
    /**
     * Tests if the Stone is on the right position.
     * @param differences
     * @param board
     * @throws ValidationException 
     */
    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException
    {
        for (Difference difference : differences)
        {
            Position difPos = difference.getPosition();
            if (difPos.getRow() >= 1 && board.getStone(difPos.newPosition(Direction.SOUTH)) == null)
            {
                throw new InvalidPositionException(difPos);
            }
        }
    }

}
