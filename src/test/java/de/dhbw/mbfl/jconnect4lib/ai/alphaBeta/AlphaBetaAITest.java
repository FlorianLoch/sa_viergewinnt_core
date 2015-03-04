package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlphaBetaAITest {

    //THIS ARE (MORE OR LESS) REGRESSION TESTS

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

    // TODO Re-enable regression tests

//    @Test
//    public void testCalculateEvenTurn() {
//        AlphaBetaAI instance = new AlphaBetaAI();
//        Board board = new Board();
//
//        board.addStone(new Position("D1"));
//
//        Position p1 = instance.calculateTurn(board, 1);
//        assertEquals(new Position("D2"), p1);
//
//        Position p2 = instance.calculateTurn(board, 2);
//        assertEquals(new Position("C1"), p2);
//
//        Position p3 = instance.calculateTurn(board, 3);
//        assertEquals(new Position("C1"), p3);
//
//        Position p4 = instance.calculateTurn(board, 4);
//        assertEquals(new Position("B1"), p4);
//
//        Position p5 = instance.calculateTurn(board, 5);
//        assertEquals(new Position("B1"), p5);
//
//        Position p6 = instance.calculateTurn(board, 6);
//        assertEquals(new Position("C1"), p6);
//
//        Position p7 = instance.calculateTurn(board, 7);
//        assertEquals(new Position("B1"), p7);   //Because there is an odd number of fields taken into account in column c and d,
//                                                //so the player will not get the same amount of tokens played as the other - therefore he will not get as much
//                                                //stones into d as Yellow
//
//        Position p8 = instance.calculateTurn(board, 8);
//        assertEquals(new Position("B1"), p8);
//
//        Position p9 = instance.calculateTurn(board, 9);
//        assertEquals(new Position("B1"), p9);
//
//        board.addStone(p8);
//
//        Position p10 = instance.calculateTurn(board, 1);
//        assertEquals(new Position("B1"), p9);
//
//        board.addStone(p10);
//        board.addStone(new Position("D3"));
//
//        Position p11 = instance.calculateTurn(board, 1);
//        assertEquals(new Position("C1"), p11);
//    }
//
//    @Test
//    public void testCalculateOddTurn() {
//        AlphaBetaAI instance = new AlphaBetaAI();
//        Board board = new Board();
//
//        Position p2 = instance.calculateTurn(board, 1);
//
//        assertEquals(new Position("D1"), p2);
//
//        board.addStone(new Position("B1"));
//
//        Position p = instance.calculateTurn(board, 2);
//        assertEquals(new Position("D1"), p);
//
//        board.addStone(p);
//        board.addStone(new Position("D2"));
//
//        p = instance.calculateTurn(board, 4);
//        assertEquals(new Position("D3"), p);
//    }

}