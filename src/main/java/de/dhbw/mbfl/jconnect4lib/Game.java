package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.board.TurnSummary;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import de.dhbw.mbfl.jconnect4lib.validators.InvalidPositionValidator;
import de.dhbw.mbfl.jconnect4lib.validators.MoreThanOneValidator;
import de.dhbw.mbfl.jconnect4lib.validators.NoChangeValidator;
import de.dhbw.mbfl.jconnect4lib.validators.StoneChangedValidator;
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
    private Stone stonePlayer;
    private Stone stoneAI;
    
    public Game(AI ai, Stone stonePlayer) {   
        this(new Board(), initValidatorList(), ai, stonePlayer);
    }

    public Game(Board board, ArrayList<Validator> validators, AI ai, Stone stonePlayer) {
        this.board = board;
        this.validators = validators;
        this.ai = ai;
        this.stonePlayer = stonePlayer;
        this.stoneAI = (this.stonePlayer == Stone.RED) ? Stone.YELLOW : Stone.RED;
    }
    
    private static ArrayList<Validator> initValidatorList() {
        ArrayList<Validator> validators = new ArrayList<>();
        validators.add(new NoChangeValidator());
        validators.add(new InvalidPositionValidator());
        validators.add(new MoreThanOneValidator());
        validators.add(new StoneChangedValidator());
        
        return validators;
    }
    
    public TurnSummary doPlayerTurn(Position pos) throws ValidationException {
        Board newBoard = (Board) this.board.clone();
        newBoard.addStone(pos, this.stonePlayer);
        
        return this.doPlayerTurn(newBoard);
    }    
    
    public TurnSummary doPlayerTurn(Board board) throws ValidationException {
        Position userTurn = handleUserTurn(board);
        this.board.addStone(userTurn, stonePlayer);
        
        int state = this.board.turnEndedGame();
        return new TurnSummary(userTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);
    }
    
    public TurnSummary doAITurn() {
        Position aiTurn = this.ai.calculateTurn(this.board, this.stoneAI);
        this.board.addStone(aiTurn, this.stoneAI);
        
        int state = this.board.turnEndedGame();
        
        return new TurnSummary(aiTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);        
    }
    
    private Position handleUserTurn(Board board) throws ValidationException {
        // TODO Run validators, if differnce list only contains one item (only if this is a valid difference) 
        // this is the lastTurn done which can be given to the AI
        ArrayList<Difference> differences = this.board.determineDifferences(board);
        
        try {
            for (Validator validator : this.validators) {
                validator.validate(differences, board);
            }
        } catch (ValidationException ex) {
            throw ex;
        }
        
        return differences.get(0).getPosition();
    }
    
    public String getCurrentBoardAsString()
    {
        return this.board.toString();
    }
}
