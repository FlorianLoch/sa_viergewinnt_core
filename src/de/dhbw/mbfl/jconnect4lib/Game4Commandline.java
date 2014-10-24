/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.interrupt.EasyAI;
import de.dhbw.mbfl.jconnect4lib.ai.random.RandomAI;
import de.dhbw.mbfl.jconnect4lib.board.Board;
import de.dhbw.mbfl.jconnect4lib.board.Position;
import de.dhbw.mbfl.jconnect4lib.board.Stone;
import de.dhbw.mbfl.jconnect4lib.board.TurnSummary;
import de.dhbw.mbfl.jconnect4lib.exceptions.ValidationException;
import java.util.Scanner;

/**
 *
 * @author Maurice Busch <busch.maurice@gmx.net>
 */
public class Game4Commandline
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Game game = new Game(new EasyAI(), Stone.YELLOW);
        Scanner input = new Scanner(System.in);
        int col = 0;
        int row = 0;

        while(true)
        {
            while(true)
            {
                System.out.print("Bitte Spalte eingeben: ");
                col = input.nextInt() -1;
                if(col >= 0 && col < Board.COLUMN_COUNT)
                {
                    break;
                }
            }
            while(true)
            {
                System.out.print("Bitte Zeile eingeben: ");
                row = input.nextInt() -1;
                if(row >= 0 && row < Board.ROW_COUNT)
                {
                    break;
                }
            }

            System.out.println();

            try {
                TurnSummary playerSummary = game.doPlayerTurn(new Position(col, row));
                if(playerSummary.isRemi())
                {
                    System.out.println("Remi");
                    break;
                }

                if(playerSummary.isWon())
                {
                    System.out.println("You win!");
                    break;
                }

                TurnSummary aiSummary = game.doAITurn();
                if(aiSummary.isRemi())
                {
                    System.out.println("Remi");
                    break;
                }

                if(aiSummary.isWon())
                {
                    System.out.println("You lose!");
                    break;
                }

                System.out.println(game.getCurrentBoardAsString());                
            }
            catch (ValidationException ex) {
                System.out.println(ex);
                System.out.println(game.getCurrentBoardAsString()); 
            }
        }
        System.out.println(game.getCurrentBoardAsString());
    }

}
