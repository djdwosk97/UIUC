package pieces;

import java.awt.Color;

public class Rook extends Piece {
	/**
	 * Constructor
	 * @param color
	 * @param startX
	 * @param startY
	 */
	public Rook(Color color, int startX, int startY){
		setColor(color);
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(false);
		setLetter("R");
		if(color == Color.WHITE)
			setImage("src/images/WhiteRook.png");
		else
			setImage("src/images/BlackRook.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new Rook(this.getColor(), this.getX(), this.getY());
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
		int deltaX = destX - this.getX(); 
		int deltaY = destY - this.getY(); 
		if(deltaX == 0 && deltaY == 0) //start = destination 
			return false; 
		if(deltaX == 0 || deltaY == 0) {
			if(checkPath(destX, destY, pieces)){
				return true;
			}
		}
		return false; 
		
	}
	
	/**
	 * Check the path, but NOT the destination position.
	 * Only be called from validMove, so deltaX OR deltaY = 0. 
	 * @param deltaX
	 * @param deltaY
	 * @return true if the path is clear, false if not
	 */
	public boolean checkPath(int destX, int destY, Piece[][] pieces){
		int deltaX = destX - this.getX(); 
		int deltaY = destY - this.getY(); 
		if(deltaX == 0){ // don't divide by 0
			for(int i=1; i<Math.abs(deltaY); i++){
				if(pieces[this.getX()][this.getY() + i*(deltaY/Math.abs(deltaY))] != null)
					return false;
			}
		}
		if(deltaY == 0){
			for(int i=1; i<Math.abs(deltaX); i++){
				if(pieces[this.getX() + i*(deltaX/Math.abs(deltaX))][this.getY()] != null) 
					return false;
			}
		}
		
		if(pieces[destX][destY] != null) { //check destination 
			return (pieces[this.getX()][this.getY()].getColor() != pieces[destX][destY].getColor()); //true if opponents piece 
		}
		return true; 
	}
	
	
	
}