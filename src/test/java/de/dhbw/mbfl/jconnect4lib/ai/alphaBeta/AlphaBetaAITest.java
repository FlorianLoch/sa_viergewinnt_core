package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import org.junit.Test;
import static org.junit.Assert.*;

public class AlphaBetaAITest {
    @Test
    public void testTurnGenerationIsIdempotent() {
        AlphaBetaAI instance = new AlphaBetaAI();
        Board board = new Board();
        Board clone;


        board.addStone(new Position("F1"));
        clone = board.clone();

        Position p = instance.calculateTurn(board);
        assertEquals(new Position("D1"), p);

        assertTrue(board.areBoardOccupationsEqual(clone));
    }


    @Test
    public void testCalculateEvenTurn() {
        AlphaBetaAI instance = new AlphaBetaAI();
        Board board = new Board();

        board.addStone(new Position("D1"));

        Position p1 = instance.calculateTurn(board, 1);
        assertEquals(new Position("D2"), p1);

        Position p2 = instance.calculateTurn(board, 2);
        assertEquals(new Position("D2"), p2);

        Position p3 = instance.calculateTurn(board, 3);
        assertEquals(new Position("D2"), p3);

        Position p4 = instance.calculateTurn(board, 4);
        assertEquals(new Position("D2"), p4);

        Position p5 = instance.calculateTurn(board, 5);
        assertEquals(new Position("D2"), p5);

        Position p6 = instance.calculateTurn(board, 6);
        assertEquals(new Position("C1"), p6);

        Position p7 = instance.calculateTurn(board, 7);
        assertEquals(new Position("D2"), p7);   //Because there is an odd number of fields taken into account in column c and d,
                                                //so the player will not get the same amount of tokens played as the other - therefore he will not get as much
                                                //stones into d as Yellow

        Position p8 = instance.calculateTurn(board, 8);
        assertEquals(new Position("C1"), p8);

        Position p9 = instance.calculateTurn(board, 9);
        assertEquals(new Position("D2"), p9);

        board.addStone(p8);

        Position p10 = instance.calculateTurn(board, 1);
        assertEquals(new Position("D2"), p9);

        board.addStone(p10);
        board.addStone(new Position("D3"));

        Position p11 = instance.calculateTurn(board, 1);
        assertEquals(new Position("D4"), p11);
    }

    @Test
    public void testCalculateOddTurn() {
        AlphaBetaAI instance = new AlphaBetaAI();
        Board board = new Board();

        Position p2 = instance.calculateTurn(board, 1);

        assertEquals(new Position("D1"), p2);

        board.addStone(new Position("B1"));

        Position p = instance.calculateTurn(board, 2);
        assertEquals(new Position("D1"), p);

        board.addStone(p);
        board.addStone(new Position("D2"));

        p = instance.calculateTurn(board, 4);
        assertEquals(new Position("D3"), p);
    }

    @Test
    public void testCalculateTurnEndSituation() {
//        AlphaBetaAI instance = new AlphaBetaAI();
//        Board board = new Board();
//
//        board.addStone(new Position("B1"), Stone.YELLOW);
//        board.addStone(new Position("D1"), Stone.YELLOW);
//        board.addStone(new Position("E1"), Stone.YELLOW);
//        board.addStone(new Position("F1"), Stone.YELLOW);
//        board.addStone(new Position("DT2"), Stone.YELLOW);
//        board.addStone(new Position("E2"), Stone.YELLOW);
//        board.addStone(new Position("G2"), Stone.YELLOW);
//        board.addStone(new Position("A3"), Stone.YELLOW);
//        board.addStone(new Position("E3"), Stone.YELLOW);
//        board.addStone(new Position("F3"), Stone.YELLOW);
//        board.addStone(new Position("C4"), Stone.YELLOW);
//        board.addStone(new Position("D4"), Stone.YELLOW);
//        board.addStone(new Position("G4"), Stone.YELLOW);
//        board.addStone(new Position("A5"), Stone.YELLOW);
//        board.addStone(new Position("B5"), Stone.YELLOW);
//        board.addStone(new Position("D5"), Stone.YELLOW);
//        board.addStone(new Position("E5"), Stone.YELLOW);
//        board.addStone(new Position("F5"), Stone.YELLOW);
//        board.addStone(new Position("G5"), Stone.YELLOW);
//        board.addStone(new Position("A6"), Stone.YELLOW);
//
//
//        board.addStone(new Position("A1"), Stone.RED);
//        board.addStone(new Position("A2"), Stone.RED);
//        board.addStone(new Position("C1"), Stone.RED);
//        board.addStone(new Position("C2"), Stone.RED);
//        board.addStone(new Position("C3"), Stone.RED);
//        board.addStone(new Position("A4"), Stone.RED);
//        board.addStone(new Position("B2"), Stone.RED);
//        board.addStone(new Position("B3"), Stone.RED);
//        board.addStone(new Position("B4"), Stone.RED);
//        board.addStone(new Position("B6"), Stone.RED);
//        board.addStone(new Position("C5"), Stone.RED);
//        board.addStone(new Position("C6"), Stone.RED);
//        board.addStone(new Position("D3"), Stone.RED);
//        board.addStone(new Position("D6"), Stone.RED);
//        board.addStone(new Position("E4"), Stone.RED);
//        board.addStone(new Position("F2"), Stone.RED);
//        board.addStone(new Position("F4"), Stone.RED);
//        board.addStone(new Position("F6"), Stone.RED);
//        board.addStone(new Position("G1"), Stone.RED);
//        board.addStone(new Position("G3"), Stone.RED);
//
//        Position p = instance.calculateTurn(board, Stone.RED);
//        assertEquals(p, new Position("G6"));
    }
}