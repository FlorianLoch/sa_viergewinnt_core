/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.ArrayList;

/**
 * This AI is going to try to interrupt the gameplay of the player.
 * Gives Posible moves a number:
 *  0 => can't do player wins with next turn
 *  1 => can do nothing happens
 *  2 => must do player wins
 *  3 => must do AI win
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class EasyAI implements AI
{
    //When changing these values, also the order of the checks perfomed in rankTurn() has to be adapted
    private static int POTENTIALLY_STUPID_MOVE = -1;
    private static int WOULD_HELP_PLAYER = 0;
    private static int NOTHING_HAPPENS = 1;
    private static int PLAYER_WIN_NEXT = 2;
    private static int AI_WINS_NEXT = 3;     
    private static int PLAYER_WIN = 4;
    private static int AI_WIN = 5;
    
    private int turn = 0;
    
    @Override
    public Position calculateTurn(Board board, Stone stoneAI)
    {
        //Prevent "Oma"-Trick by setting to C or D at the first turn
        if (turn == 0) {
            turn = 1;
            int firstTry, secondTry;
            if (Math.random() >= 0.5) {
                firstTry = 2;
                secondTry = 3;
            }
            else {
                firstTry = 3;
                secondTry = 2;
            }
            if (board.getStone(new Position(firstTry)) == null) {
                return new Position(firstTry);
            }
            else {
                return new Position(secondTry);
            }
        }
        
        int bestTurn = -2;
        ArrayList<Position> bestTurns = new ArrayList();
        
        for(Position pos : this.getPosiblePositions(board))
        {
            int turn = this.rankTurn(board, pos, stoneAI);
            if(turn >= bestTurn)
            {
                if(turn > bestTurn)
                {
                    bestTurn = turn;
                    bestTurns.clear();
                }
                bestTurns.add(pos);
            }
        }
        
        this.turn++;
        
        return bestTurns.get((int) (Math.random() * bestTurns.size()));
    }

    private ArrayList<Position> getPosiblePositions(Board board)
    {
        ArrayList<Position> posiblePositions = new ArrayList();
        for(int i = 0; i < Board.COLUMN_COUNT; i++)
        {
            int row = this.calculateRow(i, board);
            if(row >= 0) 
            {
                posiblePositions.add(new Position(i, row));
            }
        }
        return posiblePositions;
    }
    
    /**
     * Gives the Row where the Stone can be places. If the Column is full -1
     * will be given.
     *
     * @param col
     * @param board
     *
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
    
    /**
     * Gives every move a number:
     *  0 => can't do player wins with next turn
     *  1 => can do nothing happens
     *  2 => must do player wins
     *  3 => must do I win
     * @param board
     * @param pos
     * @return 
     */
    private int rankTurn(Board board, Position pos, Stone stoneAI)
    {
        Stone stonePlayer = (stoneAI == Stone.RED)? Stone.YELLOW : Stone.RED;
        
        // AI WINS
        board.addStone(pos, stoneAI);
        if(board.turnEndedGame() == Board.STATE_WIN)
        {
            board.undoLastTurn();
            return AI_WIN;
        }
        board.undoLastTurn();
        
        // PLAYER WINS
        board.addStone(pos, stonePlayer);
        if(board.turnEndedGame() == Board.STATE_WIN)
        {
            board.undoLastTurn();
            return PLAYER_WIN;
        }
        board.undoLastTurn();
        
        // AI MIGHT WIN NEXT TURN (OR AI MIGHT HELP PLAYER)
        Position above = new Position(pos.getColumn(), pos.getRow() + 1);
        if(board.isOnBoard(above))
        {
            board.addStone(pos, stoneAI);
            board.addStone(above, stonePlayer);
            if (board.turnEndedGame() == Board.STATE_WIN) {
                board.undoLastTurn();
                board.undoLastTurn();
                return WOULD_HELP_PLAYER;
            }
            board.undoLastTurn();
            board.addStone(above, stoneAI);
            if(board.turnEndedGame() == Board.STATE_WIN)
            {
                board.undoLastTurn();
                board.undoLastTurn();
                return AI_WINS_NEXT;
            }
            board.undoLastTurn();
            board.undoLastTurn();
        }
        
        // PLAYER MIGHT WIN NEXT TURN
        if(board.isOnBoard(above))
        {
            board.addStone(pos, stonePlayer);
            board.addStone(above, stonePlayer);
            if(board.turnEndedGame() == Board.STATE_WIN)
            {
                board.undoLastTurn();
                board.undoLastTurn();
                return PLAYER_WIN_NEXT;
            }
            board.undoLastTurn();
            board.undoLastTurn();
        }        
        
        // Try to avoid a move when above the current field less then two rows are available - because when this position in code is reached, at least two more stones above are needed two win.
        // If there is no stone of same color below even 3 rows above would be needed - but we do not want to restrict this case because otherwise AI would also try to place stones in the lower rows
        if (Board.ROW_COUNT - pos.getRow() - 1 < 2) return POTENTIALLY_STUPID_MOVE;
        
        // NOTHING special will HAPPEN
        return NOTHING_HAPPENS;
    }
    
}