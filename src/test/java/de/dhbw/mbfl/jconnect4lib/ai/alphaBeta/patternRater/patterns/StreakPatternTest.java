package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Streak;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

public class StreakPatternTest extends EasyMockSupport {
    private StreakPattern instance;

    @Before
    public void setup() {
        this.instance = new StreakPattern();
    }

    @Test
    public void testRating() {
        Board board = new Board();
        board.addStone("C1");
        board.addStone("D1");

        board.addStone("C2");
        board.addStone("D2");

        board.addStone("D3");
        board.addStone("F1");

        RatingResult rating = this.instance.searchPatternImpl(board);

        assertEquals(2, rating.getRatingPlayerOne());
        assertEquals(0, rating.getRatingPlayerTwo()); //Because this streaks cannot be enlarged anymore - it is fenced by yellow stones
    }

    @Test
    public void testFindAllStreaks() {
        Board board = new Board();
        board.addStone("C1");
        board.addStone("D1");

        board.addStone("C2");
        board.addStone("D2");

        board.addStone("D3");
        board.addStone("F1");

        board.addStone("C3");

        ArrayList<Streak> streaksPlayerOne = this.instance.findAllStreaks(board, new Stack<Position>(){{
            add(new Position("C1"));
            add(new Position("C2"));
            add(new Position("D3"));
            add(new Position("C3"));
        }});
        assertEquals(3, streaksPlayerOne.size());

        Stack<Position> positionsPlayerTwo = new Stack<Position>(){{
            add(new Position("D1"));
            add(new Position("D2"));
            add(new Position("F1"));
        }};
        ArrayList<Streak> streaksPlayerTwo = this.instance.findAllStreaks(board, positionsPlayerTwo);
        assertEquals(1, streaksPlayerTwo.size());

        board.addStone("E1");

        streaksPlayerTwo = this.instance.findAllStreaks(board, positionsPlayerTwo);
        assertEquals(3, streaksPlayerTwo.size());
    }

    @Test
    public void testCopyStack() {
        Stack<String> original = new Stack<String>() {{
            add("Hello");
            add("wonderful");
            add("World!");
        }};

        Stack<String> copy = new Stack<String>();

        StreakPattern.copyStack(original, copy);

        assertEquals(3, original.size());
        assertEquals(3, copy.size());

        while (!original.empty()) {
            String origItem = original.pop();
            String copyItem = copy.pop();

            assertEquals(origItem, copyItem);
        }

        assertEquals(0, original.size());
        assertEquals(0, copy.size());
    }

    @Test
    public void filterAllMaximizableStreaks() {
        Streak streak1 = createMockBuilder(Streak.class).addMockedMethods("couldBeMaximized").createStrictMock();
        expect(streak1.couldBeMaximized()).andReturn(true);

        Streak streak2 = createMockBuilder(Streak.class).addMockedMethods("couldBeMaximized").createStrictMock();
        expect(streak2.couldBeMaximized()).andReturn(false);

        Streak streak3 = createMockBuilder(Streak.class).addMockedMethods("couldBeMaximized").createStrictMock();
        expect(streak3.couldBeMaximized()).andReturn(true);

        replayAll();

        ArrayList<Streak> streaks = new ArrayList<Streak>();
        streaks.add(streak1);
        streaks.add(streak2);
        streaks.add(streak3);

        ArrayList<Streak> filteredStreaks = this.instance.filterAllMaximizableStreaks(streaks);

        assertEquals(2, filteredStreaks.size());
    }

}