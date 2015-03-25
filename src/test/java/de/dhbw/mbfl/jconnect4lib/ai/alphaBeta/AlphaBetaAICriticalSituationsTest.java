package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.BoardUtils;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Created by florian on 02.03.15.
 */
@RunWith(Parameterized.class)
public class AlphaBetaAICriticalSituationsTest {
    private String boardAllocation;
    private int expectedState;
    private Position expectedProposedTurn;

    private Board board;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"D1;C1$D2;C2$D3;D4$C3;D5$E1;D6$F1", Board.STATE_NOTYETOVER, new Position("G1")},     // Situation is as described here: https://github.com/FlorianLoch/sa_viergewinnt_server/issues/3
                {"A1;D1$D2;E1$C1;D3$D4;B1$B2;C2$C3", Board.STATE_WIN, null},
                {"E1;D1$C1;D2$D3;D4$E2;D5$F1;D6$C2;B1$C3;C4$E3;E4$E5;C5$C6;E6$G1;A1$G2;A2$G3;G4$A3;A4$A5;A6$G5;G6", Board.STATE_NOTYETOVER, new Position("F2")},
                {"D1;C1$F1;E1$E2;E3$E4;F2$B1;D2$D3;D4$C2;G1", Board.STATE_WIN, null},
                {"D1;C1$E1;B1$F1;G1$E2;E3$D2;F2$E4;D3$D4;F3$B2;B3$F4;D5$A1;D6$B4;E5$F5;A2$A3;A4$A5;A6$B5;B6$E6;F6$G2;G3", Board.STATE_WIN, null}
        });
    }

    public AlphaBetaAICriticalSituationsTest(String boardAllocation, int expectedState, Position expectedProposedTurn) {
        this.boardAllocation = boardAllocation;
        this.expectedState = expectedState;
        this.expectedProposedTurn = expectedProposedTurn;

        this.board = new Board();

        BoardUtils.addStonesToBoard(this.board, this.boardAllocation);
    }

    @Test
    public void testSituation() {
        assertEquals(this.expectedState, this.board.turnEndedGame());

        if (this.expectedProposedTurn != null) {
            AlphaBetaAI instance = new AlphaBetaAI();
            Position proposedTurn = instance.calculateTurn(this.board, 8);

            assertEquals(this.expectedProposedTurn, proposedTurn);
        }
    }
}
