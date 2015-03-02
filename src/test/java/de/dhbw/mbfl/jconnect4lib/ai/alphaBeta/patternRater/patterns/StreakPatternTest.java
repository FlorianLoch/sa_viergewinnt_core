package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Streak;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreakPatternTest {

    @Test
    public void testFindAllStreaks() {
        Board b = new Board();
        b.addStone("C1");
        b.addStone("D1");
        b.addStone("C2");
        b.addStone("D2");
        b.addStone("D3");
        b.addStone("F1");
        b.addStone("C3");

        StreakPattern instance = new StreakPattern();
        RatingResult rating = instance.searchPatternImpl(b);

        assertEquals(3, rating.getRatingPlayerOne());
        assertEquals(1, rating.getRatingPlayerTwo());

        b.addStone("E1");

        rating = instance.searchPatternImpl(b);

        assertEquals(3, rating.getRatingPlayerOne());
        assertEquals(3, rating.getRatingPlayerTwo());
    }

}