// Nicholas Donahue 2023
package com.nicholasdonahue.CLIChess;


import com.nicholasdonahue.CLIChess.util.Game.*;
import com.nicholasdonahue.CLIChess.util.PrintableTable.PrintableTable;

import java.util.Scanner;
public class CLIChessApplication {
    // Method to verify if a move string is valid, checking format and bounds
    public static boolean verifyMoveString(String[] moveCodeSplit) {
        // Extracting coordinates from the move string
        String a0 = moveCodeSplit[0];
        String b0 = moveCodeSplit[1];
        String a1 = moveCodeSplit[2];
        String b1 = moveCodeSplit[3];

        // Check if the numeric parts of the move are actually digits
        if (!Character.isDigit(b0.charAt(0)) || !Character.isDigit(b1.charAt(0))) {
            return false;
        }

        // Define legal letters for the chess board columns
        String legalLetters = "abcdefgh";
        // Check if the letter parts of the move are within the legal letters
        if (!legalLetters.contains(a0.toLowerCase()) || !legalLetters.contains(a1.toLowerCase())) {
            return false;
        }

        // Parse the numeric parts of the move and check if they are within the chess board bounds
        int b0i = Integer.parseInt(b0);
        int b1i = Integer.parseInt(b1);
        if (b0i > 8 || b0i < 1 || b1i > 8 || b1i < 1) {
            return false;
        }

        return true;
    }

    public static void printCommands(boolean inGame) {
        PrintableTable welcome = new PrintableTable();
        welcome.createHeader("Command");
        welcome.createHeader("Example");
        welcome.createHeader("Info");
        welcome.createHeader("In Game");
        welcome.createHeader("Out of Game");
        welcome.createHeader("Available");



        welcome.createRow("ng [player1 name] [player2 name]", "ng joe jane", "creates a new game with player1 and player2, if called while in a game the current game will be overwritten", "x", "x", "x");
        welcome.createRow("mv [piecePosition][toPosition]", "mv a2a3", "tries to move a friendly piece at piecePosition to the requested position, toPosition.", "x", "", inGame ? "x" : "");
        welcome.createRow("cp [piecePosition][capturePosition]", "cp a2a3", "tries to capture an opposing piece at the capturePosition with the friendly piece at piecePosition.", "x", "", inGame ? "x" : "");
        welcome.createRow("undo", "undo", "undoes the last move.", "x", "", inGame ? "x" : "");
        welcome.createRow("undo [x]", "undo 5", "undoes the last x number of moves.", "x", "", inGame ? "x" : "");
        welcome.createRow("print status", "print status", "prints the current board.", "x", "", inGame ? "x" : "");
        welcome.createRow("help", "help", "shows a list of all possible commands", "x", "x", "x");
        welcome.createRow("exit", "exit", "quits the game", "x", "x", "x");


        welcome.print();
    }

    // Main method, the entry point of the program
    public static void main(String[] args) {
        Scanner InputListen = new Scanner(System.in); // Scanner to read user input

        Game currentGame = null; // Variable to hold the current game

        // Initial greeting and command list
        System.out.println("\nWelcome to CLI-Chess!\n");
        printCommands(false);
        System.out.println("\nEnter a command:");

        // Main game loop
        while (currentGame == null || !currentGame.getWinnerStatus()) {
            String nextInput = InputListen.nextLine();
            String[] splitted = nextInput.split(" ");
            if (splitted.length == 0) continue;

            /*========== new util.game ==========*/
            if (splitted[0].equalsIgnoreCase("ng")) {
                if (splitted.length < 3) { continue; }
                currentGame = new Game(splitted[1], splitted[2]);

                currentGame.showBoard(System.out, true);
            };

            if (splitted[0].equalsIgnoreCase("help")) {
                printCommands(currentGame != null);
            }

            /*========== move piece ==========*/
            if (splitted[0].equalsIgnoreCase("mv")) {
                if (currentGame == null) { continue; } // ensure there is an active util.game
                if (splitted.length < 2) { continue; }

                String moveCode = splitted[1];
                String[] moveCodeSplit = moveCode.split("");

                if (moveCodeSplit.length < 4) { continue; } // ensure there are enough values

                if (!verifyMoveString(moveCodeSplit)) { System.out.println("illegal move"); continue; }

                Move thisMove = new Move(moveCode);
                boolean turn = currentGame.isWhiteTurn();
                if (currentGame.move(thisMove)) {
                    System.out.printf("%s moves %s\n\n", turn ? currentGame.getPlayer1() : currentGame.getPlayer2(), moveCode);
                    currentGame.showBoard(System.out, true);

                } else {
                    System.out.println("illegal move");
                    continue;
                }

            }

            /*========== capture piece ==========*/
            if (splitted[0].equalsIgnoreCase("cp")) {
                if (currentGame == null) { continue; } // ensure there is an active util.game
                if (splitted.length < 2) { continue; }

                String moveCode = splitted[1];
                String[] moveCodeSplit = moveCode.split("");

                if (moveCodeSplit.length < 4) { continue; }

                if (!verifyMoveString(moveCodeSplit)) { System.out.println("illegal capture"); continue; }

                Move thisMove = new Move(moveCode);
                boolean turn = currentGame.isWhiteTurn();
                if (currentGame.move(thisMove, true)) {

                    System.out.printf("%s captures: %s\n\n", turn ? currentGame.getPlayer1() : currentGame.getPlayer2(), moveCode);
                    currentGame.showBoard(System.out, true);
                } else {
                    System.out.println("illegal capture");
                    continue;
                }

            }

            /*========== undo move ==========*/
            if (splitted[0].equalsIgnoreCase("undo")) {
                if (currentGame == null) { continue; }

                boolean isDigits = true;

                if (splitted.length > 1) {
                    for (char i : splitted[1].toCharArray()) {
                        if (!Character.isDigit(i)) {
                            isDigits = false;
                        }
                    }
                } else { isDigits = false; }

                if (isDigits) {
                    System.out.println("Undoing " + splitted[1] + " moves.");
                    currentGame.reverse(Integer.parseInt(splitted[1]));
                } else {
                    System.out.println("Undoing 1 move.");
                    currentGame.undoLastMove();
                }

                currentGame.showBoard(System.out, true);
            }

            /*========== print status ==========*/
            if (splitted[0].equalsIgnoreCase("print") && splitted[1].equalsIgnoreCase("status")) {
                if (currentGame == null) { continue; }
                currentGame.printStatus();
            }

            if (splitted[0].equalsIgnoreCase("exit")) {
                break;
            }

        }
    }
}
