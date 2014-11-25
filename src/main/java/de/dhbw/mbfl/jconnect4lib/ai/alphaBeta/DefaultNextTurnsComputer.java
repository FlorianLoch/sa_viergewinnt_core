/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.common.AIUtils;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
class DefaultNextTurnsComputer implements NextTurnsComputer {

    @Override
    public LinkedList<Board> computeNextTurns(Board currentBoard) {
        LinkedList<Board> possibleTurns = new LinkedList<>();
        LinkedList<Board> sortedPossibleTurns = new LinkedList<>();
        
        if (currentBoard.turnEndedGame() != 0) return sortedPossibleTurns;
        
        //Who's turn is it?
        Stone who = (currentBoard.getTurnCount() % 2 == 0) ? Stone.YELLOW : Stone.RED;
        
        //Try to add a stone in each column
        for (Position p : AIUtils.determinePossiblePositions(currentBoard)) {
            possibleTurns.add(currentBoard.clone().addStone(p, who));
        }
        
        //"Presort" to enable higher "cutting" rate    
        for (Board b : possibleTurns) {
            if (b.turnEndedGame() != 0) {
                sortedPossibleTurns.offerFirst(b);
            }
            else {
                sortedPossibleTurns.offerLast(b);
            }
        }
        
        return sortedPossibleTurns;
    }
}
