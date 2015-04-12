/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
class DefaultNextTurnsComputer implements NextTurnsComputer {
    private int[] sequence = null;

    public DefaultNextTurnsComputer() {
        this.sequence = computeSequence();
    }

    private int[] computeSequence() {
        int[] sequence = new int[Size.BOARD.column()];
        int middle = Size.BOARD.column() / 2;   //floor, middle is used as an index, therefore the left side's size is always >= the right side's size
                                                //so if the column count is odd the loop handles both sites equally, if it is even right handling gets skipped once at the end

        sequence[0] = middle;

        for (int i = 1; i < sequence.length; i++) {
            int step = (int) Math.ceil(i / 2.0); //ceil -> 1, 1, 2, 2, 3, 3

            if (1 == i % 2) {
                //Handle left half
                sequence[i] = middle - step;
                continue;
            }

            //Handle right halt
            sequence[i] = middle + step;
        }

        return sequence;
    }

    @Override
    public LinkedList<Board> computeNextTurns(Board currentBoard) {
        LinkedList<Board> possibleTurns = new LinkedList<Board>();

        if (currentBoard.turnEndedGame() != Board.STATE_NOTYETOVER) return possibleTurns;

        //Add a stone in each column
        for (int i = 0; i < Size.BOARD.column(); i++) {
            Position lowestFreeFieldInColumn = currentBoard.determineLowestFreeFieldInColumn(this.sequence[i]);

            if (null == lowestFreeFieldInColumn) continue;

            Board clonedBoard = currentBoard.clone();
            clonedBoard.addStone(lowestFreeFieldInColumn);

            if (Board.STATE_WIN == clonedBoard.turnEndedGame()) {
                possibleTurns.addFirst(clonedBoard);
                continue;
            }

            possibleTurns.addLast(clonedBoard);
        }

        return possibleTurns;
    }
}
