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

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class BoardTest extends EasyMockSupport
{
    
    /**
     * Tests if the method addStone throws an OutOfBoard exeption
     */
    @Test
    public void testAddStoneOutOfBoard()
    {
        Position pos = createStrictMock(Position.class);
        Board board = createMockBuilder(Board.class).addMockedMethod("isOnBoard").createStrictMock();
        
        expect(board.isOnBoard(pos)).andReturn(false);
        replayAll();
        
        try {
            board.addStone(pos, Stone.RED);
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
        Board board = createMockBuilder(Board.class).addMockedMethods("isOnBoard", "getStone").createStrictMock();
        
        expect(board.isOnBoard(pos)).andReturn(true);
        expect(board.getStone(pos)).andReturn(Stone.RED);
        expect(board.isOnBoard(pos)).andReturn(true);
        expect(board.getStone(pos)).andReturn(Stone.YELLOW);
        replayAll();
        
        try {
            board.addStone(pos, Stone.RED);
            fail("Expected an PositionOccupiedException to be thrown (red)");
        } catch (PositionOccupiedException ex) {
            assertThat(ex.getMessage(), containsString(PositionOccupiedException.MSG.replace("##pos##", "EasyMock for class de.dhbw.mbfl.jconnect4lib.board.Position")));
        }
        
        try {
            board.addStone(pos, Stone.YELLOW);
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
        
        board.addStone(pos, Stone.RED);
        Position posLog = board.getLastTurn();
        Stone stoneAdded = board.getStone(pos);
        Stone nullStone = board.getStone(posNull);
        
        assertEquals("The position in the log must be equas to the addes pos.", pos, posLog);
        assertEquals("The Stone on the board must have the same color as the added one.", Stone.RED, stoneAdded);
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
        board.addStone(first, Stone.YELLOW);
        Position logFirst = board.getLastTurn();
        board.addStone(second, Stone.RED);
        Position logSecond = board.getLastTurn();
        board.addStone(thierd, Stone.YELLOW);
        Position logThierd = board.getLastTurn();
        board.addStone(forth, Stone.RED);
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
        
        board.addStone(first, Stone.YELLOW);
        board.addStone(second, Stone.RED);
        board.addStone(thierd, Stone.YELLOW);
        board.addStone(forth, Stone.RED);
        
        boardDif.addStone(first, Stone.YELLOW);
        boardDif.addStone(second, Stone.RED);
        boardDif.addStone(thierd, Stone.YELLOW);
        boardDif.addStone(forth, Stone.RED);
        
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
        
        board.addStone(first, Stone.YELLOW);
        board.addStone(second, Stone.RED);
        board.addStone(thierd, Stone.YELLOW);
        board.addStone(forth, Stone.RED);
        
        boardDif.addStone(first, Stone.YELLOW);
        boardDif.addStone(second, Stone.RED);
        boardDif.addStone(thierd, Stone.YELLOW);
        boardDif.addStone(forth, Stone.RED);
        boardDif.addStone(fivth, Stone.YELLOW);
        
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
        
        board.addStone(first, Stone.YELLOW);
        board.addStone(second, Stone.RED);
        board.addStone(thierd, Stone.YELLOW);
        board.addStone(forth, Stone.RED);
        
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
        
        board.addStone(first, Stone.YELLOW);
        board.addStone(second, Stone.RED);
        
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
     * Tests turnEndedGame if won is registerd in Direction North and South.
     */
    @Test
    public void testTurnEndedGameWinNorthSouth()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(true);
        Board board = createMockBuilder(Board.class).addMockedMethod("turnEndGame").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be won in North South", Board.STATE_WIN, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if won is registerd in Direction East and West.
     */
    @Test
    public void testTurnEndedGameWinEastWest()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(false);
        expect(streak.isEnd()).andReturn(true);
        Board board = createMockBuilder(Board.class).addMockedMethod("turnEndGame").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        expect(board.turnEndGame(Direction.EAST, Direction.WEST)).andReturn(streak);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be won in East West", Board.STATE_WIN, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if won is registerd in Direction NorthEast and SouthWest.
     */
    @Test
    public void testTurnEndedGameWinNorthEastSouthWest()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(false).times(2);
        expect(streak.isEnd()).andReturn(true);
        Board board = createMockBuilder(Board.class).addMockedMethod("turnEndGame").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        expect(board.turnEndGame(Direction.EAST, Direction.WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_EAST, Direction.SOUTH_WEST)).andReturn(streak);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be won in North-East South-West", Board.STATE_WIN, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if won is registerd in Direction NorthWest and SouthEast.
     */
    @Test
    public void testTurnEndedGameWinNorthWestSouthEast()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(false).times(3);
        expect(streak.isEnd()).andReturn(true);
        Board board = createMockBuilder(Board.class).addMockedMethod("turnEndGame").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        expect(board.turnEndGame(Direction.EAST, Direction.WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_EAST, Direction.SOUTH_WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_WEST, Direction.SOUTH_EAST)).andReturn(streak);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be won in North-West South-East", Board.STATE_WIN, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if remi is registerd.
     */
    @Test
    public void testTurnEndedGameRemi()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(false).times(4);
        Size.BOARD.unlog().changeSize(7, 6);
        Board board = createMockBuilder(Board.class).addMockedMethods("turnEndGame", "getTurnCount").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        expect(board.turnEndGame(Direction.EAST, Direction.WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_EAST, Direction.SOUTH_WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_WEST, Direction.SOUTH_EAST)).andReturn(streak);
        expect(board.getTurnCount()).andReturn(42);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be remi", Board.STATE_REMI, endState);
        verifyAll();
    }
    
    /**
     * Tests turnEndedGame if not Over is registerd.
     */
    @Test
    public void testTurnEndedGameNotOver()
    {
        Streak streak = createStrictMock(Streak.class);
        expect(streak.isEnd()).andReturn(false).times(4);
        Size.BOARD.unlog().changeSize(7, 6);
        Board board = createMockBuilder(Board.class).addMockedMethods("turnEndGame", "getTurnCount").createStrictMock();
        expect(board.turnEndGame(Direction.NORTH, Direction.SOUTH)).andReturn(streak);
        expect(board.turnEndGame(Direction.EAST, Direction.WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_EAST, Direction.SOUTH_WEST)).andReturn(streak);
        expect(board.turnEndGame(Direction.NORTH_WEST, Direction.SOUTH_EAST)).andReturn(streak);
        expect(board.getTurnCount()).andReturn(15);
        replayAll();
        
        int endState = board.turnEndedGame();
        
        assertEquals("State must be not over", Board.STATE_NOTYETOVER, endState);
        verifyAll();
    }
    
    /**
     * Tests countStreak with a complead row.
     */
    @Test
    public void testCountStreak()
    {
        Size.BOARD.unlog().changeSize(1, 4);
        Board board = new Board();
        
        Position first = new Position(0, 0);
        Position second = new Position(0, 1);
        Position thierd = new Position(0, 2);
        Position forth = new Position(0, 3);
        
        board.addStone(first, Stone.RED);
        board.addStone(second, Stone.RED);
        board.addStone(thierd, Stone.RED);
        board.addStone(forth, Stone.RED);
        
        Streak north = board.countStreak(Direction.NORTH, first, new Streak(4, 1));
        Streak south = board.countStreak(Direction.SOUTH, forth, new Streak(4, 1));
        
        assertEquals(4, north.getStreak());
        assertEquals(4, south.getStreak());
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
        
        Streak northHalf = board.countStreak(Direction.NORTH, first, new Streak(4, 1));
        
        board.addStone(thierd, Stone.YELLOW);
        board.addStone(forth, Stone.RED);
        
        Streak north = board.countStreak(Direction.NORTH, first, new Streak(4, 1));
        Streak south = board.countStreak(Direction.SOUTH, forth, new Streak(4, 1));
        
        assertEquals(2, northHalf.getStreak());
        assertEquals(2, north.getStreak());
        assertEquals(1, south.getStreak());
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
        
        board.addStone(first, Stone.RED);
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
        
        board.addStone(first, Stone.YELLOW);
        board.addStone(second, Stone.RED);
        board.addStone(thierd, Stone.YELLOW);
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
        board.addStone(new Position(10), Stone.RED);
        board.addStone(new Position(12), Stone.YELLOW);
        
        Board clone = board.clone();
       
        assertNotEquals(board, clone);
        assertEquals(clone.getStone(new Position(10)), Stone.RED);
        assertEquals(clone.getStone(new Position(12)), Stone.YELLOW);     
        assertEquals(clone.getLastTurn(), new Position(12));
    }
}
