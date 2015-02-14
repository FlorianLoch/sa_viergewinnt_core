/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 * Board Size the default is set to col 7 and row 6, like a normal connect 4 game.
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public enum Size
{

    BOARD(7, 6);

    private int column, row;
    private boolean changeable;

    private Size(int column, int row)
    {
        this.column = column;
        this.row = row;
        this.changeable = true;
    }
    
    public void changeSize(int column, int row)
    {
        if(changeable)
        {
            if(column > 0 && column <= 24)
            {
                this.column = column;
            }
            if(row > 0 && row <= 24 )
            {
                this.row = row;
            }
        }
        
        changeable = false;
    }
    
    public int column()
    {
        return this.column;
    }
    
    public int size() {
        return this.column * this.row;
    }
    
    public int row()
    {
        return this.row;
    }
    
    /**
     * unlogs the size so you can change it.
     */
    public Size unlog()
    {
        changeable = true;
        return this;
    }

}
