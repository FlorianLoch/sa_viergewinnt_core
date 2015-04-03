/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.validators;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Difference;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.exceptions.StoneChangedException;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.ArrayList;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class StoneChangeTest extends EasyMockSupport
{
    
    @Test
    public void testValidateNoStoneChange()
    {
        Position posTwo = createStrictMock(Position.class);
        Difference difOne = new Difference(Stone.YELLOW, Stone.YELLOW, createStrictMock(Position.class));
        Difference difTwo = new Difference(Stone.RED, Stone.RED, createStrictMock(Position.class));
        ArrayList<Difference> difs = new ArrayList();
        difs.add(difOne);
        difs.add(difTwo);
        Board board = createStrictMock(Board.class);
        StoneChangedValidator validator = new StoneChangedValidator();
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
    public void testValidateNoStoneChangedNull()
    {
        Position posTwo = createStrictMock(Position.class);
        Difference difOne = new Difference(null, Stone.YELLOW, createStrictMock(Position.class));
        Difference difTwo = new Difference(null, Stone.RED, createStrictMock(Position.class));
        ArrayList<Difference> difs = new ArrayList();
        difs.add(difOne);
        difs.add(difTwo);
        Board board = createStrictMock(Board.class);
        StoneChangedValidator validator = new StoneChangedValidator();
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
    public void testValidateStoneChangedRed()
    {
        Position posTwo = createStrictMock(Position.class);
        Difference difOne = new Difference(Stone.RED, Stone.YELLOW, createStrictMock(Position.class));
        ArrayList<Difference> difs = new ArrayList();
        difs.add(difOne);
        Board board = createStrictMock(Board.class);
        StoneChangedValidator validator = new StoneChangedValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
            fail("An exeption is expected.");
        }
        catch(ValidationException ex)
        {
            assertThat(ex, instanceOf(StoneChangedException.class));
        }
        
        verifyAll();
    }
    
    @Test
    public void testValidateStoneChangedYellow()
    {
        Position posTwo = createStrictMock(Position.class);
        Difference difOne = new Difference(Stone.YELLOW, Stone.RED, createStrictMock(Position.class));
        ArrayList<Difference> difs = new ArrayList();
        difs.add(difOne);
        Board board = createStrictMock(Board.class);
        StoneChangedValidator validator = new StoneChangedValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
            fail("An exeption is expected.");
        }
        catch(ValidationException ex)
        {
            assertThat(ex, instanceOf(StoneChangedException.class));
        }
        
        verifyAll();
    }
    
}
