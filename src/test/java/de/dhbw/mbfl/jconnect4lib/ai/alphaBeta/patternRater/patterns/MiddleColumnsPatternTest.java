package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternDetector;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class MiddleColumnsPatternTest {

    @Test
    public void testMiddleColumnsRater() {
        PatternDetector instance = new MiddleColumnsPattern();
        Board board = new Board();
        board.addStone("B1");
        board.addStone("C1");
        board.addStone("C2");
        board.addStone("D1");
        board.addStone("D2");
        board.addStone("G1");

        RatingResult r = instance.searchPattern(board);

        assertEquals(6, r.getRatingPlayerOne());
        assertEquals(5, r.getRatingPlayerTwo());
    }

}