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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class DefaultNextTurnsComputerTest {
    
    public DefaultNextTurnsComputerTest() {
    }

    /**
     * Test of computeNextTurns method, of class DefaultNextTurnsComputer.
     */
    @Test
    public void testComputeNextTurns() {;
        DefaultNextTurnsComputer instance = new DefaultNextTurnsComputer();
    
        Board currentBoard = new Board();
        currentBoard.addStone(new Position(0, 0), Stone.YELLOW);
        currentBoard.addStone(new Position(1, 0), Stone.RED);
        currentBoard.addStone(new Position(0, 1), Stone.YELLOW);
        currentBoard.addStone(new Position(1, 1), Stone.RED);
        currentBoard.addStone(new Position(0, 2), Stone.YELLOW);
        currentBoard.addStone(new Position(1, 2), Stone.RED);
        currentBoard.addStone(new Position(0, 3), Stone.YELLOW);
        
        assertTrue("The game is over", currentBoard.turnEndedGame() == Board.STATE_WIN);

        assertTrue("No more turns get computed for this board", instance.computeNextTurns(currentBoard).isEmpty());
    }
    
}
