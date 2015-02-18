/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleColumns;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class AlphaBetaAI implements AI {
    public static final int MAX_DEPTH = 6; //This causes the AI to make an odd number of foresight steps. This leads to better results than using an even amount of steps

    public Position calculateTurn(Board board, int maxDepth) {
        PatternRater patternRater = new PatternRater();
        patternRater.addPatternDetector(new MiddleColumns());
        
        AlphaBetaResult res = AlphaBeta.findBestTurn(board, maxDepth, patternRater, null);
        
        return res.getComputedTurn();
    }


    @Override
    public Position calculateTurn(Board board) {
        return this.calculateTurn(board, MAX_DEPTH);
    }
}
