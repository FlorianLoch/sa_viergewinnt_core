/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import de.dhbw.mbfl.jconnect4lib.exceptions.OutOfBoardException;
import de.dhbw.mbfl.jconnect4lib.exceptions.PositionOccupiedException;
import java.util.ArrayList;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class Board2Test {
    private Board b = new Board();
    
    private static final String GAMESITUATION_1 = "C1;D1$D2;A1$B1;A2$C2;A3$D3;A4";
    
    @Before
    public void setUp() {
        b = new Board();
    }

    /**
     * Test of clone method, of class Board.
     */
    @Test
    public void testClone() {
        b.addStone(new Position(10), Stone.RED);
        b.addStone(new Position(12), Stone.YELLOW);
        
        Board clone = b.clone();
       
        assertNotEquals(b, clone);
        assertEquals(clone.getStone(new Position(10)), Stone.RED);
        assertEquals(clone.getStone(new Position(12)), Stone.YELLOW);     
        assertEquals(clone.getLastTurn(), new Position(12));
    }

    /**
     * Test of addStone method, of class Board.
     */
    @Test
    public void testAddStone() {
        b.addStone(new Position(0, 0), Stone.RED);
        
        assertEquals(b.getStone(new Position(0)), Stone.RED);
        assertNull(b.getStone(new Position(2)));
        assertEquals(b.getLastTurn(), new Position(0));
    }

    @Test (expected = PositionOccupiedException.class)
    public void testAddStoneAlreadyOccupied() {
        b.addStone(new Position(0, 0), Stone.RED);
        b.addStone(new Position(0, 0), Stone.YELLOW);
    }    

    @Test (expected = OutOfBoardException.class)
    public void testAddStoneOutOfBoard() {
        b.addStone(new Position(42), Stone.RED);
    }        
    
    /**
     * Test of isOnBoard method, of class Board.
     */
    @Test
    public void testIsOnBoard() {
        Position p0 = new Position(-1);
        Position p1 = new Position(42);
        Position p2 = new Position(41);
        
        assertFalse(b.isOnBoard(p0));
        assertFalse(b.isOnBoard(p1));
        assertTrue(b.isOnBoard(p2));
    }

    /**
     * Test of determineDifferences method, of class Board.
     */
    @Test
    public void testDetermineDifferences() {
        Board b2 = new Board();
        b.addStone(new Position(20), Stone.RED);
        b2.addStone(new Position(10), Stone.YELLOW);
        b2.addStone(new Position(20), Stone.RED);
        b2.addStone(new Position(30), Stone.RED);
        
        ArrayList<Difference> dif = b.determineDifferences(b2);
        
        assertThat(dif.get(0).getNewStone(), Is.is(Stone.YELLOW));
        assertThat(dif.get(1).getNewStone(), Is.is(Stone.RED));
        assertThat(dif.size(), Is.is(2));
    }

    /**
     * Test of turnEndedGame method, of class Board.
     */
    @Test
    public void testTurnEndedGame() {
        //Test some border cases
    }

    /**
     * Test of countStreak method, of class Board.
     */
    @Test
    public void testCountStreak() {
        //testCountStreak() always counts beginning from the last added stone
        reconstructBoard(this.b, GAMESITUATION_1);
        
        Streak s1 = b.countStreak(Direction.SOUTH, b.getLastTurn(), new Streak(4, 1));
        
        assertTrue(s1.isEnd());
    }

    /**
     * Test of undoLastTurn method, of class Board.
     */
    @Test
    public void testUndoLastTurn() {
        Board clone = b.clone();
        b.addStone(new Position(0), Stone.RED);
        
        assertEquals(clone.determineDifferences(b).size(), 1);
        
        b.undoLastTurn();
        
        assertEquals(clone.determineDifferences(b).size(), 0);
    }

    /**
     * Test of toString method, of class Board.
     */
    @Test
    public void testToString() {
    }
    
    public static void reconstructBoard(Board board, String logStr) {
        int addedStones = 0;
        
        String[] lines = logStr.split("\\$");
        for (String line : lines) {
            String[] positions = line.split(";");
            for (String positionStr : positions) {
                if (positionStr.isEmpty()) continue;
                
                board.addStone(Position.parsePosition(positionStr), (addedStones % 2 == 1) ? Stone.RED : Stone.YELLOW);
                addedStones++;
            }
        }
    }
}
