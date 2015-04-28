/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class InvalidPositionException extends ValidationException
{
    public static final String MSG = "Wrong position. Under the new stone '%s' must be in another stone.";
    private final Position invalidPosition;
    
    public InvalidPositionException(Position invalidPosition)
    {
        super(String.format(MSG, invalidPosition));
        this.invalidPosition = invalidPosition;
    }
    
    public Position getInvalidPosition()
    {
        return invalidPosition;
    }
}
