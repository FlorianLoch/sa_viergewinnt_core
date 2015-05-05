/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.dhbw.mbfl.jconnect4lib.ai.alphaBeta;

import de.dhbw.mbfl.jconnect4lib.ai.AI;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.PatternRater;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleColumnsPattern;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.MiddleRowsPattern;
import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.patternRater.patterns.StreakPattern;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Size;

import java.util.Scanner;

/**
 *
 * @author Florian Loch (florian dot loch at gmail dot com)
 */
public class AlphaBetaAI implements AI {
    public static final int DEFAULT_DEPTH = 6; //This causes the AI to make an odd number of foresight steps. This leads to better results than using an even amount of steps
    public static final int ADAPTIVE_INCREASEMENT_STARTING_AT = 20;
    private int depth;

    //For benchmarking purposes
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        if (args.length == 1 && args[0].equals("wait")) {
            Scanner scn = new Scanner(System.in);
            scn.nextLine();
        }

        for (int i = 12; i <= 12; i++) {
            AlphaBetaAI ai = new AlphaBetaAI();
            Board board = new Board();

            Position computed = ai.calculateTurn(board, i);
        }

        long duration = System.nanoTime() - startTime;
        System.out.println("Computation took " + duration + "ns (~" + duration / 10E5 + "ms)");
    }

    public Position calculateTurn(Board board, int depth) {
        PatternRater patternRater = new PatternRater();
        patternRater.addPatternDetector(new MiddleColumnsPattern());
        patternRater.addPatternDetector(new MiddleRowsPattern());
        patternRater.addPatternDetector(new StreakPattern(), 1, true);

        int adaptiveDepth = computeAdaptiveSearchDepth(board, depth);

        AlphaBetaResult res = AlphaBeta.findBestTurn(board, adaptiveDepth, patternRater, new HeuristicNextTurnsComputer());
        
        return res.getComputedTurn();
    }

    private static int computeAdaptiveSearchDepth(Board board, int startDepth) {
        int turnsPlayed = board.getTurnCount();

        if (turnsPlayed < ADAPTIVE_INCREASEMENT_STARTING_AT) return startDepth;

        return startDepth + (int) Math.ceil(((turnsPlayed - ADAPTIVE_INCREASEMENT_STARTING_AT) / 4.0));
    }


    public AlphaBetaAI() {
        this(DEFAULT_DEPTH);
    }

    public AlphaBetaAI(int depth) {
        if (1 > depth) {
            throw new IllegalArgumentException("Depth cannot be less than 1");
        }
        this.depth = depth;
    }

    @Override
    public Position calculateTurn(Board board) {
        return this.calculateTurn(board, this.depth);
    }

    public int getDepth() {
        return depth;
    }
}
