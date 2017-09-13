package pieces;
import java.awt.Color;

public class King extends Piece {
	public King(Color color, int startX, int startY){
		setColor(color); 
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(true);
		setLetter("K");
		if(color == Color.WHITE)
			setImage("src/images/WhiteKing.png");
		else
			setImage("src/images/BlackKing.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new King(this.getColor(), this.getX(), this.getY());
		return piece; 
	}
	
	/**
	 * Check if the move is valid. 
	 * Check if the destination has an opponent's piece.
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
		
		if(Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1) { //move 1 in any direction
			if(pieces[destX][destY] != null) { //check destination 
				return (pieces[this.getX()][this.getY()].getColor() != pieces[destX][destY].getColor()); //true if opponents piece 
			}
			return true; 
		}
		return false; 
	}
	
}
