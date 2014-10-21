/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.board;

/**
 *
 * @author florian
 */
public class Difference {
    private Stone oldStone;
    private Stone newStone;
    private Position position;

    public Difference(Stone oldStone, Stone newStone, Position position) {
        this.oldStone = oldStone;
        this.newStone = newStone;
        this.position = position;
    }
    
    /**
     * @return the oldStone
     */
    public Stone getOldStone() {
        return this.oldStone;
    }

    /**
     * @return the newStone
     */
    public Stone getNewStone() {
        return this.newStone;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return this.position;
    }
}
