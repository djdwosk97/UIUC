package pieces;
import java.awt.Color;

public class Knight extends Piece {
	
	public Knight(Color color, int startX, int startY){
		setColor(color);
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(false);
		setLetter("Kn");
		setNeverMoved(false);
		if(color == Color.WHITE)
			setImage("src/images/WhiteKnight.png");
		else
			setImage("src/images/BlackKnight.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new Knight(this.getColor(), this.getX(), this.getY());
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
		
		if((Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1) || (Math.abs(deltaY) == 2 && Math.abs(deltaX) == 1)){
			if(pieces[destX][destY] != null) { //check destination 
				return (pieces[this.getX()][this.getY()].getColor() != pieces[destX][destY].getColor()); //true if opponents piece 
			}
			return true; 
		}
		return false; 
	}
}
