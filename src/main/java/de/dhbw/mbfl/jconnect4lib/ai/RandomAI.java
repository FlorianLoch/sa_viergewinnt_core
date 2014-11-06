package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 * A AI which place randomly a Stone into the Board.
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class RandomAI implements AI
{

    /**
     * Calculates randomly a turn wich the computer do.
     *
     * @param board
     * @return position of the made turn.
     */
    @Override
    public Position calculateTurn(Board board, Stone stoneAI)
    {
        int col = -1;
        int row = -1;

        while(col < 0)
        {
            col = this.getRandomColumn();
            row = this.calculateRow(col, board);
            if(row == -1)
            {
                col = -1;
            }
        }

        return new Position(col, row);
    }

    /**
     * Generats randomly a column.
     *
     * @return column
     */
    private int getRandomColumn()
    {
        return (int) (Math.random() * Board.COLUMN_COUNT);
    }

    /**
     * Gives the Row where the Stone can be places. If the Column is full -1
     * will be given.
     *
     * @param col
     * @param board
     * @return row
     */
    private int calculateRow(int col, Board board)
    {
        for(int i = 0; i < Board.ROW_COUNT; i++)
        {
            if(board.getStone(new Position(col, i)) == null)
            {
                return i;
            }
        }

        return -1;
    }

}
