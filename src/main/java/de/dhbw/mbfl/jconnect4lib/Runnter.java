package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.AlphaBetaAI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;

import java.util.Scanner;

/**
 * Created by florian on 04.05.15.
 */
public class Runnter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter scan level");
        int level = in.nextInt();

        System.out.println("Started scanning to level " + level);

        AlphaBetaAI ai = new AlphaBetaAI();
        Board board = new Board();

        ai.calculateTurn(board, level);
    }
}
