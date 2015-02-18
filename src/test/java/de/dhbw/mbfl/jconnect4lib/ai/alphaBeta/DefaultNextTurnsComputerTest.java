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
     * Test of computeNextTurns method of class DefaultNextTurnsComputer.
     */
    @Test
    public void testComputeNextTurnsWinSituation() {
        DefaultNextTurnsComputer instance = new DefaultNextTurnsComputer();
    
        Board currentBoard = new Board();
        currentBoard.addStone(new Position(0, 0));
        currentBoard.addStone(new Position(1, 0));
        currentBoard.addStone(new Position(0, 1));
        currentBoard.addStone(new Position(1, 1));
        currentBoard.addStone(new Position(0, 2));
        currentBoard.addStone(new Position(1, 2));
        currentBoard.addStone(new Position(0, 3));
        
        assertTrue("The game is over", currentBoard.turnEndedGame() == Board.STATE_WIN);

        assertTrue("No more turns get computed for this board", instance.computeNextTurns(currentBoard).isEmpty());
    }

    @Test
    public void testComputeNextTurnsStartSituation() {
        DefaultNextTurnsComputer instance = new DefaultNextTurnsComputer();

        LinkedList<Board> expected = new LinkedList<Board>();
        for (int i = 0; i < 7; i++) {
            Board b = new Board();
            b.addStone(new Position(i, 0), Stone.YELLOW);
            expected.add(b);
        }
        int expectedBoardsCount = expected.size();

        Board board = new Board();
        LinkedList<Board> list = instance.computeNextTurns(board);

        for (Board b : list) {
            for (Board b2 : expected) {
                if (b.areBoardOccupationsEqual(b2)) {
                    expected.remove(b2);
                    break;
                }
            }
        }

        assertEquals("All expected board should have been computed", expected.size(), 0);
        assertEquals("Not more then the expected boards should have been computed", expectedBoardsCount, list.size());
    }
}
