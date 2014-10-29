/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author florian
 */
public class OutOfBoardException extends RuntimeException {

    public OutOfBoardException(Position pos) {
        super("The given position (" + pos + ") is outside of the boards bounds.");
    }
    
    
    
}
