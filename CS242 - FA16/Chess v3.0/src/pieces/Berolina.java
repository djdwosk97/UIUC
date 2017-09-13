package pieces;

import java.awt.Color;

/**
 * A Berolina moves complement to the pawn.
 * A Berolina moves diagonally forward 1 space (2 on the first move), 
 * but captures head on.
 */
public class Berolina extends Piece{
	
	public Berolina(Color color, int startX, int startY){
		setColor(color);
		setOnBoard(true);
		setX(startX);
		setY(startY);
		setIsKing(false);
		setLetter("Be");
		setNeverMoved(true);
		if(color == Color.WHITE)
			setImage("src/images/WhiteBerolina.png");
		else
			setImage("src/images/BlackBerolina.png");
	}
	
	/**
	 * Copy constructor
	 * @return a copy of the piece
	 */
	public Piece copy(){
		Piece piece = new Berolina(this.getColor(), this.getX(), this.getY());
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
		
		//black increments, white decrements 
		if((deltaY > 0 && activePiece.getColor() == Color.WHITE) || (deltaY < 0 && activePiece.getColor() == Color.BLACK))
			return false; 
		
		if((Math.abs(deltaX) == Math.abs(deltaY)) && ((Math.abs(deltaX) == 1) || (Math.abs(deltaX) == 2 && pieces[this.getX()][this.getY()].getNeverMoved()))){
			for(int i=1; i <= Math.abs(deltaY); i++){
				if(pieces[this.getX() + i*(deltaX/Math.abs(deltaX))][this.getY() + i*(deltaY/Math.abs(deltaY))] != null)
					return false; 
			}
			return true; 
		}
		
		if((Math.abs(deltaX) == 0) && (Math.abs(deltaY) == 1)){
			if(pieces[destX][destY] != null){
				return (pieces[this.getX()][this.getY()].getColor() != pieces[destX][destY].getColor()); //true if opponents piece
			}
		}
		return false;  
	}
}
