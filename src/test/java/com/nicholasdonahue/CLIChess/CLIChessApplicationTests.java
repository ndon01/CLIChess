package com.nicholasdonahue.CLIChess;

import org.junit.Before;
import org.junit.Test;

import com.nicholasdonahue.CLIChess.util.Game.*;
import com.nicholasdonahue.CLIChess.util.Pieces.*;

import static org.junit.Assert.*;

public class CLIChessApplicationTests {

	private Game game;

	@Before
	public void setUp() {
		game = new Game("Alice", "Bob");
	}

	@Test
	public void testPawnMovement() {
		// Test that a pawn can move forward one space
		Move move = new Move("a2a3"); // assuming this is the correct format for your Move constructor
		boolean result = game.move(move);

		assertTrue("Pawn should be able to move forward one space", result);

		// Test that the pawn is now in the correct place
		assertNotNull("There should be a piece at a3", game.getPiece(5, 0));
		assertTrue("Piece at a3 should be a Pawn", game.getPiece(5, 0) instanceof Pawn);

		// Test that a pawn cannot move backwards
		move = new Move("a3a2");
		result = game.move(move);
		assertFalse("Pawn should not be able to move backwards", result);
	}

	// write tests for other pieces

}
