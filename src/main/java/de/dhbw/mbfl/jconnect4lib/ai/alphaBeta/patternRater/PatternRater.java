/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.BoardRater;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import java.util.ArrayList;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class PatternRater implements BoardRater {

    final private ArrayList<PatternDetector> detectors = new ArrayList<>();

    public PatternRater() {
        
    }
    
    public void addPatternDetector(PatternDetector detector) {
        this.addPatternDetector(detector, 0);
    }
    
    public void addPatternDetector(PatternDetector detector, int weighting) {
        //If weighting is 0 (zero), the default value provided by the detector is used
        if (weighting != 0) {
            detector.setWeighting(weighting);
        }
        
        this.detectors.add(detector);
    }

    @Override
    public int rate(Board board) {
        int rating = 0;
        
        for (PatternDetector d : this.detectors) {
            rating += d.getWeighting() * d.searchPattern(board);
        }
        
        return rating;
    }    
}
