/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.exceptions;

import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class StoneChangedException extends ValidationException
{
    public static final String MSG = "Stone at position '%s' changed!";
    
    private final Difference difference;
    
    public StoneChangedException(Difference difference)
    {
        super(String.format(MSG, difference.getPosition()));
        this.difference = difference;
    }
    
    public Difference getDifference()
    {
        return difference;
    }
    
    public Position getDifferencePosition()
    {
        return difference.getPosition();
    }
    
    public String getDifferencePositionAsString()
    {
        return difference.getPosition().toString();
    }
    
    public Stone getDifferenceNewStone()
    {
        return difference.getNewStone();
    }
    
    public Stone getDifferenceOldStone()
    {
        return difference.getOldStone();
    }
    
}
