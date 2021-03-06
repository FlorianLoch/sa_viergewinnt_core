/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public abstract class PatternDetector {
    // The default weighting for any pattern is initially 1
    private int weighting = 1;
    private boolean multiplier = false;

    /**
     * @param board The board on which the pattern shall be searched
     * @return How often the pattern has been found
    */
    public RatingResult searchPattern(Board board) {
        return this.searchPatternImpl(board);
    }
    
    protected abstract RatingResult searchPatternImpl(Board board);
    
    public int getWeighting() {
        return this.weighting;
    }
    
    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    public boolean isMultiplier() {
        return multiplier;
    }

    public void setMultiplier(boolean multiplier) {
        this.multiplier = multiplier;
    }
}
