/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.interrupt;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
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
    private static int PLAYER_WIN_NEXT = 0;
    private static int NOTHING_HAPPENS = 1;
    private static int PLAYER_WIN = 2;
    private static int AI_WIN = 3;
    
    
    @Override
    public Position calculateTurn(Board board, Stone stoneAI)
    {
        int bestTurn = -1;
        ArrayList<Position> bestTurns = new ArrayList();
        
        for(Position pos : this.getPosiblePositions(board))
        {
            int turn = this.calculateTurn(board, pos, stoneAI);
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
    private int calculateTurn(Board board, Position pos, Stone stoneAI)
    {
        Stone stonePlayer = (stoneAI == Stone.RED)? Stone.YELLOW : Stone.RED;
        
        board.addStone(pos, stoneAI);
        if(board.turnEndedGame() == Board.STATE_WIN)
        {
            board.undoLastTurn();
            return AI_WIN;
        }
        if(pos.getPosition() + 1 < Board.ROW_COUNT)
        {
            board.addStone(new Position(pos.getColumn(), pos.getPosition() + 1), stonePlayer);
            if(board.turnEndedGame() == Board.STATE_WIN)
            {
                board.undoLastTurn();
                return PLAYER_WIN_NEXT;
            }
            board.undoLastTurn();
        }
        board.undoLastTurn();
        
        board.addStone(pos, stonePlayer);
        if(board.turnEndedGame() == Board.STATE_WIN)
        {
            board.undoLastTurn();
            return PLAYER_WIN;
        }
        board.undoLastTurn();
        
        return NOTHING_HAPPENS;
    }
    
}
