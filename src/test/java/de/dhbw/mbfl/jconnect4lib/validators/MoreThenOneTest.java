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
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import de.dhbw.mbfl.jconnect4lib.exceptions.MoreThenOneException;
import java.util.ArrayList;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class MoreThenOneTest extends EasyMockSupport
{
    
    @Test
    public void testValidateZero()
    {
        Position pos = createStrictMock(Position.class);
        ArrayList<Difference> difs = new ArrayList();
//        difs.add(new Difference(Stone.YELLOW, Stone.YELLOW, pos));
        Board board = createStrictMock(Board.class);
        MoreThanOneValidator validator = new MoreThanOneValidator();
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
    public void testValidateOne()
    {
        Position pos = createStrictMock(Position.class);
        ArrayList<Difference> difs = new ArrayList();
        difs.add(new Difference(Stone.YELLOW, Stone.YELLOW, pos));
        Board board = createStrictMock(Board.class);
        MoreThanOneValidator validator = new MoreThanOneValidator();
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
    public void testValidateTwo()
    {
        Position pos = createStrictMock(Position.class);
        ArrayList<Difference> difs = new ArrayList();
        difs.add(new Difference(Stone.YELLOW, Stone.YELLOW, pos));
        difs.add(new Difference(Stone.YELLOW, Stone.YELLOW, pos));
        Board board = createStrictMock(Board.class);
        MoreThanOneValidator validator = new MoreThanOneValidator();
        replayAll();
        
        try
        {
            validator.validate(difs, board);
            fail("Here is an exeption expected.");
        }
        catch(ValidationException ex)
        {
            assertThat(ex, instanceOf(MoreThenOneException.class));
        }
        
        verifyAll();
    }
    
}
