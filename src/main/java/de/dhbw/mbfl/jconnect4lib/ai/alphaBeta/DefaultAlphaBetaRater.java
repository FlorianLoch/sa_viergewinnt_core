/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 *
 * @author florian
 */
class DefaultAlphaBetaRater implements AlphaBetaRater {

    @Override
    public int rate(Board board) {
        int state = board.turnEndedGame();
        Stone lastPlayer = board.getLastStone(); //This should never be null, because the initial board (nobody did a turn) should never be rated
        
        if (state == Board.STATE_WIN) {
            if (lastPlayer == Stone.RED) return Integer.MAX_VALUE - 1;
            
            return Integer.MIN_VALUE + 1;
        }
        else if (state == Board.STATE_REMI) {
            if (lastPlayer == Stone.RED) return -1; //Remi is rated worse than continuing the game; otherwise the AI would force a remi whenever possible
        
            return 1;
        }
        
        return 0;
    }
    
}
