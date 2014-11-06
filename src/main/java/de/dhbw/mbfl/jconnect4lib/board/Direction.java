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
    
    @Override
    public String toString()
    {
        return direction;
    }
}
