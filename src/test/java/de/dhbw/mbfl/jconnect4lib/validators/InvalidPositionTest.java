/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Direction;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class InvalidPositionTest extends EasyMockSupport
{
    
    @Test
    public void testValidateBottomRow()
    {
        Position pos = createStrictMock(Position.class);
        expect(pos.getRow()).andReturn(0);
        Difference dif = new Difference(Stone.YELLOW, Stone.YELLOW, pos);
        ArrayList<Difference> difs = new ArrayList();
        difs.add(dif);
        Board board = createStrictMock(Board.class);
        InvalidPositionValidator validator = new InvalidPositionValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
        }
        catch(ValidationException ex)
        {
            fail("Here is no exeption expected.");
        }
        
        verifyAll();
    }
    
    @Test
    public void testValidateSomeRow()
    {
        Position pos = createStrictMock(Position.class);
        Position underPos = createStrictMock(Position.class);
        expect(pos.getRow()).andReturn(3);
        expect(pos.newPosition(Direction.SOUTH)).andReturn(underPos);
        expect(pos.getRow()).andReturn(3);
        expect(pos.newPosition(Direction.SOUTH)).andReturn(underPos);
        Difference dif = new Difference(Stone.YELLOW, Stone.YELLOW, pos);
        ArrayList<Difference> difs = new ArrayList();
        difs.add(dif);
        Board board = createStrictMock(Board.class);
        expect(board.getStone(underPos)).andReturn(Stone.RED);
        expect(board.getStone(underPos)).andReturn(Stone.YELLOW);
        InvalidPositionValidator validator = new InvalidPositionValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
            validator.validate(difs, board);
        }
        catch(ValidationException ex)
        {
            fail("Here is no exeption expected.");
        }
        
        verifyAll();
    }
    
    @Test
    public void testValidateException()
    {
        Position pos = createStrictMock(Position.class);
        Position underPos = createStrictMock(Position.class);
        expect(pos.getRow()).andReturn(3);
        expect(pos.newPosition(Direction.SOUTH)).andReturn(underPos);
        Difference dif = new Difference(Stone.YELLOW, Stone.YELLOW, pos);
        ArrayList<Difference> difs = new ArrayList();
        difs.add(dif);
        Board board = createStrictMock(Board.class);
        expect(board.getStone(underPos)).andReturn(null);
        InvalidPositionValidator validator = new InvalidPositionValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
            fail("An Exeption was expected.");
        }
        catch(ValidationException ex)
        {
            assertThat(ex.getMessage(), containsString("Fehlerhafte Position."));
        }
        
        verifyAll();
    }
}
