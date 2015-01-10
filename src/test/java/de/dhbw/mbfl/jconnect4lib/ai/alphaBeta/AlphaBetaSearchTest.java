/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.AlphaBetaNode;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.MaximizingNode;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.MinimizingNode;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author florian
 */
public class AlphaBetaSearchTest {

//    @Test
//    public void testAlphaBetaSearch() {
//        //Construct tree according to this example: http://youtu.be/Ewh-rF7KSEg
//        AlphaBetaNode root = new MaximizingNode(null); //a
//        AlphaBetaNode b = new MinimizingNode(null);
//        AlphaBetaNode c = new MinimizingNode(null);
//        AlphaBetaNode d = new MinimizingNode(null);
//        AlphaBetaNode h = new MaximizingNode(null);
//        AlphaBetaNode j = new MaximizingNode(null);
//        
//        b.addChild(new MaximizingNode(null, 4));
//        b.addChild(new MaximizingNode(null, 5));
//        
//        h.addChild(new MinimizingNode(null, 3));
//        h.addChild(new MinimizingNode(null, 4));
//        
//        j.addChild(new MinimizingNode(null, 7));
//        j.addChild(new MinimizingNode(null, 9));        
//        
//        c.addChild(new MaximizingNode(null, 6));
//        c.addChild(h);
//        c.addChild(j);
//        
//        d.addChild(new MaximizingNode(null, 3));
//        d.addChild(new MaximizingNode(null, 8));
//        
//        root.addChild(b);
//        root.addChild(c);
//        root.addChild(d);
//        
//        int bestGuaranteedResult = root.computeBestTurnFromNode(3);
//        
//        assertTrue(bestGuaranteedResult == 4);
//    }
    
}