/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import java.util.ArrayList;

/**
 *
 * @author florian
 */
public class SynchronizedJobList {
    private ArrayList<AlphaBetaNode> list;

    public SynchronizedJobList() {
        this.list = new ArrayList<AlphaBetaNode>();
    }
    
    public synchronized void addJob(AlphaBetaNode job) {
        this.list.add(job);
    }
    
    public synchronized AlphaBetaNode getAJob() {
        if (this.list.isEmpty()) return null;
        
        return this.list.remove(0);
    }
}
