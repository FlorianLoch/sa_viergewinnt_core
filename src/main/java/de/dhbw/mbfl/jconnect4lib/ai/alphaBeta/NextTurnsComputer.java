/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import java.util.LinkedList;

/**
 *
 * @author florian
 */
public interface NextTurnsComputer {
    
    public LinkedList<Board> computeNextTurns(Board currentBoard);
    
}
