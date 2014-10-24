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
            Position pos = readPositionFromInput(input);

            System.out.println();

            try {
                TurnSummary playerSummary = game.doPlayerTurn(pos);
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

    private static Position readPositionFromInput(Scanner input) {
        String posStr;
        do {
            System.out.println("Please enter a valid field: ");
            posStr = input.nextLine().toUpperCase();
        } while (!isStringValidPosition(posStr));
        
        return new Position((Integer.parseInt(posStr.substring(1)) - 1) * 7 + (posStr.codePointAt(0) - 65));
    }
    
    private static boolean isStringValidPosition(String posStr) {
        if (posStr.length() != 2) return false;
        
        int asciiFirstChar = posStr.codePointAt(0);
        int digitSecondChar = Integer.parseInt(posStr.substring(1));
        
        return (asciiFirstChar > 64 && asciiFirstChar < 72 && digitSecondChar > 0 && digitSecondChar < 7);
    }

}
