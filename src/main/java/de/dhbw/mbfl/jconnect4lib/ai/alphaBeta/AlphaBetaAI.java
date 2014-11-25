/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class AlphaBetaAI implements AI {
    public static final int MAX_DEPTH = 5;
    
    @Override
    public Position calculateTurn(Board board, Stone stoneAI) {
        AlphaBetaResult res = AlphaBeta.findBestTurn(board, MAX_DEPTH);
        
        return res.getComputedTurn();
    }
}
