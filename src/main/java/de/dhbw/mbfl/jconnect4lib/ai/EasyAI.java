/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Direction;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.ArrayList;

/**
 * This AI is going to try to interrupt the gameplay of the player. Gives
 * Posible moves a number: 0 => can't do player wins with next turn 1 => can do
 * nothing happens 2 => must do player wins 3 => must do AI win
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class EasyAI implements AI {

    //When changing these values, also the order of the checks perfomed in rankTurn() has to be adapted

    private static int WOULD_HELP_PLAYER = -1;
    private static int POTENTIALLY_STUPID_MOVE = 0; //Better do a stupid move than helping the player without beeing forced to. This makes one not look that retarted.
    private static int NOTHING_HAPPENS = 1;
    private static int PLAYER_WINS_NEXT = 2;
    private static int AI_WINS_NEXT = 3;
    private static int PLAYER_WINS = 4;
    private static int AI_WINS = 5;

    private int turnCounter = 0;
    private boolean random;

    public EasyAI() {
        this(true);
    }

    public EasyAI(boolean random) {
        this.random = random;
    }

    @Override
    public Position calculateTurn(Board board) {
        //Prevent "Oma"-Trick by setting to C or D at the first turn
        if (turnCounter == 0) {
            turnCounter = 1;
            int firstTry, secondTry;
            if (Math.random() >= 0.5) {
                firstTry = 2;
                secondTry = 3;
            } else {
                firstTry = 3;
                secondTry = 2;
            }
            if (board.getStone(new Position(firstTry)) == null) {
                return new Position(firstTry);
            } else {
                return new Position(secondTry);
            }
        }

        int bestTurn = -2;
        ArrayList<Position> bestTurns = new ArrayList();

        for (Position pos : board.determinePossiblePositions()) {
            int turn = this.rankTurn(board, pos);
            if (turn >= bestTurn) {
                if (turn > bestTurn) {
                    bestTurn = turn;
                    bestTurns.clear();
                }
                bestTurns.add(pos);
            }
        }

        this.turnCounter++;

        if (!this.random) return bestTurns.get(0); //Allways retuns the first "best" turn found - this makes the algorithm deterministic, great for testing
        
        return bestTurns.get((int) (Math.random() * bestTurns.size())); //Select turn by random, great for a more thrilling game
    }

    /**
     * Gives every move a number: 0 => can't do player wins with next turn 1 =>
     * can do nothing happens 2 => must do player wins 3 => must do I win
     *
     * @param board
     * @param pos
     * @return
     */
    private int rankTurn(Board board, Position pos) {
        Stone stoneAI = board.nextStone();
        
        // AI WINS
        board.addStone(pos);
        if (board.turnEndedGame() == Board.STATE_WIN) {
            board.undoLastTurn();
            return AI_WINS;
        }
        board.undoLastTurn();

        // PLAYER WINS
        board.addStone(pos);
        if (board.turnEndedGame() == Board.STATE_WIN) {
            board.undoLastTurn();
            return PLAYER_WINS;
        }
        board.undoLastTurn();

        // AI MIGHT WIN NEXT TURN (OR AI MIGHT HELP PLAYER)
        Position above = new Position(pos.getColumn(), pos.getRow() + 1);
        if (board.isOnBoard(above)) {
            board.addStone(pos);
            board.addStone(above);
            if (board.turnEndedGame() == Board.STATE_WIN) {
                board.undoLastTurn();
                board.undoLastTurn();
                return WOULD_HELP_PLAYER;
            }
            board.undoLastTurn();
            board.addStone(above);
            if (board.turnEndedGame() == Board.STATE_WIN) {
                board.undoLastTurn();
                board.undoLastTurn();
                return AI_WINS_NEXT;
            }
            board.undoLastTurn();
            board.undoLastTurn();
        }

        // PLAYER MIGHT WIN NEXT TURN
        if (board.isOnBoard(above)) {
            board.addStone(pos);
            board.addStone(above);
            if (board.turnEndedGame() == Board.STATE_WIN) {
                board.undoLastTurn();
                board.undoLastTurn();
                return PLAYER_WINS_NEXT;
            }
            board.undoLastTurn();
            board.undoLastTurn();
        }

        // Try to avoid a move when above the current field less then two (if there is an ai stone below) or three (if there is no ai stone beow) 
        // rows are available - because when this position in code is reached, at least two more stones above are needed two win (because of early returns used above).
        Position below = pos.newPosition(Direction.SOUTH);
        boolean sameColorBelow = false;

        if (board.isOnBoard(below) && board.getStone(below) == stoneAI) sameColorBelow = true;
        
        if (Size.BOARD.row() - pos.getRow() - 1 < 3 || (sameColorBelow && Size.BOARD.row() - pos.getRow() - 1 < 2)) {
            return POTENTIALLY_STUPID_MOVE;
        }

        // NOTHING special will HAPPEN
        return NOTHING_HAPPENS;
    }

}
