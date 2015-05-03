/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.mbfl.jconnect4lib;

import de.dhbw.mbfl.jconnect4lib.ai.alphaBeta.AlphaBetaAI;
import de.dhbw.mbfl.jconnect4lib.board.Position;
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
        Scanner input = new Scanner(System.in);

        System.out.println("Who shall start? AI (a) or player (p)?");
        String whoShallStart = input.nextLine().toLowerCase();
        boolean aiStarts;
        if (whoShallStart.equals("a")) {
            aiStarts = true;
        }
        else if (whoShallStart.equals("p")) {
            aiStarts = false;
        }
        else {
            System.out.println("Sorry, your input was invalid. Please try again!");
            return;
        }

        int foresight = 10;
        System.out.println("Which level of foresight shall the AI use (leave blank for default of " + foresight + ")?");
        String foresightInput = input.nextLine();
        if (!foresightInput.isEmpty()) {
            foresight = Integer.parseInt(foresightInput);
        }

        AlphaBetaAI ai = new AlphaBetaAI(foresight);
        Game game = new Game(ai);

        if (aiStarts) {
            game.doAITurn();
            System.out.println(game.getCurrentBoardAsString());
        }

        while(true)
        {
            
            try {
                TurnSummary playerSummary = null;
                boolean exceptionThrown = false;
                do {
                    exceptionThrown = false;
                    try {
                        Position pos = readPositionFromInput(input);
                        System.out.println();
                        playerSummary = game.doPlayerTurn(pos);
                    }catch (RuntimeException ex) {
                        ex.printStackTrace();
                        exceptionThrown = true;
                    }
                } while (exceptionThrown);

                if(playerSummary.isRemis())
                {
                    System.out.println("Remi");
                    break;
                }

                if(playerSummary.isWon())
                {
                    System.out.println("You won!");
                    break;
                }

                TurnSummary aiSummary = game.doAITurn();
                if(aiSummary.isRemis())
                {
                    System.out.println("Drawn");
                    break;
                }

                if(aiSummary.isWon())
                {
                    System.out.println("You lost!");
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
        Position pos;
        do {
            System.out.println("Please enter a valid field: ");
            posStr = input.nextLine().toUpperCase();

            if (posStr.equals("QUIT")) {
                System.exit(0);
            }
        } while ((pos = new Position(posStr)) == null);
        
        return pos;
    }
}
