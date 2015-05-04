package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.board.Board;

import java.util.HashMap;

/**
 * Created by florian on 02.05.15.
 */
public class AlphaBetaCache {
    private static final int LIMIT = (int) 10E3;
    public static final int CACHE_WHEN_BELOW_LEVEL = 10;

    private int askedCounter = 0;
    private int resolvedByCacheCounter = 0;
    private int levelOffset;

    private HashMap<Board, AlphaBetaResult> cache = new HashMap<Board, AlphaBetaResult>(LIMIT);

    public AlphaBetaCache(int levelOffset) {
        this.levelOffset = levelOffset;
    }

    public AlphaBetaResult lookup(Board board) {
        if (board.getTurnCount() >= CACHE_WHEN_BELOW_LEVEL) return null;

        AlphaBetaResult result = this.cache.get(board);

        this.askedCounter++;
        if (result != null) this.resolvedByCacheCounter++;


        return result;
    }

    public void store(Board board, AlphaBetaResult result) {
        if (this.cache.size() < LIMIT && board.getTurnCount() < (this.levelOffset + CACHE_WHEN_BELOW_LEVEL)) {
            this.cache.put(board, result);
        }
    }

    public int getResolvedByCacheCounter() {
        return resolvedByCacheCounter;
    }

    public int getAskedCounter() {
        return askedCounter;
    }

    public int getFillLevel() {
        return this.cache.size();
    }
}
