/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.common;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import java.util.ArrayList;

/**
 *
 * @author florian
 */
public class AIUtils {
    public static ArrayList<Position> determinePossiblePositions(Board board) {
        ArrayList<Position> possiblePositions = new ArrayList();
        for (int i = 0; i < Size.BOARD.column(); i++) {
            Position lowestInColumn = AIUtils.determineLowestFreeFieldInColumn(i, board);
            if (lowestInColumn != null) {
                possiblePositions.add(lowestInColumn);
            }
        }
        return possiblePositions;
    }
    
    /**
     * Returns the lowest possible position in a column. "Possible" means that the field is currently set to null, so not occupied yet.
     * If there is no field left in this column null is returned.
     * 
     * @param col
     * @param board
     *
     * @return The lowest possible position
     */
    public static Position determineLowestFreeFieldInColumn(int col, Board board) {
        for (int i = 0; i < Size.BOARD.row(); i++) {
            Position p = new Position(col, i);
            if (board.getStone(p) == null) {
                return p;
            }
        }

        return null;
    }
    
    
    
}
