/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public enum Direction
{
    NORTH("North"), EAST("East"), SOUTH("South"), WEST("West"), 
    NORTH_EAST("North-East"), NORTH_WEST("North-West"), 
    SOUTH_EAST("South-East"), SOUTH_WEST("South-West");
    
    private String direction;
    
    private Direction(String direction)
    {
        this.direction = direction;
    }
    
    /**
     * Gives the horizontal Direction on the bord. Returns -1 for left collumn on the
     * board, 1 for right column and 0 for the same collumn:
     * 
     * 3
     * 2   -1  0  1
     * 1
     *   A  B  C  D  E
     * @return direction horizontal
     */
    public int horizontalDirection()
    {
        switch (this)
        {
            case NORTH: case SOUTH: return 0;
            case EAST: case NORTH_EAST: case SOUTH_EAST: return 1;
            case WEST: case NORTH_WEST: case SOUTH_WEST: return -1;
            default: return 0;
        }
    }
    
    /**
     * Gives the vertical Direction on the board. Returns -1 for the row below, 1 for the
     * row above and 0 for the same row:
     * 
     * 5
     * 4     1
     * 3     0
     * 2    -1
     * 1
     *    A  B  C
     * 
     * @return vertical Direction
     */
    public int verticalDirection()
    {
        switch (this)
        {
            case EAST: case WEST: return 0;
            case NORTH: case NORTH_EAST: case NORTH_WEST: return 1;
            case SOUTH: case SOUTH_EAST: case SOUTH_WEST: return -1;
            default: return 0;
        }
    }



    public Direction getOpposite() {
        if (this == NORTH) return SOUTH;
        if (this == NORTH_EAST) return SOUTH_WEST;
        if (this == EAST) return WEST;
        if (this == SOUTH_EAST) return NORTH_WEST;

        if (this == SOUTH) return NORTH;
        if (this == SOUTH_WEST) return NORTH_EAST;
        if (this == WEST) return EAST;
        if (this == NORTH_WEST) return SOUTH_EAST;

        return null;
    }

    @Override
    public String toString()
    {
        return direction;
    }
}
