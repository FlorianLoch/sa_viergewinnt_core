package de.dhbw.mbfl.jconnect4lib.ai;

import de.dhbw.mbfl.jconnect4lib.board.Board;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by florian on 27.02.15.
 */
public enum RatingCache {
    PATTERN_RATER;

    private HashMap<Board, Integer> cache;

    RatingCache() {
        this.cache = new HashMap<Board, Integer>();
    }

    public Integer lookupBoard(Board board) {
        this.cache.get(board);
    }

    public void putBoard(Board board, Integer rating) {
        this.cache.put(board, rating);
    }
}
