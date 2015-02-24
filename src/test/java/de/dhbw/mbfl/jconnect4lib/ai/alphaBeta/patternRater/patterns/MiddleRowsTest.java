package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class MiddleRowsTest {

    @Test
    public void testMiddleRowRater() {
        PatternDetector instance = new MiddleRows();
        Board board = new Board();
        board.addStone("D1");
        board.addStone("D2");
        board.addStone("D3");
        board.addStone("D4");
        board.addStone("C1");
        board.addStone("D5");

        RatingResult r = instance.searchPattern(board);

        assertEquals(8, r.getRatingPlayerOne());
        assertEquals(3, r.getRatingPlayerTwo());
    }

}