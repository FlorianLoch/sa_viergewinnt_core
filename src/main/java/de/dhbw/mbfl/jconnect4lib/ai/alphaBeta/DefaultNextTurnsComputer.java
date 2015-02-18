/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
class DefaultNextTurnsComputer implements NextTurnsComputer {

    @Override
    public LinkedList<Board> computeNextTurns(Board currentBoard) {
        LinkedList<Board> possibleTurns = new LinkedList<Board>();
        LinkedList<Board> sortedPossibleTurns = new LinkedList<Board>();
        
        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) return sortedPossibleTurns;
        
        //Try to add a stone in each column
        for (Position p : currentBoard.determinePossiblePositions()) {
            Board clonedBoard = currentBoard.clone();
            clonedBoard.addStone(p);
            possibleTurns.add(clonedBoard);
        }
        
        //"Presort" to enable higher "cutting" rate    
        for (Board b : possibleTurns) {
            if (b.turnEndedGame() == Board.STATE_NOTYETOVER) {
                sortedPossibleTurns.offerLast(b);
            }
            else {
                sortedPossibleTurns.offerFirst(b);
            }
        }
        
        return sortedPossibleTurns;
    }
}
