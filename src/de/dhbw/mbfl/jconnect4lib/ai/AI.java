package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public interface AI {
    
    public Position calculateTurn(Board board);
    
}
