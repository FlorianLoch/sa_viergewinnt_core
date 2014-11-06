package de.dhbw.mbfl.jconnect4lib.board;

import static org.junit.Assert.*;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Tests the Direction Class
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
@RunWith(Theories.class)
public class DirectionTest
{
    
    private static class Data {
        public int horizontal, vertical;
        public Direction direction;
        public Data (Direction direction, int horizontal, int vertical)
        {
            this.direction = direction; this.horizontal = horizontal; this.vertical = vertical;
        }
    }
    
    @DataPoints
    public static Data[] dataPoints() 
    {
        return new Data[] {
            new Data(Direction.NORTH, 0, 1), new Data(Direction.SOUTH, 0, -1),
            new Data(Direction.EAST, 1, 0), new Data(Direction.WEST, -1, 0),
            new Data(Direction.NORTH_EAST, 1, 1), new Data(Direction.NORTH_WEST, -1, 1),
            new Data(Direction.SOUTH_EAST, 1, -1), new Data(Direction.SOUTH_WEST, -1, -1)
        };
    }
    
    @Theory
    public void testHorizontal(Data data)
    {
        assertEquals("Wrong horizontal direction in " + data.direction.toString(), data.horizontal, data.direction.horizontalDirection());
    }
    
    @Theory
    public void testVertical(Data data)
    {
        assertEquals("Wrong vertical direction in " + data.direction.toString(), data.vertical, data.direction.verticalDirection());
    }
    
}
