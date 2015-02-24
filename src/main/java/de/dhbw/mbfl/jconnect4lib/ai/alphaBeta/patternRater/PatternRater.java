/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.BoardRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.RatingResult;

import de.dhbw.mbfl.jconnect4lib.board.Board;
import java.util.ArrayList;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class PatternRater extends BoardRater {

    final private ArrayList<PatternDetector> detectors;

    public PatternRater() {
        this.detectors = new ArrayList<PatternDetector>();
    }
    
    public void addPatternDetector(PatternDetector detector) {
        this.addPatternDetector(detector, 0, false);
    }
    
    public void addPatternDetector(PatternDetector detector, int weighting, boolean multiplier) {
        //If weighting is 0 (zero), the default value provided by the detector is used
        if (weighting != 0) {
            detector.setWeighting(weighting);
        }

        detector.setMultiplier(multiplier);
        
        this.detectors.add(detector);
    }

    @Override
    protected RatingResult rateImpl(Board board) {
        RatingResult rating = new RatingResult(0, 0);

        for (PatternDetector d : this.detectors) {
            RatingResult r = d.searchPattern(board);
            rating.addRating(r, d.getWeighting(), d.isMultiplier());
        }
        
        return rating;
    }
}
