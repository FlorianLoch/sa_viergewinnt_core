/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author florian
 */
public final class AlphaBetaTree {
    private AlphaBetaNode root;
    private Object lock;
    private SynchronizedJobList jobList;
    private final int maxDepth;
    
    public AlphaBetaTree(int maxDepth, Board currentBoard, Stone currentPlayer) {
        this.lock = new Object();
        this.maxDepth = maxDepth;
        
        if (currentPlayer == Stone.YELLOW) {
            this.root = new MaximizingNode(0, currentBoard);
        }
        else if (currentPlayer == Stone.RED) {
            this.root = new MinimizingNode(0, currentBoard);
        }
        else {
            throw new IllegalArgumentException("Current player must be either YELLOW or RED (instance of Stone enum)");
        }
    }
    
    public void constructTree() {
        this.jobList = new SynchronizedJobList();
        this.jobList.addJob(this.root);
      
        Runnable worker = new Runnable() {
            @Override
            public void run() {
                AlphaBetaNode node = jobList.getAJob();
                
                int currentDepth = node.getDepthLevel();
                
                if (node.isLeaf()) {
                    node.setValue(node.computeLeafValue());
                    return;
                }
                
                if (currentDepth + 1 == maxDepth) return;
                
                ArrayList<Board> combinations = computeNextCombinations(root);
                
                AlphaBetaNode newNode;
                for (Board b :  combinations) {
                    if (node instanceof MaximizingNode) {
                        newNode = new MinimizingNode(currentDepth + 1, b);
                    } 
                    else {
                        newNode = new MaximizingNode(currentDepth + 1, b);
                    }
                    node.addChild(newNode);
                    jobList.addJob(newNode);
                }
            }
        };
        
        try {
            //Executor-Framework, start some threads
            this.lock.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(AlphaBetaTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
    private static ArrayList<Board> computeNextCombinations(AlphaBetaNode node) {
        
    }

    private static boolean checkBoardsValidity() {
        
    }
}
