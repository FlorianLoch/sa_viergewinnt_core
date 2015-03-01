/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import de.dhbw.mbfl.jconnect4lib.exceptions.OutOfBoardException;
import de.dhbw.mbfl.jconnect4lib.exceptions.PositionOccupiedException;
import java.util.ArrayList;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;
import org.junit.Before;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class BoardTest extends EasyMockSupport
{
    
    @Before
    public void setCorrectBoardSize() {
        Size.BOARD.unlog().changeSize(7, 6);
    }
    
    @Test
    public void testArrayAccess() {
        int[][] a = new int[4][4];
        
        assertTrue(a[0].length == 4);
    }
    
    @Test
    public void testPositionIterator() {
        int counter = 0;
        int boardSize = Size.BOARD.column() * Size.BOARD.row();
        Stone[] positions = new Stone[boardSize];
        
        Board instance = new Board();
        instance.addStone(new Position(1, 2));
        instance.addStone(new Position(0));
        instance.addStone(new Position(41));
       
        for (Position p : instance) {
            positions[counter] = instance.getStone(p);
            counter++;
        }
        
        assertTrue(counter == boardSize);
        assertEquals(positions[1 + 2 * Size.BOARD.column()], Stone.YELLOW);
        assertEquals(positions[0], Stone.RED);
        assertEquals(positions[41], Stone.YELLOW);
    }
    
    /**
     * Tests if the method addStone throws an OutOfBoard exeption
     */
    @Test
    public void testAddStoneOutOfBoard()
    {
        Position pos = createStrictMock(Position.class);
        Board board = createMockBuilder(Board.class).addMockedMethods("isOnBoard", "nextStone").createStrictMock();
        
        expect(board.nextStone()).andReturn(Stone.YELLOW);
        expect(board.isOnBoard(pos)).andReturn(false);
        replayAll();
        
        try {
            board.addStone(pos);
            fail("Expected an OutOfBoardException to be thrown");
        } catch (OutOfBoardException ex) {
            
            assertThat(ex.getMessage(), containsString(OutOfBoardException.MSG.replace("##pos##", "EasyMock for class de.dhbw.mbfl.jconnect4lib.board.Position")));
        }
        verifyAll();
    }
    
    /**
     * Tests that the addStone method throws a PositionOccupiedException.
     */
    @Test
    public void testAddStonePositionOccupied()
    {
        Position pos = createStrictMock(Position.class);
        Board board = createMockBuilder(Board.class).addMockedMethods("isOnBoard", "getStone", "nextStone").createStrictMock();
        
        expect(board.nextStone()).andReturn(Stone.RED);
        expect(board.isOnBoard(pos)).andReturn(true);
        expect(board.getStone(pos)).andReturn(Stone.RED);
        expect(board.nextStone()).andReturn(Stone.YELLOW);
        expect(board.isOnBoard(pos)).andReturn(true);
        expect(board.getStone(pos)).andReturn(Stone.YELLOW);
        replayAll();
        
        try {
            board.addStone(pos);
            fail("Expected an PositionOccupiedException to be thrown (red)");
        } catch (PositionOccupiedException ex) {
            assertThat(ex.getMessage(), containsString(PositionOccupiedException.MSG.replace("##pos##", "EasyMock for class de.dhbw.mbfl.jconnect4lib.board.Position")));
        }
        
        try {
            board.addStone(pos);
            fail("Expected an PositionOccupiedException to be thrown (yellow)");
        } catch (PositionOccupiedException ex) {
            assertThat(ex.getMessage(), containsString(PositionOccupiedException.MSG.replace("##pos##", "EasyMock for class de.dhbw.mbfl.jconnect4lib.board.Position")));
        }
        verifyAll();
    }
    
    /**
     * Adds one stone and tests if he is on the right position, the position is correctly logged
     * and the added stone is correct.
     */
    @Test
    public void testAddStoneSmall()
    {
        Position pos = createPositionMock(0, 0, 3);
        
        Position posNull = createStrictMock(Position.class);
        expect(posNull.getRow()).andReturn(0);
        expect(posNull.getColumn()).andReturn(1);
        
        Size.BOARD.unlog().changeSize(2, 1);
        Board board = createMockBuilder(Board.class).addMockedMethods("isOnBoard").withConstructor().createStrictMock();
        
        expect(board.isOnBoard(pos)).andReturn(true);
        replayAll();
        
        board.addStone(pos);
        Position posLog = board.getLastTurn();
        Stone stoneAdded = board.getStone(pos);
        Stone nullStone = board.getStone(posNull);
        
        assertEquals("The position in the log must be equas to the addes pos.", pos, posLog);
        assertEquals("The Stone on the board must have the same color as the added one.", Stone.YELLOW, stoneAdded);
        assertNull("Only one Stone added the otherone must be null.", nullStone);
        
        verifyAll();
    }
    
    /**
     * Makes some moves and tests if they are correct.
     * 1    Y     1   4
     * 0  R Y R   0 3 1 2
     *    0 1 2     0 1 2
     */
    @Test
    public void testAddStoneSomeMoves()
    {
        Position first = createPositionMock(1, 0, 3);
        Position second = createPositionMock(2, 0, 3);
        Position thierd = createPositionMock(1, 1, 3);
        Position forth = createPositionMock(0, 0, 3);
        
        Position nullFirst = createPositionMock(0, 1, 1);
        Position nullSecond = createPositionMock(2, 1, 1);
        
        Size.BOARD.unlog().changeSize(3, 2);
        Board board = createMockBuilder(Board.class).addMockedMethods("isOnBoard").withConstructor().createStrictMock();
        expect(board.isOnBoard(first)).andReturn(true);
        expect(board.isOnBoard(second)).andReturn(true);
        expect(board.isOnBoard(thierd)).andReturn(true);
        expect(board.isOnBoard(forth)).andReturn(true);
        replayAll();
        
        //add 4 stones and writh log down
        board.addStone(first);
        Position logFirst = board.getLastTurn();
        board.addStone(second);
        Position logSecond = board.getLastTurn();
        board.addStone(thierd);
        Position logThierd = board.getLastTurn();
        board.addStone(forth);
        Position logForth = board.getLastTurn();
        
        //assert the logged positions
        assertEquals("The position in the log must be equals to the added pos (first)", first, logFirst);
        assertEquals("The position in the log must be equals to the added pos (second)", second, logSecond);
        assertEquals("The position in the log must be equals to the added pos (thierd)", thierd, logThierd);
        assertEquals("The position in the log must be equals to the added pos (forth)", forth, logForth);
        
        //get all stones from board and assert them
        assertEquals("The Stone on the board must have the same color as the added one (yellow first)", Stone.YELLOW, board.getStone(first));
        assertEquals("The Stone on the board must have the same color as the added one (red second)", Stone.RED, board.getStone(second));
        assertEquals("The Stone on the board must have the same color as the added one (yellow thierd)", Stone.YELLOW, board.getStone(thierd));
        assertEquals("The Stone on the board must have the same color as the added one (yellow forth)", Stone.RED, board.getStone(forth));
        
        //check if there are empty positions
        assertNull("This stone must be null. (first)", board.getStone(nullFirst));
        assertNull("This stone must be null. (second)", board.getStone(nullSecond));
        
        verifyAll();
    }
    
    private Position createPositionMock(int col, int row, int expectTimes)
    {
        Position pos = createStrictMock(Position.class);
        
        for(int i = 0; i < expectTimes; i++)
        {
            expect(pos.getRow()).andReturn(row);
            expect(pos.getColumn()).andReturn(col);
        }
        
        return pos;
    }
    
    /**
     * Tests isOnBoard
     */
    @Test
    public void testIsOnBoard()
    {
        Size.BOARD.unlog().changeSize(1, 1);
        Board board = new Board();
        
        assertTrue("Position is on Board", board.isOnBoard(new Position(0, 0)));
        assertFalse("Positon north is not on Board", board.isOnBoard(new Position(0, 1)));
        assertFalse("Positon south is not on Board", board.isOnBoard(new Position(0, -1)));
        assertFalse("Positon east is not on Board", board.isOnBoard(new Position(1, 0)));
        assertFalse("Positon west is not on Board", board.isOnBoard(new Position(-1, 0)));
        assertFalse("Positon north east is not on Board", board.isOnBoard(new Position(1, 1)));
        assertFalse("Positon north west is not on Board", board.isOnBoard(new Position(-1, 1)));
        assertFalse("Positon south east is not on Board", board.isOnBoard(new Position(1, -1)));
        assertFalse("Positon south west is not on Board", board.isOnBoard(new Position(-1, -1)));
    }
    
    /**
     * Test that determinatDifferences registers zero changes.
     * 1   Y    1   Y
     * 0 R Y R  0 R Y R
     *   0 1 2    0 1 2
     */
    @Test
    public void testDeterminateDifferencesZero()
    {
        Size.BOARD.unlog().changeSize(3, 2);
        Board board = new Board();
        Board boardDif = new Board();
        
        Position first = new Position(1, 0);
        Position second = new Position(2, 0);
        Position thierd = new Position(1, 1);
        Position forth = new Position(0, 0);
        
        board.addStone(first);
        board.addStone(second);
        board.addStone(thierd);
        board.addStone(forth);
        
        boardDif.addStone(first);
        boardDif.addStone(second);
        boardDif.addStone(thierd);
        boardDif.addStone(forth);
        
        ArrayList<Difference> difs = board.determineDifferences(boardDif);
        
        assertEquals("There are no differences", 0, difs.size());
    }
    
    /**
     * Test that determinatDifferences registers that a stone was added.
     * 1   Y    1   Y Y
     * 0 R Y R  0 R Y R
     *   0 1 2    0 1 2
     */
    @Test
    public void testDeterminateDifferencesStoneAdd()
    {
        Size.BOARD.unlog().changeSize(3, 2);
        Board board = new Board();
        Board boardDif = new Board();
        
        Position first = new Position(1, 0);
        Position second = new Position(2, 0);
        Position thierd = new Position(1, 1);
        Position forth = new Position(0, 0);
        Position fivth = new Position(2, 1);
        
        board.addStone(first);
        board.addStone(second);
        board.addStone(thierd);
        board.addStone(forth);
        
        boardDif.addStone(first);
        boardDif.addStone(second);
        boardDif.addStone(thierd);
        boardDif.addStone(forth);
        boardDif.addStone(fivth);
        
        ArrayList<Difference> difs = board.determineDifferences(boardDif);
        
        assertEquals("There must be one difference", 1, difs.size());
        assertEquals("The found difference is in the wrong collumn", fivth.getColumn(), difs.get(0).getPosition().getColumn());
        assertEquals("The found difference is in the wrong row", fivth.getRow(), difs.get(0).getPosition().getRow());
        assertNull("The old stone must be null", difs.get(0).getOldStone());
        assertEquals("The new stone must be a yellow stone", Stone.YELLOW, difs.get(0).getNewStone());
    }
    
    /**
     * Test that determinatDifferences registers that a stone was switched.
     * 1   Y    1   Y
     * 0 R Y R  0 R Y Y
     *   0 1 2    0 1 2
     */
    @Test
    public void testDeterminateDifferencesStoneChanged()
    {
        Size.BOARD.unlog().changeSize(3, 2);
        Board board = new Board();
        Board boardDif = new Board();
        
        Position first = new Position(1, 0);
        Position second = new Position(2, 0);
        Position thierd = new Position(1, 1);
        Position forth = new Position(0, 0);
        
        board.addStone(first);
        board.addStone(second);
        board.addStone(thierd);
        board.addStone(forth);
        
        boardDif.addStone(first, Stone.YELLOW);
        boardDif.addStone(second, Stone.YELLOW);
        boardDif.addStone(thierd, Stone.YELLOW);
        boardDif.addStone(forth, Stone.RED);
        
        ArrayList<Difference> difs = board.determineDifferences(boardDif);
        
        assertEquals("There must be one difference", 1, difs.size());
        assertEquals("The found difference is in the wrong collumn", second.getColumn(), difs.get(0).getPosition().getColumn());
        assertEquals("The found difference is in the wrong row", second.getRow(), difs.get(0).getPosition().getRow());
        assertEquals("The old stone must be a red stone", Stone.RED, difs.get(0).getOldStone());
        assertEquals("The new stone must be a yellow stone", Stone.YELLOW, difs.get(0).getNewStone());
    }
    
    /**
     * Test that determinatDifferences registers two changes.
     * 1         1   Y
     * 0   Y R   0 Y Y R
     *   0 1 2     0 1 2
     */
    @Test
    public void testDeterminateDifferencesTwoStones()
    {
        Size.BOARD.unlog().changeSize(3, 2);
        Board board = new Board();
        Board boardDif = new Board();
        
        Position first = new Position(1, 0);
        Position second = new Position(2, 0);
        Position thierd = new Position(1, 1);
        Position forth = new Position(0, 0);
        
        board.addStone(first);
        board.addStone(second);
        
        boardDif.addStone(first, Stone.YELLOW);
        boardDif.addStone(second, Stone.RED);
        boardDif.addStone(thierd, Stone.YELLOW);
        boardDif.addStone(forth, Stone.YELLOW);
        
        ArrayList<Difference> difs = board.determineDifferences(boardDif);
        
        assertEquals("There must be one difference", 2, difs.size());
        assertEquals("The found difference is in the wrong collumn", thierd.getColumn(), difs.get(1).getPosition().getColumn());
        assertEquals("The found difference is in the wrong row", thierd.getRow(), difs.get(1).getPosition().getRow());
        assertEquals("The found difference forth is in the wrong collumn", forth.getColumn(), difs.get(0).getPosition().getColumn());
        assertEquals("The found difference forth is in the wrong row", forth.getRow(), difs.get(0).getPosition().getRow());
        assertNull("The old stone must be a null stone (thierd)", difs.get(1).getOldStone());
        assertEquals("The new stone must be a yellow stone (thierd)", Stone.YELLOW, difs.get(1).getNewStone());
        assertNull("The old stone must be a null stone (forth)", difs.get(0).getOldStone());
        assertEquals("The new stone must be a yellow stone (forth)", Stone.YELLOW, difs.get(0).getNewStone());
    }
    
    /**
     * Tests turnEndedGame if won is registerd
     */
    @Test
    public void testTurnEndedGameWin()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isStreakEndingGame()).andReturn(true);
        Board board = createMockBuilder(Board.class).addMockedMethods("getTurnCount", "searchLongestStreak").createStrictMock();
        expect(board.getTurnCount()).andReturn(1);
        expect(board.searchLongestStreak()).andReturn(streak);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be won in North South", Board.STATE_WIN, endState);
        verifyAll();
    }

    @Test
    public void testSearchLongestStreak() {
        Board b = new Board();
        b.addStone("A1");
        b.addStone("B1");
        b.addStone("A2");
        b.addStone("B2");
        b.addStone("A3");
        b.addStone("B3");

        Streak s = b.searchLongestStreak();

        assertEquals(3, s.getStreakLength());
        assertEquals(Direction.NORTH, s.getFirstDirection());
        assertEquals(Direction.SOUTH, s.getSecondDirection());

        b.addStone("A4");
        s = b.searchLongestStreak();

        assertEquals(4, s.getStreakLength());
    }

    /**
     * Tests turnEndedGame if remis is registerd.
     * TODO
     */
    @Test
    public void testTurnEndedGameRemis()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isStreakEndingGame()).andReturn(false);
        Size.BOARD.unlog().changeSize(7, 6);
        Board board = createMockBuilder(Board.class).addMockedMethods("searchLongestStreak", "getTurnCount").createStrictMock();
        expect(board.getTurnCount()).andReturn(42);
        expect(board.searchLongestStreak()).andReturn(streak);

        expect(board.getTurnCount()).andReturn(42);
        replayAll();

        int endState = board.turnEndedGame();

        assertEquals("State must be remi", Board.STATE_REMI, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if not Over is registerd.
     * TODO
     */
//    @Test
//    public void testTurnEndedGameNotOver()
//    {
//        Streak streak = createStrictMock(Streak.class);
//        expect(streak.isStreakEndingGame()).andReturn(false).times(4);
//        Size.BOARD.unlog().changeSize(7, 6);
//        Board board = createMockBuilder(Board.class).addMockedMethods("startCountingStreak", "getTurnCount").createStrictMock();
//        expect(board.getTurnCount()).andReturn(15);
//        expect(board.startCountingStreak(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
//        expect(board.startCountingStreak(Direction.EAST, Direction.WEST)).andReturn(streak);
//        expect(board.startCountingStreak(Direction.NORTH_EAST, Direction.SOUTH_WEST)).andReturn(streak);
//        expect(board.startCountingStreak(Direction.NORTH_WEST, Direction.SOUTH_EAST)).andReturn(streak);
//        expect(board.getTurnCount()).andReturn(15);
//        replayAll();
//
//        int endState = board.turnEndedGame();
//
//        assertEquals("State must be not over", Board.STATE_NOTYETOVER, endState);
//        verifyAll();
//    }
    
    /**
     * Tests countStreak with a complead row.
     */
    @Test
    public void testCountStreak()
    {
        Size.BOARD.unlog().changeSize(1, 4);
        Board board = new Board();
        
        Position first = new Position("A1");
        Position second = new Position("A2");
        Position third = new Position("A3");
        Position forth = new Position("A4");
        
        board.addStone(first, Stone.RED);
        board.addStone(second, Stone.RED);
        board.addStone(third, Stone.RED);
        board.addStone(forth, Stone.RED);
        
        Streak north = board.countStreak(Direction.NORTH, first, new Streak(4, first, Direction.NORTH));
        Streak south = board.countStreak(Direction.SOUTH, forth, new Streak(4, forth, Direction.SOUTH));
        
        assertEquals(4, north.getStreakLength());
        assertEquals(4, south.getStreakLength());
    }
    
    /**
     * Tests countStreak with only 2 stones in a row.
     */
    @Test
    public void testCountStreakHalf()
    {
        Size.BOARD.unlog().changeSize(1, 4);
        Board board = new Board();
        
        Position first = new Position(0, 0);
        Position second = new Position(0, 1);
        Position thierd = new Position(0, 2);
        Position forth = new Position(0, 3);
        
        board.addStone(first, Stone.RED);
        board.addStone(second, Stone.RED);
        
        Streak northHalf = board.countStreak(Direction.NORTH, first, new Streak(4, first, Direction.NORTH));
        
        board.addStone(thierd, Stone.YELLOW);
        board.addStone(forth, Stone.RED);
        
        Streak north = board.countStreak(Direction.NORTH, first, new Streak(4, first, Direction.NORTH));
        Streak south = board.countStreak(Direction.SOUTH, forth, new Streak(4, forth, Direction.SOUTH));
        
        assertEquals(2, northHalf.getStreakLength());
        assertEquals(2, north.getStreakLength());
        assertEquals(1, south.getStreakLength());
    }
    
    /**
     * Tests undoLastTurn with only one Stone.
     */
    @Test
    public void undoLastTurnOne()
    {
        Size.BOARD.unlog().changeSize(7, 6);
        Board board = new Board();
        Position first = new Position(0, 3);
        
        board.addStone(first);
        board.undoLastTurn();
        
        Position pos = board.getLastTurn();
        Stone lastStone = board.getLastStone();
        Stone stone = board.getStone(first);
        
        assertNull("Position must be null", pos);
        assertNull("Last Stone must be null", lastStone);
        assertNull("Stone on position must be null", stone);
    }
    
    /**
     * Test undoLastTurn with some Stones.
     */
    @Test
    public void undoLastTurnSome()
    {
        Size.BOARD.unlog().changeSize(7, 6);
        Board board = new Board();
        Position first = new Position(0, 3);
        Position second = new Position(0, 2);
        Position thierd = new Position(0, 1);
        
        board.addStone(first);
        board.addStone(second);
        board.addStone(thierd);
        board.undoLastTurn();
        
        assertEquals("Position must be second position", second, board.getLastTurn());
        assertEquals("Last Stone must be a red stone", Stone.RED, board.getLastStone());
        assertNull("Stone on the undone position must be null", board.getStone(thierd));
    }
    
    /**
     * Test of clone method, of class Board.
     */
    @Test
    public void testClone() {
        Board board = new Board();
        System.out.println("Error");
        board.addStone(new Position(10));
        System.out.println("error");
        board.addStone(new Position(12));
        
        Board clone = board.clone();
       
        assertFalse(board == clone);
        assertEquals(clone.getStone(new Position(10)), Stone.YELLOW);
        assertEquals(clone.getStone(new Position(12)), Stone.RED);     
        assertEquals(clone.getLastTurn(), new Position(12));
    }
    
    @Test
    public void testDetermineLowestFreeFieldInColumn() {
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(0, 1), Stone.RED);
        board.addStone(new Position(0, 2), Stone.RED);
        
        assertEquals(board.determineLowestFreeFieldInColumn(0).getRow(), 3);
        assertEquals(board.determineLowestFreeFieldInColumn(5).getRow(), 0);
    }
    
    @Test
    public void testDeterminePossiblePositions() {
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(0, 1), Stone.RED);
        board.addStone(new Position(0, 2), Stone.RED);
        board.addStone(new Position(1, 0), Stone.RED);
        board.addStone(new Position(2, 0), Stone.RED);
        board.addStone(new Position(3, 0), Stone.RED);
        
        ArrayList<Position> possiblePositions = board.determinePossiblePositions();
        
        assertEquals(possiblePositions.size(), 7);
        assertEquals(possiblePositions.get(0), new Position(0, 3));
        assertEquals(possiblePositions.get(1), new Position(1, 1));
        assertEquals(possiblePositions.get(2), new Position(2, 1));
        assertEquals(possiblePositions.get(3), new Position(3, 1));
        assertEquals(possiblePositions.get(4), new Position(4, 0));
        assertEquals(possiblePositions.get(5), new Position(5, 0));
        assertEquals(possiblePositions.get(6), new Position(6, 0));
    }
    
    @Test
    public void testAreBoardOccupationsEqual() {
        Board board = new Board();
        board.addStone(new Position(0, 0));
        board.addStone(new Position(41));
        
        Board board2 = new Board();
        board2.addStone(new Position(0, 0));
        board2.addStone(new Position(41));   
        
        assertTrue(board.areBoardOccupationsEqual(board2));
        
        board2.addStone(new Position(20));
        
        assertFalse(board.areBoardOccupationsEqual(board2));
    }
}
