package pieces;
import java.awt.Color;

/**
 * A grasshopper can move any number of spaces along a rank, file, or diagonal (like a queen),
 * however it doesn't matter if the path between the current position and end position is blocked. 
 * A grasshopper will move directly to the destination position. 
 * If there is an opponent's piece there it can capture it, if there is a friendly piece there, then it is an invalid move. 
 * TODO: A grasshopper can NOT capture, it instead swaps the piece at the destination with itself. You can not swap with a friendly piece. 
 * Lastly, a grasshopper can only move if the first square on the path has ANY player's piece on it. 
 */
public class Grasshopper extends Piece {
	
	public Grasshopper(Color color, int startX, int startY){
		setColor(color);
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(false);
		setLetter("G");
		setNeverMoved(false);
		if(color == Color.WHITE)
			setImage("src/images/WhiteGrasshopper.png");
		else
			setImage("src/images/BlackGrasshopper.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new Grasshopper(this.getColor(), this.getX(), this.getY());
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
		if(destX >= pieces.length || destY >= pieces[0].length)
			return false;
		int deltaX = destX - this.getX(); 
		int deltaY = destY - this.getY(); 
		if(deltaX == 0 && deltaY == 0) //start = destination
			return false; 
		if(Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1)//can't move to an adjacent space
			return false; 
		if(pieces[destX][destY] != null && pieces[destX][destY].getColor() == this.getColor()) //friendly piece at destination
			return false;
		
		return checkPath(destX, destY, deltaX, deltaY, pieces); 
	}
	
	/**
	 * 
	 * @param destX
	 * @param destY
	 * @param deltaX
	 * @param deltaY
	 * @param pieces
	 * @return True if there is a piece directly in front of the grasshopper to jumpover.
	 */
	public boolean checkPath(int destX, int destY, int deltaX, int deltaY, Piece[][] pieces){
		if(Math.abs(deltaX) == Math.abs(deltaY)) {//bishop movement
			if(pieces[this.getX() + deltaX/Math.abs(deltaX)][this.getY() + deltaY/Math.abs(deltaY)] == null) //deltaX/Math.abs(deltaX) = 1 or -1 
				return false; 
			return true; 
		}
		
		if(deltaX == 0 || deltaY == 0) { // rook movement
			if(deltaX == 0){ // don't divide by 0
				if(pieces[this.getX()][this.getY() + deltaY/Math.abs(deltaY)] == null)
					return false;
			}
			if(deltaY == 0){
				if(pieces[this.getX() + deltaX/Math.abs(deltaX)][this.getY()] == null) 
					return false;
			}
			return true;
		}
			
		return false; 
		
	}
}
