package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;
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
 * The Game where you can play the Game.
 * @author Maurice Busch & Florian Loch
 */
public class Game {
    private Board board;
    private ArrayList<Validator> validators;
    private AI ai;
    private boolean playerStarts;
    
    public Game(AI ai) {   
        this(new Board(), initValidatorList(), ai);
    }

    public Game(Board board, ArrayList<Validator> validators, AI ai) {
        this.board = board;
        this.validators = validators;
        this.ai = ai;
    }

    public AI getAi() {
        return ai;
    }

    /**
     * Default initialisation of the Validators.
     * @return 
     */
    private static ArrayList<Validator> initValidatorList() {
        ArrayList<Validator> validators = new ArrayList<Validator>();
        validators.add(new NoChangeValidator());
        validators.add(new InvalidPositionValidator());
        validators.add(new MoreThanOneValidator());
        validators.add(new StoneChangedValidator());
        
        return validators;
    }
    
    /**
     * Make the ai move and returns a TrunSummary where you can find all furhter informations
     * about the game.
     * @return turnSummary
     */
    public TurnSummary doAITurn() {
        Position aiTurn = this.ai.calculateTurn(this.board);
        this.board.addStone(aiTurn);
        
        int state = this.board.turnEndedGame();
        
        return new TurnSummary(aiTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);        
    }
    
    /**
     * Make a player move with a position and returns a TurnSummary where you can find
     * all further informations about the game. This method will throw a ValidationException
     * if somthing is wrong with this position.
     * @param pos
     * @return turnSummary
     * @throws ValidationException 
     */
    public TurnSummary doPlayerTurn(Position pos) throws ValidationException {
        Board newBoard = (Board) this.board.clone();
        newBoard.addStone(pos);
        
        return this.doPlayerTurn(newBoard);
    }    
    
    /**
     * Make a player move with the complead board and returns a TurnSummary where you can find
     * all further informations about the game. This method will throw a ValidationException
     * if somthing is wrong with this position.
     * @param board
     * @return turnSummary
     * @throws ValidationException 
     */
    public TurnSummary doPlayerTurn(Board board) throws ValidationException {
        Position userTurn = handleUserTurn(board);
        this.board.addStone(userTurn);
        
        int state = this.board.turnEndedGame();
        return new TurnSummary(userTurn, state == Board.STATE_WIN, state == Board.STATE_REMI);
    }
    
    /**
     * Validates the turn of the player if no error was found the position of the
     * new stone will be returned.
     * @param board
     * @return position
     * @throws ValidationException 
     */
    private Position handleUserTurn(Board board) throws ValidationException {
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

    /**
     * Returns the position of the last move.
     * @return position
     */
    public Position getLastTurn() {
        return this.board.getLastTurn();
    }

    /**
     * Gives the board as a string
     * @return board as string
     */
    public String getCurrentBoardAsString()
    {
        return this.board.toString();
    }

    public Board getBoard() {
        return this.board; //TODO this is againt the law of Demeter
    }
}
