/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

import java.util.BitSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class BoardIdentityTest {
    @Test
    public void testConstructor() {
        String expectation = "{0, 10, 83}";
           
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(5, 0), Stone.RED);
        board.addStone(new Position(41), Stone.YELLOW);
        
        BoardIdentity instance = new BoardIdentity(board);

        assertEquals(expectation, instance.toString());
    }
    
    /**
     * Test of reconstructBoard method, of class BoardIdentity.
     */
    @Test
    public void testReconstructBoard() {
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(20), Stone.RED);
        board.addStone(new Position(41), Stone.YELLOW);
        
        BoardIdentity instance = new BoardIdentity(board);
        
        Board reconstructedBoard = instance.reconstructBoard();
        
        int i = 0;
        for (Position p : reconstructedBoard) {
            Stone s = reconstructedBoard.getStone(p);
            if (i == 0 || i == 20) assertEquals(s, Stone.RED);
            else if (i == 41) assertEquals(s, Stone.YELLOW);
            else assertEquals(s, null);
            
            i++;
        }
    }

    @Test
    public void testEquals() {
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(20), Stone.RED);
        board.addStone(new Position(41), Stone.YELLOW);
        
        Board secondBoard = board.clone();
        
        BoardIdentity instance = new BoardIdentity(board);
        BoardIdentity instanceSecondBoard = new BoardIdentity(secondBoard);
        
        assertEquals(instance, instanceSecondBoard);
        
        secondBoard.addStone(new Position(8), Stone.YELLOW);
        instanceSecondBoard = new BoardIdentity(secondBoard);
        
        assertNotEquals(instance, instanceSecondBoard);
    }
    
    @Test
    public void testConstructorAndReconstruction() {
        Board board = new Board();
        board.addStone(new Position(0, 0), Stone.RED);
        board.addStone(new Position(20), Stone.RED);
        board.addStone(new Position(41), Stone.YELLOW);
        
        BoardIdentity instance = new BoardIdentity(board);
        
        assertTrue(instance.reconstructBoard().areBoardOccupationsEqual(board));
    }
}
