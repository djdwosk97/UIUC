package testbench;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import controller.Chess;

import board.Board;
import pieces.Piece;
import pieces.King;
import pieces.Pawn;
import pieces.Bishop;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;
import Player.Player;
import pieces.Grasshopper;
import pieces.Berolina;
import coord.Coord;

public class Testbench {
	@Test
	/**
	 * test buildBoard(int, int) and fillboard()
	 */
	public void testBuildBoardDefault(){
		Piece[][] pieces = Board.buildBoard(8,8);
		if(pieces[0].length != 8)
			fail("incorrect board height");
		if(pieces.length != 8)
			fail("incorrect board width");
		for(int y=0; y<2; y++){
			for(int x=0; x<8; x++){
				if(pieces[x][y] == null)
					fail("incorrect BLACK piece layout");
			}
		}
		for(int y=7; y>5; y--){
			for(int x=0; x<8; x++){
				if(pieces[x][y] == null)
					fail("incorrect WHITE piece layout");
			}
		}
	}
	
	@Test
	/**
	 * Test Player constructor
	 */
	public void testPlayerConstructor(){
		Player activePlayer = new Player(Color.WHITE, "BLACK");
		if(activePlayer.getMoveNumber() != 0 || activePlayer.getColor() != Color.WHITE)
			fail("Failed to correctly define a player");
	}
	
	@Test
	/**
	 * Test Piece constructor
	 */
	public void testPieceConstructor(){
		Piece activePiece = new King(Color.BLACK, 4, 5);
		if(activePiece.getColor() != Color.BLACK || activePiece.getX() != 4 || activePiece.getY() != 5)
			fail("Failed to correctly define piece");
	}
	
	@Test
	/**
	 * Test Copy constructor
	 */
	public void testCopyConstructor(){
		Piece original = new Pawn(Color.BLACK, 0, 0);
		Piece other = new Pawn(Color.BLACK, 4, 0);
		Piece clone = original.copy();
		if(original.getColor() != clone.getColor())
			fail("Failed to copy a piece.");
	}
	
	@Test
	/**
	 * Test validPosition
	 */
	public void testValidPosition(){
		if(!Board.validPosition(7, 7, 8, 8))
			fail("validPosition failed");
		if(Board.validPosition(-1, 0, 20, 20))
			fail("validPosition failed on negatives");
	}
	
	@Test
	/**
	 * Test activePlayersPiece
	 */
	public void testActivePlayersPiece(){
		Piece activePiece = new King(Color.BLACK, 4, 4);
		Player activePlayer = new Player(Color.BLACK, "BLACK");
		if(!Board.activePlayersPiece(activePiece, activePlayer)){
			fail("failed to correctly identify a player's piece");
		}
	}
	
	@Test
	/**
	 * Test King move validation and movement/capture 
	 */
	public void testKingMoveValidation(){
		Piece[][] testBoard = new Piece[8][8];
		Player[] newPlayers = Board.addPlayers("BLACK", "WHITE");
		testBoard[4][3] = new King(Color.BLACK, 4, 3);
		if(!Board.movePiece(3, 4, 4, 3, testBoard, testBoard[4][3])) //test valid move
			fail("This was a valid move.");
		if(Board.movePiece(7, 3, 3, 4, testBoard, testBoard[3][4])) //test invalid move
			fail("This was an invalid move.");
		testBoard[4][5] = new Pawn(Color.BLACK, 4, 5);
		if(Board.movePiece(4, 5, 3, 4, testBoard, testBoard[3][4])) //test friendly capture
			fail("You cannot capture a friendly piece.");
		testBoard[2][4] = new Pawn(Color.WHITE, 2, 4);
		if(!Board.movePiece(2, 4, 3, 4, testBoard, testBoard[3][4])) //test valid capture
			fail("This was a valid capture.");
		testBoard[1][7] = new Rook(Color.WHITE, 1, 7);
		if(Board.movePiece(1, 4, 2, 4, testBoard, testBoard[2][4])) //test self-check
			fail("You cannot put the king in check.");
	}
	
	@Test
	/**
	 * Test Bishop move validation and movement/capture
	 */
	public void testBishopMoveValidation(){
		Piece[][] testBoard = new Piece[8][8];
		Player[] newPlayers = Board.addPlayers("","");
		testBoard[4][4] = new Bishop(Color.BLACK, 4, 4);
		if(Board.movePiece(5, 4, 4, 4, testBoard, testBoard[4][4])) //test invalid move
			fail("This is an invalid move.");
		if(!Board.movePiece(6, 6, 4, 4, testBoard, testBoard[4][4])) //test valid move
			fail("This is a valid move.");
		testBoard[7][5] = new Pawn(Color.BLACK, 7, 5); 
		if(Board.movePiece(7, 5, 6, 6, testBoard, testBoard[6][6])) //test invalid capture
			fail("This is an invalid capture.");
		if(!Board.movePiece(2, 2, 6, 6, testBoard, testBoard[6][6])) //test valid capture 
			fail("You can capture an opponent's piece.");
		testBoard[2][0] = new King(Color.BLACK, 2, 0);
		testBoard[2][7] = new Rook(Color.WHITE, 2, 7);
		if(Board.movePiece(3, 3, 2, 2, testBoard, testBoard[2][2])) //test putting king in check
			fail("This puts the king in check.");
		testBoard[1][3] = new Pawn(Color.BLACK, 1, 3);
		testBoard[0][4] = new Pawn(Color.WHITE, 0, 4);
		if(Board.movePiece(0, 4, 2, 2, testBoard, testBoard[2][2])) //test blocked path
			fail("This path is blocked.");
	}

	@Test
	/**
	 * Test Knight move validation and movement/capture
	 */
	public void testKnightMoveValidation(){
		Piece[][] testBoard = new Piece[8][8]; 
		Player[] newPlayers = Board.addPlayers("",""); 
		testBoard[4][4] = new Knight(Color.BLACK, 4, 4);
		if(Board.movePiece(5, 4, 4, 4, testBoard, testBoard[4][4])) //test invalid move
			fail("This is an invalid move.");
		if(!Board.movePiece(2, 5, 4, 4, testBoard, testBoard[4][4])) //test valid move
			fail("This is a valid move.");
		testBoard[4][4] = new Pawn(Color.BLACK, 4, 4); 
		if(Board.movePiece(4, 4, 2, 5, testBoard, testBoard[2][5])) //test invalid capture
			fail("This is an invalid capture.");
		testBoard[4][6] = new Pawn(Color.WHITE, 4, 6);
		if(!Board.movePiece(4, 6, 2, 5, testBoard, testBoard[2][5])) //test valid capture 
			fail("You can capture an opponent's piece.");
		testBoard[2][0] = new King(Color.BLACK, 4, 7);
		testBoard[2][7] = new Rook(Color.WHITE, 4, 5);
		if(Board.movePiece(6, 5, 4, 6, testBoard, testBoard[4][6])) //test putting king in check
			fail("This puts the king in check.");
	}
	
	@Test
	/**
	 * Test Pawn move validation and movement/capture
	 */
	public void testPawnMoveValidation(){	
		Piece[][] testBoard = new Piece[8][8]; 
		Player[] newPlayers = Board.addPlayers("",""); 
		testBoard[3][1] = new Pawn(Color.BLACK, 3, 1);
		if(!Board.movePiece(3, 3, 3, 1, testBoard, testBoard[3][1])) //test valid pawn jump
			fail("This is a valid pawn jump.");
		if(Board.movePiece(3, 5, 3, 3, testBoard, testBoard[3][3])) //test invalid pawn jump
			fail("This is an invalid pawn jump.");
		if(Board.movePiece(4, 3, 3, 3, testBoard, testBoard[3][3])) //test invalid move
			fail("This is an invalid move.");
		if(!Board.movePiece(3, 4, 3, 3, testBoard, testBoard[3][3])) //test valid move
			fail("This is an valid move.");
		testBoard[4][5] = new Pawn(Color.BLACK, 4, 5);
		if(Board.movePiece(4, 5, 3, 4, testBoard, testBoard[3][4])) //test friendly capture
			fail("This is an invalid friendly capture.");
		testBoard[2][5] = new Pawn(Color.WHITE, 2, 5);
		if(!Board.movePiece(2, 5, 3, 4, testBoard, testBoard[3][4])) //test valid capture
			fail("This is a valid capture.");
		testBoard[0][6] = new King(Color.BLACK, 0, 6);
		testBoard[5][6] = new Rook(Color.WHITE, 5, 6); 
		testBoard[2][6] = new Pawn(Color.BLACK, 2, 6);
		if(Board.movePiece(2, 7, 2, 6, testBoard, testBoard[2][6])) //test putting king in check
			fail("You can't put the king in check.");
		testBoard[1][1] = new Pawn(Color.BLACK, 1, 1);
		testBoard[1][2] = new Pawn(Color.WHITE, 1, 2);
		if(Board.movePiece(1, 3, 1, 1, testBoard, testBoard[1][1])) //test blocked path
			fail("The destination is blocked.");
		if(Board.movePiece(1, 2, 1, 1, testBoard, testBoard[1][1])) //test blocked destination
			fail("The destination is blocked.");
	}
	
	@Test
	/**
	 * Test Rook move validation and movement/capture
	 */
	public void testRookMoveValidation(){
		Piece[][] testBoard = new Piece[8][8];
		Player[] newPlayers = Board.addPlayers("",""); 
		testBoard[0][0] = new Rook(Color.BLACK, 0, 0);
		if(Board.movePiece(4, 4, 0, 0, testBoard, testBoard[0][0])) //test invalid move
			fail("This is an invalid move.");
		if(!Board.movePiece(4, 0, 0, 0, testBoard, testBoard[0][0])) //test valid move
			fail("This is a valid move.");
		testBoard[6][0] = new Pawn(Color.BLACK, 6, 0);
		if(Board.movePiece(6, 0, 4, 0, testBoard, testBoard[4][0])) //test invalid move
			fail("This is an invalid capture.");
		testBoard[4][4] = new Pawn(Color.WHITE, 4, 4);
		if(!Board.movePiece(4, 4, 4, 0, testBoard, testBoard[4][0])) //test valid move
			fail("This is a valid capture.");
		testBoard[3][4] = new King(Color.BLACK, 3, 4);
		testBoard[6][4] = new Rook(Color.WHITE, 6, 4);
		if(Board.movePiece(4, 5, 4, 4, testBoard, testBoard[4][4])) //test invalid move
			fail("You can't put your king in check.");
	}
	
	@Test
	/**
	 * Test Queen move validation and movement/capture
	 */
	public void testQueenMoveValidation(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[3][3] = new Queen(Color.BLACK, 3, 3);
		testBoard[3][5] = new Pawn(Color.BLACK, 3, 5); //on path
		testBoard[4][4] = new Pawn(Color.BLACK, 4, 4); //on diagonal path
		if(Board.movePiece(3, 7, 3, 3, testBoard, testBoard[3][3]))
			fail("There is a piece in your way, you can't make this move1.");
		if(Board.movePiece(5, 5, 3, 3, testBoard, testBoard[3][3]))
			fail("There is a piece in your way, you can't make this move.");
		
		testBoard[3][1] = new Pawn(Color.WHITE, 3, 1); //blocking horizontal destination
		if(!Board.movePiece(3, 1, 3, 3, testBoard, testBoard[3][3]))
			fail("This was a legal move and capture1.");
	}
	
	@Test
	/**
	 * See if opponent's king is in check
	 */
	public void testInCheck(){
		Piece[][] testBoard = new Piece[8][8];
		testBoard[0][0] = new King(Color.BLACK, 0, 0);
		testBoard[1][7] = new Rook(Color.WHITE, 1, 7); 
		Player[] testPlayers = Board.addPlayers("", "");
		if(Board.inCheck(testBoard, testPlayers[0]))
			fail("You are not in Check so you shouldn't see this.");
		testBoard[7][0] = new Rook(Color.WHITE, 7, 0);
		if(!Board.inCheck(testBoard, testPlayers[0]))
			fail("You are in Check so you shouldn't see this.");
	}
	
	
	@Test
	/**
	 * See if opponent can move a piece to block check
	 */
	public void testOpponentCanBlock(){
		Piece[][] testBoard = new Piece[8][8];
		testBoard[0][0] = new King(Color.BLACK, 0, 0);
		testBoard[1][7] = new Rook(Color.WHITE, 1, 7); 
		Player[] testPlayers = Board.addPlayers("","");
		if(!Board.opponentCanBlock(testBoard, testPlayers[0])) 
			fail("You did not lose, so you shouldn't see this.");
		testBoard[0][7] = new Rook(Color.WHITE, 0, 7);
		if(Board.opponentCanBlock(testBoard, testPlayers[0]))
			fail("You lost, so you shouldn't see this.");
		testBoard[1][1] = new Rook(Color.BLACK, 1, 1);
		if(!Board.opponentCanBlock(testBoard, testPlayers[0]))
			fail("You were able to successfully block your opponent's check, you should not see this.");
	}
	
	@Test
	/**
	 * See if opponent's king is in check 
	 */
	public void testcheckMate(){
		Piece[][] testBoard = new Piece[8][8];
		testBoard[0][0] = new King(Color.BLACK, 0, 0);
		testBoard[1][7] = new Rook(Color.WHITE, 1, 7); 
		Player[] testPlayers = Board.addPlayers("","");
		if(Board.checkmate(testBoard, testPlayers[0])) 
			fail("You did not lose, so you shouldn't see this.");
		testBoard[0][7] = new Rook(Color.WHITE, 0, 7);
		if(!Board.checkmate(testBoard, testPlayers[0]))
			fail("You lost, so you shouldn't see this.");
		testBoard[1][1] = new Rook(Color.BLACK, 1, 1);
		if(Board.checkmate(testBoard, testPlayers[0]))
			fail("You were able to successfully block your opponent's check, you should not see this.");
		
	}
	
	@Test
	/**
	 * Stalemate
	 */
	public void testStalemate(){
		Piece[][] testBoard = new Piece[8][8]; 
		Player[] testPlayers = Board.addPlayers("", "");
		testBoard[0][0] = new King(Color.BLACK, 0, 0);
		testBoard[1][5] = new Rook(Color.WHITE, 1, 5); 
		testBoard[5][1] = new Rook(Color.WHITE, 5, 1);
		if(!Board.stalemate(testBoard, testPlayers[0]))
			fail("You have no more valid moves.");
	}
	
	@Test
	/**
	 * See if move handler works correctly
	 */
	public void testMoveHandler(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[0][0] = new Rook(Color.BLACK, 0, 0);
		Board.movePiece(4, 0, 0, 0, testBoard, testBoard[0][0]);
		if(testBoard[4][0] == null)
			fail("FAILED");
		if(!(testBoard[4][0].getColor() == Color.BLACK && testBoard[4][0].getX() == 4))
			fail("Failed to properly move piece.");
		if(!(testBoard[0][0] == null))
			fail("Failed to properly move piece.");
	}
	
	
	@Test
	/**
	 * Test tempMove
	 */
	public void testTestTempMove(){
		Piece[][] testBoard = new Piece[8][8]; 
		Player[] opponent = Board.addPlayers("","");
		testBoard[4][4] = new King(Color.BLACK, 4, 4);
		testBoard[7][7] = new King(Color.WHITE, 7, 7);
		testBoard[5][6] = new Rook(Color.WHITE, 5, 6);
		assertFalse(Board.testTempMove(5, 4, 4, 4, testBoard, opponent[0]));
	}
	
	
	@Test
	/**
	 * Test putting self into check
	 */
	public void selfCheck(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[4][4] = new King(Color.BLACK, 4, 4);
		testBoard[5][5] = new Rook(Color.WHITE, 5, 5);
		assertFalse(Board.movePiece(5, 4, 4, 4, testBoard, testBoard[4][4]));
	}
	
	@Test 
	/**
	 * Test Grasshopper movement
	 */
	public void testGrasshopperMovement(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[0][0] = new King(Color.BLACK, 0, 0);
		testBoard[7][7] = new King(Color.WHITE, 7, 7);
		testBoard[3][3] = new Grasshopper(Color.BLACK, 3, 3);
		assertFalse(Board.movePiece(3, 7, 3, 3, testBoard, testBoard[3][3]));
		testBoard[3][4] = new Pawn(Color.BLACK, 3, 4);
		assertTrue(Board.movePiece(3, 7, 3, 3, testBoard, testBoard[3][3]));
	}
	
	@Test
	/**
	 * Test Berolina movement
	 */
	public void testBerolinaMovement(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[0][0] = new King(Color.BLACK, 0, 0); 
		testBoard[7][7] = new King(Color.WHITE, 7, 7);
		testBoard[2][1] = new Berolina(Color.BLACK, 2, 1);
		testBoard[6][6] = new Berolina(Color.WHITE, 6, 6);
		assertFalse(Board.movePiece(6, 4, 6, 6, testBoard, testBoard[6][6]));
		assertFalse(Board.movePiece(6, 5, 6, 6, testBoard, testBoard[6][6]));
		assertTrue(Board.movePiece(4, 4, 6, 6, testBoard, testBoard[6][6])); //berolina jump
		assertFalse(Board.movePiece(2, 2, 4, 4, testBoard, testBoard[4][4]));
		assertTrue(Board.movePiece(3, 3, 4, 4, testBoard, testBoard[4][4])); //move diagonally
		testBoard[3][2] = new Pawn(Color.BLACK, 3, 2); 
		assertTrue(Board.movePiece(3, 2, 3, 3, testBoard, testBoard[3][3])); //capture head on
		assertFalse(Board.movePiece(2, 2, 2, 1, testBoard, testBoard[2][1]));
		assertFalse(Board.movePiece(2, 3, 2, 1, testBoard, testBoard[2][1]));
		testBoard[3][2] = null;
		assertFalse(Board.movePiece(1, 0, 2, 1, testBoard, testBoard[2][1]));
		assertTrue(Board.movePiece(4, 3, 2, 1, testBoard, testBoard[2][1])); //berolina jump
	}
	
	@Test
	/**
	 * Test showPossibleMoves
	 */
	public void testShowPossibleMoves(){
		Piece[][] testBoard = new Piece[8][8]; 
		testBoard[5][5] = new King(Color.BLACK, 5, 5);
		testBoard[0][0] = new King(Color.WHITE, 0, 0);
		Player[] currentPlayers = Board.addPlayers("blackPlayer", "whitePlayer");
		Coord[] possibleMoves = Board.possibleMoves(testBoard, testBoard[5][5], currentPlayers[0]);
		assertTrue(possibleMoves[0].getX() == 4 && possibleMoves[0].getY() == 4);
		assertTrue(possibleMoves[6].getX() == 6 && possibleMoves[6].getY() == 5);
		assertTrue(possibleMoves[8] == null);
	}
}
