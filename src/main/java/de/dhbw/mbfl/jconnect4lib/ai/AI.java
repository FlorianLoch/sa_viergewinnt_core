package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 * The Interface for all AIs.
 * @author Maurice Busch & Florian Loch
 */
public interface AI {
    
    /**
     * The AI will get the actual Board. With this board the AI soud calculate one move.
     * @param board
     * @return position of the calculated stone
     */
    public Position calculateTurn(Board board);
    
}
