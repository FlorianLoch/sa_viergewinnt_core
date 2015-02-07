/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.board;

import java.util.BitSet;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class BoardIdentity {
    private BitSet bits; //Bit at index n (expecting n to be a even number) describes together with n+1 the state of position n div 2 on the board.
                         //00 means position not set
                         //01 means position occupied by yellow stone
                         //10 means position occupied by red stone
                         //Bits are initilized with "false" (0)
    
    public BoardIdentity(Board board) {
        this.bits = new BitSet(42 * 2); //TODO Replace the constant with something more adaptive
        
        convertBoardToBitSet(board);
    }
    
    private void convertBoardToBitSet(Board board) {
        int i = 0;
        for (Stone s : board) {
            if (s == Stone.YELLOW) bits.set(i + 1);
            if (s == Stone.RED) bits.set(i);
            i += 2;
        }
    }
    
    public Board reconstructBoard() {
        Board board = new Board();
        
        for (int i = 0; i < bits.length(); i += 2) {
            boolean a = bits.get(i);
            boolean b = bits.get(i + 1);
            Stone stoneToAdd = null;
            
            if (!a && !b) continue;
            if (!a && b) stoneToAdd = Stone.YELLOW;
            if (a && !b) stoneToAdd = Stone.RED;
            
            board.addStone(new Position(i / 2), stoneToAdd);
        }
        
        return board;
    }

    @Override
    public String toString() {
        return this.bits.toString();
    } 

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BoardIdentity)) return false;
        
        BoardIdentity b = (BoardIdentity) o;
        
        return this.bits.equals(b.bits);
    }
}
