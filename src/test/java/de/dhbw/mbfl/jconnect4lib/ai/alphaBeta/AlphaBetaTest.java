/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class AlphaBetaTest {
    
    public AlphaBetaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findBestTurn method, of class AlphaBeta.
     */
    @Test
    public void testFindBestTurn() {
        Board currentBoard = new Board();
        int maxAbsoluteDepth = 7;
        for (int i = 7; i < 13; i++) {
            AlphaBeta.findBestTurn(currentBoard, i);
        }
    }    
}
