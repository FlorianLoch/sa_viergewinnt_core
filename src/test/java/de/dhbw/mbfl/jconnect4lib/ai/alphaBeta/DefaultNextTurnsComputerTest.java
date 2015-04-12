/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
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
    public void testComputeNextTurnsStartSituationOn7xXBoard() {
        DefaultNextTurnsComputer instance = new DefaultNextTurnsComputer();

        LinkedList<Board> expected = new LinkedList<Board>();
        expected.add(new Board().addStone(new Position(3)));
        expected.add(new Board().addStone(new Position(2)));
        expected.add(new Board().addStone(new Position(4)));
        expected.add(new Board().addStone(new Position(1)));
        expected.add(new Board().addStone(new Position(5)));
        expected.add(new Board().addStone(new Position(0)));
        expected.add(new Board().addStone(new Position(6)));

        int expectedBoardsCount = expected.size();

        Board board = new Board();
        LinkedList<Board> list = instance.computeNextTurns(board);

        assertEquals("No more than the expected boards have been computed", expectedBoardsCount, list.size());

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).areBoardOccupationsEqual(expected.get(i))) {
                fail("A board has been computed that has not been expected (perhaps just wrong sequence)");
            }
        }
    }

    @Test
    public void testComputeNextTurnsStartSituationOn6xXBoard() {
        Size.BOARD.unlog().changeSize(6, 6);

        DefaultNextTurnsComputer instance = new DefaultNextTurnsComputer();

        LinkedList<Board> expected = new LinkedList<Board>();
        expected.add(new Board().addStone(new Position(3)));
        expected.add(new Board().addStone(new Position(2)));
        expected.add(new Board().addStone(new Position(4)));
        expected.add(new Board().addStone(new Position(1)));
        expected.add(new Board().addStone(new Position(5)));
        expected.add(new Board().addStone(new Position(0)));

        int expectedBoardsCount = expected.size();

        Board board = new Board();
        LinkedList<Board> list = instance.computeNextTurns(board);

        Size.BOARD.unlog().changeSize(7, 6);

        assertEquals("More boards than expected has been computed!", expectedBoardsCount, list.size());

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).areBoardOccupationsEqual(expected.get(i))) {
                fail("A board has been computed that has not been expected (perhaps just wrong sequence)");
            }
        }
    }
}
