# CLI Chess
Java-based chess game in the Command Line Interface (CLI)

## Description
This project is a Java-based chess game that runs in the console. It features a complete chess game logic including all the standard chess rules, move validations, and a simple text-based UI to display the chessboard and the status of the game.

## Features
- Play chess in a command-line interface
- Supports basic chess rules
- Simple and intuitive text-based user interface


## Installation
To install CLI Chess, follow these steps:
1. Ensure you have Java OpenJDK 21 and Maven Installed on your system.
2. Clone the repository: `git clone https://github.com/ndon01/CLIChess.git`
3. Navigate to the cloned directory: `cd CLI-Chess`
4. Compile the program using the following command:
```bash
mvn package
   ```
5. Finally, Run the program using the following command:
```bash
java -jar target/CLIChess.jar
```
## Usage
After starting the game in the console, players can input their moves using standard chess notation (e.g., e2e4 to move a piece from e2 to e4).
Here is a list of possible commands:

| Command | Example | Info | Allowed In Game | Allowed out of Game |
|---------|---------|------|---------------|--------------------|
| ng [player1 name] [player2 name] | ng joe jane | creates a new game with player1 and player2, if called while in a game the current game will be overwritten | ✅              | ✅                   |
| mv [piecePosition][toPosition] | mv a2a3 | tries to move a friendly piece at piecePosition to the requested position, toPosition. | ✅              |                    |
| cp [piecePosition][capturePosition] | cp a2a3 | tries to capture an opposing piece at the capturePosition with the friendly piece at piecePosition. | ✅             |                    |
| undo | undo | undoes the last move. | ✅             |                    |
| undo [x] | undo 5 | undoes the last x number of moves. | ✅             |                    |
| print status | print status | prints the current board. | ✅             |                    |
| help | help | shows a list of all possible commands | ✅             | ✅                  |
