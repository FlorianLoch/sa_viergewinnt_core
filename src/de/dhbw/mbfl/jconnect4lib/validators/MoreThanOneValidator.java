package de.dhbw.mbfl.jconnect4lib.validators;

import com.sun.xml.internal.ws.util.StringUtils;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import de.dhbw.mbfl.jconnect4lib.utils.Utils;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class MoreThanOneValidator implements Validator {  
    public static final String MSG = "Es wurde mehr als ein Stein geändert.";

    @Override
    public void validate(ArrayList<Difference> differences, Board board) throws ValidationException {
        if(differences.size() > 1)
        {
            throw new ValidationException(MSG, new Position(0) {
                @Override
                public String toString() {
                    return Utils.joinArrayList(differences, ", ");
                }
            });
        }
    }
    
}
