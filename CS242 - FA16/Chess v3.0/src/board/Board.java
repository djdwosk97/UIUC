package board;
import java.awt.Color;

import controller.Chess;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import Player.Player;
import coord.Coord; 

public class Board { 
	public static Piece[][] pieces; 
	public static Player[] newPlayers;
	public static Piece previousPositionOfActivePiece; 
	public static Piece previousPieceAtDestination;
	
	/**
	 * Build a standard 8x8 chess board
	 * @return a 2-D array of pieces
	 */
	public static Piece[][] buildDefaultBoard(){
		pieces = fillBoard(8,8);
		return pieces; 
	}
	
	/**
	 * Build the board, height and width
	 * @param width
	 * @param height
	 * @return a 2-D array of pieces
	 */
	public static Piece[][] buildBoard(int width, int height){
		pieces = fillBoard(width, height);
		return pieces; 
	}
	
	/**
	 * Initialize all the pieces on the board. 
	 * @param width
	 * @param height
	 * @return a 2-D array of pieces (the board).
	 */
	public static Piece[][] fillBoard(int width, int height){
		pieces = new Piece[width][height];
		pieces[0][0] = new Rook(Color.BLACK, 0, 0); 
		pieces[1][0] = new Knight(Color.BLACK, 1, 0);
		pieces[2][0] = new Bishop(Color.BLACK, 2, 0);
		pieces[3][0] = new King(Color.BLACK, 3, 0);
		pieces[4][0] = new Queen(Color.BLACK, 4, 0);
		pieces[5][0] = new Bishop(Color.BLACK, 5, 0);
		pieces[6][0] = new Knight(Color.BLACK, 6, 0);
		pieces[7][0] = new Rook(Color.BLACK, 7, 0);

		pieces[0][7] = new Rook(Color.WHITE, 0, 7);
		pieces[1][7] = new Knight(Color.WHITE, 1, 7);
		pieces[2][7] = new Bishop(Color.WHITE, 2, 7);
		pieces[3][7] = new King(Color.WHITE, 3, 7);
		pieces[4][7] = new Queen(Color.WHITE, 4, 7);
		pieces[5][7] = new Bishop(Color.WHITE, 5, 7);
		pieces[6][7] = new Knight(Color.WHITE, 6, 7);
		pieces[7][7] = new Rook(Color.WHITE, 7, 7);
		
		for(int i = 0; i < width; i++){
			pieces[i][1] = new Pawn(Color.BLACK, i, 1);
			pieces[i][6] = new Pawn(Color.WHITE, i, 6);
		}
		
		return pieces; 
	}
	
	/**
	 * Add 2 players, one black and one white.
	 * If the custom names are not specified, 
	 * then they will default to "Black" and "White".
	 * @return an array of players
	 */
	public static Player[] addPlayers(String blackPlayer, String whitePlayer){
		newPlayers = new Player[2];
		if(blackPlayer == null || (blackPlayer.length() < 1))
			blackPlayer = "Black";
		if(whitePlayer == null || (whitePlayer.length() < 1))
			whitePlayer = "White";
		newPlayers[0] = new Player(Color.BLACK, blackPlayer);
		newPlayers[1] = new Player(Color.WHITE, whitePlayer);
		return newPlayers;
	}
	
	
	/**
	 * Check to see if the current player owns the selected piece. 
	 * @param activePiece
	 * @param activePlayer
	 * @return true if the piece belongs to the active player.
	 */
	public static boolean activePlayersPiece(Piece activePiece, Player activePlayer){
		return (activePiece.getColor() == activePlayer.getColor());
	}
	
	/**
	 * Validate that the destination is valid and is a valid move for the selected piece. 
	 * Move the active piece to the destination and capture an opponents piece if it is capturable. 
	 * This function will also ensure that the move does not put the king in check. 
	 * @param destX
	 * @param destY
	 * @param x
	 * @param y
	 * @param pieces
	 * @param activePiece
	 * @return true if the piece is moved.
	 * @return false if the destination is off the board, not a valid move, or if the active piece is null.
	 */
	public static boolean movePiece(int destX, int destY, int x, int y, Piece[][] pieces, Piece activePiece){
		if(!validPosition(destX, destY, pieces.length, pieces[0].length))
			return false; 
		if(pieces[x][y] == null)
			return false; 
		if(activePiece.validMove(destX, destY, pieces, activePiece)){
			if(!Board.testTempMoveHelper(destX, destY, x, y, pieces))
				return false; 
			if(pieces[destX][destY] != null)
				previousPieceAtDestination = pieces[destX][destY].copy();
			else 
				previousPieceAtDestination = null; 
			previousPositionOfActivePiece = pieces[x][y].copy();
			moveHandler(destX, destY, x, y, pieces);
			if(activePiece.getIsKing()){
				Chess.getActivePlayer().setKing(pieces[destX][destY]);
			}
				
			return true; 
		}
		return false; 
	}
	
	/**
	 * Check if the destination is a valid space on the board.
	 * @param x 
	 * @param y
	 * @return true if (x,y) is a valid set of coordinates.
	 */
	public static boolean validPosition(int destX, int destY, int width, int height){
		return (destX < width && destY < height && destX >= 0 && destY >= 0);
	}
	
	/**
	 * This will only be called from within movePiece. It is guaranteed that the destination is on the board, 
	 * that the move is valid for the active piece, that the destination is either empty or occupied by an opponent's piece, 
	 * that the path is clear, and that the moves does not put the king in check.
	 * @param destX
	 * @param destY
	 * @param x
	 * @param y
	 * @param pieces
	 */
	public static void moveHandler(int destX, int destY, int x, int y, Piece[][] pieces){
		if(pieces[x][y] == null)
			return;
		if(pieces[x][y] != null && pieces[x][y].getNeverMoved() == true) { //if it's a pawn and it's never been moved
			pieces[x][y].setNeverMoved(false); 
		}	
			
		if(pieces[destX][destY] != null){ //capture piece
			pieces[destX][destY].setOnBoard(false); 
			pieces[destX][destY] = null;
		}
		pieces[destX][destY] = pieces[x][y].copy();
		pieces[destX][destY].setX(destX);
		pieces[destX][destY].setY(destY); 
		pieces[destX][destY].setNeverMoved(pieces[x][y].getNeverMoved());
		pieces[x][y] = null; 
	}
	
	/**
	 * Test if your opponent (the defender) is in check
	 * @param pieces
	 * @param opponent
	 * @return true if opponent is in check
	 */
	public static boolean inCheck(Piece[][] pieces, Player opponent){
		Piece oppKing = findKing(pieces, opponent); 
		for(int x = 0; x < pieces.length; x++){ //see if any piece can capture opponent's king
			for(int y = 0; y < pieces[0].length; y++){
				if(pieces[x][y] != null && (pieces[x][y].getColor() != opponent.getColor())){
					if(pieces[x][y].validMove(oppKing.getX(), oppKing.getY(), pieces, pieces[x][y])){ //a valid move to an opponent's king
						return true; 
					}
				}
			}
		}
		return false; 
	}
	
	/**
	 * Test if the game is over
	 * if your opponent is in check and has no moves to get out of check, then checkmate
	 * @param pieces
	 * @param opponent
	 * @return true if your opponent loses
	 */
	public static boolean checkmate(Piece[][] pieces, Player opponent){
		if((opponentCanBlock(pieces, opponent))){
			return false; 
		}
		return true; 
	}
	
	/**
	 * Test if the game is over
	 * If your opponent is NOT in check and has no legal moves, then stalemate
	 * @param pieces
	 * @param players
	 * @return true if stalemate, else false
	 */
	public static boolean stalemate(Piece[][] pieces, Player opponent){
		if(!opponentCanBlock(pieces, opponent) && !inCheck(pieces, opponent))
			return true;
		return false;
	}
	
	/**
	 * See if the opponent can move any piece to protect the king
	 * @param pieces
	 * @param opponent
	 * @return true if they can, else false
	 */
	public static boolean opponentCanBlock(Piece[][] pieces, Player opponent){
		for(int x=0; x<pieces.length; x++){
			for(int y=0; y<pieces[0].length; y++){
				for(int testMoveX = 0; testMoveX < pieces.length; testMoveX++){
					for(int testMoveY = 0; testMoveY < pieces[0].length; testMoveY++){
						if(pieces[x][y] != null && pieces[x][y].getColor() == opponent.getColor() && pieces[x][y].validMove(testMoveX, testMoveY, pieces, pieces[x][y])){
							if(testTempMove(testMoveX, testMoveY, x, y, pieces, opponent)){
								return true;
							}
						}
					} //end testY for loop
				} //end testX for loop
			} //end y for loop 
		} //end x for loop 
		return false; 
	}

	/**
	 * Test a move to see if it puts the king in check. 
	 * The move is guaranteed to be valid for the piece. 
	 * @param destX
	 * @param destY
	 * @param x
	 * @param y
	 * @param pieces
	 * @return true if the move is valid and does not put the king in check (or takes the king out of check). Else false
	 */
	public static boolean testTempMove(int destX, int destY, int x, int y, Piece[][] pieces, Player opponent){
		boolean moveStatus = pieces[x][y].getNeverMoved();
		if(pieces[destX][destY] != null){
			Piece destinationPiece = pieces[destX][destY].copy();
			moveHandler(destX, destY, x, y, pieces);
			if(inCheck(pieces, opponent)){
				moveHandler(x, y, destX, destY, pieces); 
				pieces[destX][destY] = destinationPiece; 
				pieces[x][y].setNeverMoved(moveStatus);
				return false; 
			}
			moveHandler(x, y, destX, destY, pieces); 
			pieces[destX][destY] = destinationPiece; 
			pieces[x][y].setNeverMoved(moveStatus);
			return true; 
		} else { 
			moveHandler(destX, destY, x, y, pieces);
			if(inCheck(pieces, opponent)){
				moveHandler(x, y, destX, destY, pieces); 
				pieces[x][y].setNeverMoved(moveStatus);
				return false; 
			}
			moveHandler(x, y, destX, destY, pieces);
			pieces[x][y].setNeverMoved(moveStatus);
			return true; 
		}
	}
	
	/**
	 * Helper function for testMove
	 * @param destX
	 * @param destY
	 * @param x
	 * @param y
	 * @param pieces
	 * @return true if the move is valid and does not put the king in check 
	 */
	public static boolean testTempMoveHelper(int destX, int destY, int x, int y, Piece[][] pieces){
		if(pieces[x][y] != null && pieces[x][y].getColor() == newPlayers[0].getColor()){
			return(testTempMove(destX, destY, x, y, pieces, newPlayers[0]));
		}
		else{
			return(testTempMove(destX, destY, x, y, pieces, newPlayers[1]));
		}
	}
	
	/**
	 * Find opponent's king
	 * @param pieces
	 * @param opponent
	 * @return the king
	 */
	public static Piece findKing(Piece[][] pieces, Player opponent){
		Piece king = null; 
		for(int x = 0; x < pieces.length; x++){ 
			for(int y = 0; y < pieces[0].length; y++){
				if(pieces[x][y] != null && pieces[x][y].getIsKing() == true && pieces[x][y].getColor() == opponent.getColor()){
					king = pieces[x][y];
				}
			}
		}
		return king; 
	}
	
	/**
	 * Takes in an array of pieces and tests every possible destination 
	 * for active piece to see if it's valid.
	 * @param pieces
	 * @param activePiece
	 * @return a coordinate array containing an (x,y) pair of every valid destination
	 */
	public static Coord[] possibleMoves(Piece[][] pieces, Piece activePiece, Player currentPlayer){
		if(activePiece == null || (activePiece.getColor() != currentPlayer.getColor()))
			return null;
		Coord[] possibleMoves = new Coord[64]; 
		int numMoves = 0; 
		for(int x = 0; x<pieces.length; x++){
			for(int y=0; y<pieces[x].length; y++){
				if(activePiece.validMove(x, y, pieces, activePiece) && testTempMove(x, y, activePiece.getX(), activePiece.getY(), pieces, currentPlayer)){
					possibleMoves[numMoves] = new Coord(true, x, y);
					numMoves++; 
				}
			}
		}
		if(numMoves == 0)
			return null; 
		return possibleMoves;
	}
	
	/**
	 * Undoes the last move made.
	 * Moves the last moved piece back to it's previous position
	 * and it restores the piece that was at the destination (or null if there was no piece).
	 */
	public static void undoMove(){
		pieces[previousPositionOfActivePiece.getX()][previousPositionOfActivePiece.getY()] = previousPositionOfActivePiece.copy(); 
		if(previousPieceAtDestination != null)
			pieces[previousPieceAtDestination.getX()][previousPieceAtDestination.getY()] = previousPieceAtDestination.copy();
		else
			pieces[previousPieceAtDestination.getX()][previousPieceAtDestination.getY()] = null;
		return; 
	}
}
