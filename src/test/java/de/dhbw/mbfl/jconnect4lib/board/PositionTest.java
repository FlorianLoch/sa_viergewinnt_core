package de.dhbw.mbfl.jconnect4lib.board;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
@RunWith(Theories.class)
public class PositionTest
{
    private static class Data
    {
        int col, row, pos;
        String str;
        Data(int col, int row, int pos, String str)
        {
            this.col = col;
            this.row = row;
            this.pos = pos;
            this.str = str;
        }
        @Override
        public String toString()
        {
            return "Col:" + col + " Row:" + " Pos:" + pos + " Str:" + str;
        }
    }
    
    /*
     * 5  35 36 37 38 39 40 41
     * 4  28 29 30 31 32 33 34
     * 3  21 22 23 24 25 26 27
     * 2  14 15 16 17 18 19 20
     * 1  07 08 09 10 11 12 13
     * 0  00 01 02 03 04 05 06
     *    0  1  2  3  4  5  6  
     */
    @DataPoints
    public static Data[] dataPoints()
    {
        return new Data[] {
            new Data(0, 0,  0, "A1"), new Data(1, 0,  1, "B1"), new Data(2, 0,  2, "C1"), new Data(3, 0,  3, "D1"), new Data(4, 0,  4, "E1"), new Data(5, 0,  5, "F1"), new Data(6, 0,  6, "G1"),
            new Data(0, 1,  7, "A2"), new Data(1, 1,  8, "B2"), new Data(2, 1,  9, "C2"), new Data(3, 1, 10, "D2"), new Data(4, 1, 11, "E2"), new Data(5, 1, 12, "F2"), new Data(6, 1, 13, "G2"),
            new Data(0, 2, 14, "A3"), new Data(1, 2, 15, "B3"), new Data(2, 2, 16, "C3"), new Data(3, 2, 17, "D3"), new Data(4, 2, 18, "E3"), new Data(5, 2, 19, "F3"), new Data(6, 2, 20, "G3"),
            new Data(0, 3, 21, "A4"), new Data(1, 3, 22, "B4"), new Data(2, 3, 23, "C4"), new Data(3, 3, 24, "D4"), new Data(4, 3, 25, "E4"), new Data(5, 3, 26, "F4"), new Data(6, 3, 27, "G4"),
            new Data(0, 4, 28, "A5"), new Data(1, 4, 29, "B5"), new Data(2, 4, 30, "C5"), new Data(3, 4, 31, "D5"), new Data(4, 4, 32, "E5"), new Data(5, 4, 33, "F5"), new Data(6, 4, 34, "G5"),
            new Data(0, 5, 35, "A6"), new Data(1, 5, 36, "B6"), new Data(2, 5, 37, "C6"), new Data(3, 5, 38, "D6"), new Data(4, 5, 39, "E6"), new Data(5, 5, 40, "F6"), new Data(6, 5, 41, "G6")
        };
    }
    
    @Theory
    public void testCollumnRow(Data data)
    {
        Position pos = new Position(data.col, data.row);
        
        assertEquals(data.toString(), pos.getColumn(), data.col);
        assertEquals(data.toString(), pos.getRow(), data.row);
    }
    
    @Theory
    public void testPosition(Data data)
    {
        Position posColRow = new Position(data.col, data.row);
        Position posPos = new Position(data.pos);
        
        assertEquals("When create Position-Object one with pos and one with Column and Row "
                + "the Position has to be the same." + data.toString(), posColRow.getPosition(), posPos.getPosition());
        assertEquals("When create Position-Object with Column and Row the position is not "
                + "correct" + data.toString(), data.pos, posColRow.getPosition());
        assertEquals("When create Position-Object with position the Column is not "
                + "correct" + data.toString(), data.col, posPos.getColumn());
        assertEquals("When create Position-Object with position the Column is not "
                + "correct" + data.toString(), data.row, posPos.getRow());
    }
    
    @Theory
    public void testParsePosition(Data data)
    {
        Position posColRow = new Position(data.col, data.row);
        Position posStr = new Position(data.str);
        
        assertEquals("Create Position with Column and Row there was a mistake " + data.toString(), data.str, posColRow.toString());
        assertEquals("Create Position with String the Column is not correct " + data.toString(), data.col, posStr.getColumn());
        assertEquals("Create Position with String the Row is not correct " + data.toString(), data.row, posStr.getRow());
    }
    
    @Test
    public void testNewPosition()
    {
        Position pos = new Position(1, 1);
        
        
        Position north = pos.getNewPosition(Direction.NORTH);
        Position south = pos.getNewPosition(Direction.SOUTH);
        Position east = pos.getNewPosition(Direction.EAST);
        Position west = pos.getNewPosition(Direction.WEST);
        Position northEast = pos.getNewPosition(Direction.NORTH_EAST);
        Position northWest = pos.getNewPosition(Direction.NORTH_WEST);
        Position southEast = pos.getNewPosition(Direction.SOUTH_EAST);
        Position southWest = pos.getNewPosition(Direction.SOUTH_WEST);
        
        
        assertEquals("Direction north is in column not correct.", 1, north.getColumn());
        assertEquals("Direction north is in row not correct.", 2, north.getRow());
        
        assertEquals("Direction south is in column not correct.", 1, south.getColumn());
        assertEquals("Direction south is in row not correct.", 0, south.getRow());
        
        assertEquals("Direction east is in column not correct.", 2, east.getColumn());
        assertEquals("Direction east is in row not correct.", 1, east.getRow());
        
        assertEquals("Direction west is in column not correct.", 0, west.getColumn());
        assertEquals("Direction west is in row not correct.", 1, west.getRow());
        
        assertEquals("Direction north east is in column not correct.", 2, northEast.getColumn());
        assertEquals("Direction north east is in row not correct.", 2, northEast.getRow());
        
        assertEquals("Direction north west is in column not correct.", 0, northWest.getColumn());
        assertEquals("Direction north west is in row not correct.", 2, northWest.getRow());
        
        assertEquals("Direction south east is in column not correct.", 2, southEast.getColumn());
        assertEquals("Direction south east is in row not correct.", 0, southEast.getRow());
        
        assertEquals("Direction south west is in column not correct.", 0, southWest.getColumn());
        assertEquals("Direction south west is in row not correct.", 0, southWest.getRow());
    }
}
