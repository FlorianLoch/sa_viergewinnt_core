package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.board.TurnSummary;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
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
    
    public TurnSummary doPlayerTurn(Position pos) {
        Board newBoard = (Board) this.board.clone();
        newBoard.addStone(pos, this.playerStone);
        
        return this.doPlayerTurn(newBoard);
    }    
    
    public TurnSummary doPlayerTurn(Board board) {
        Position userTurn = handleUserTurn(board);
        this.board.addStone(userTurn, playerStone);
        
        int state = this.board.turnEndedGame();
        return new TurnSummary(userTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);
    }
    
    public TurnSummary doAITurn() {
        Position aiTurn = this.ai.calculateTurn(this.board);
        this.board.addStone(aiTurn, (this.playerStone == Stone.RED) ? Stone.YELLOW : Stone.RED);
        
        int state = this.board.turnEndedGame();
        
        return new TurnSummary(aiTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);        
    }
    
    private Position handleUserTurn(Board board) {
        // TODO Run validators, if differnce list only contains one item (only if this is a valid difference) 
        // this is the lastTurn done which can be given to the AI
        ArrayList<Difference> differences = this.board.determineDifferences(board);
        
        try {
            for (Validator validator : this.validators) {
                validator.validate(differences, board);
            }
        } catch (ValidationException ex) {
            System.out.println(ex.toString());
        }
        
        return differences.get(0).getPosition();
    }
    
    public String getCurrentBoardAsString()
    {
        return this.board.toString();
    }
}
