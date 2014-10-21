package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.validators.Validator;
import java.util.ArrayList;

/**
 *
 * @author Maurice Busch & Florian Loch
 */
public class Game {
    private Board board;
    private ArrayList<Validator> validators;
    private AI ai;
    private Stone playerStone;
    
    public Game(AI ai, Stone playerColor) {   
        this(new Board(), initValidatorList(), ai, playerColor);
    }

    public Game(Board board, ArrayList<Validator> validators, AI ai, Stone playerStone) {
        this.board = board;
        this.validators = validators;
        this.ai = ai;
        this.playerStone = playerStone;
    }
    
    private static ArrayList<Validator> initValidatorList() {
        ArrayList<Validator> validators = new ArrayList<>();
        
        return validators;
    }
    
    public Position doCompleteTurn(Board board) {
        Position userTurn = handleUserTurn(board);
        this.board.addStone(userTurn, playerStone);
        
        Position aiTurn = this.ai.calculateTurn(this.board, userTurn);
        this.board.addStone(aiTurn, (this.playerStone == Stone.RED) ? Stone.YELLOW : Stone.RED);
        
        return aiTurn;
    }
    
    public Position doCompleteTurn(Position pos) {
        Board newBoard = (Board) this.board.clone();
        newBoard.addStone(pos, this.playerStone);
        
        return this.doCompleteTurn(newBoard);
    }
    
    private Position handleUserTurn(Board board) {
        // TODO Run validators, if differnce list only contains one item (only if this is a valid difference) 
        // this is the last turn done which can be given to the AI
    }
}
