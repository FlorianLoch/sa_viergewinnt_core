package de.dhbw.mbfl.jconnect4lib.board;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardUtilsTest {

    @Test
    public void testAddStonesToBoard() {
        Board oldSchool = new Board();
        oldSchool.addStone("C1");
        oldSchool.addStone("D1");
        oldSchool.addStone("D2");
        oldSchool.addStone("D3");

        final String ALLOCATION = "C1;D1$D2;D3";
        Board theNewWay = new Board();
        BoardUtils.addStonesToBoard(theNewWay, ALLOCATION);

        assertTrue("Both allocations should be the same", theNewWay.areBoardOccupationsEqual(oldSchool));
    }

    @Test
    public void testAddStoneToBoardWithOddTurnCount() {
        Board oldSchool = new Board();
        oldSchool.addStone("C1");
        oldSchool.addStone("D1");
        oldSchool.addStone("D2");
        oldSchool.addStone("D3");
        oldSchool.addStone("D4");

        final String ALLOCATION = "C1;D1$D2;D3$D4";
        Board theNewWay = new Board();
        BoardUtils.addStonesToBoard(theNewWay, ALLOCATION);

        assertTrue("Both allocations should be the same", theNewWay.areBoardOccupationsEqual(oldSchool));
    }
}