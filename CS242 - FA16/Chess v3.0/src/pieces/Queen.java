package pieces;

import java.awt.Color;

public class Queen extends Piece {

	public Queen(Color color, int startX, int startY){
		setColor(color);
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(false);
		setLetter("Q");
		setNeverMoved(false);
		if(color == Color.WHITE)
			setImage("src/images/WhiteQueen.png");
		else
			setImage("src/images/BlackQueen.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new Queen(this.getColor(), this.getX(), this.getY());
		return piece; 
	}
	
	/**
	 * Check if the move is valid 
	 * @param destX
	 * @param destY
	 * @return true if it's a valid move for the piece AND 
	 * 			if the path is clear. Else return false.
	 */
	public boolean validMove(int destX, int destY, Piece[][] pieces, Piece activePiece){	
		Rook internalRook = new Rook(this.getColor(), this.getX(), this.getY()); 
		Bishop internalBishop = new Bishop(this.getColor(), this.getX(), this.getY()); 
		int deltaX = destX - this.getX(); 
		int deltaY = destY - this.getY(); 
		if(deltaX == 0 && deltaY == 0) //start = destination
			return false; 
		if(((Math.abs(deltaX) == Math.abs(deltaY)) && (internalBishop.checkPath(destX, destY, pieces))) || 
				((deltaX == 0 || deltaY == 0) && (internalRook.checkPath(destX, destY, pieces)))){
			return true;
		}
		
		return false; 
		
	}
}
