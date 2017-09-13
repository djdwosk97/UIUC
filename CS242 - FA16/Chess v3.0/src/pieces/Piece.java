package pieces;
import java.awt.Color;

public class Piece {
	private boolean neverMoved;
	private Color color; 
	private int x; 
	private int y; 
	private boolean onBoard;
	private boolean isKing;
	private String letter; 
	private String image; 
	private char unicode; 
	
	
	//should never be called
	public Piece copy() {
		return null;
	}
	
	//should never be called
	public boolean validMove(int destX, int destY, Piece[][] pieces, Piece activePiece) {
		return false; 
	}


	/**
	 * Getters and Setters
	 */
	public Color getColor(){
		return this.color; 
	}
	public void setColor(Color color){
		this.color = color;
	}
	public String getLetter(){
		return this.letter; 
	}
	public void setLetter(String newLetter){
		this.letter = newLetter;
	}
	public String getImage(){
		return this.image; 
	}
	public void setImage(String newImage){
		this.image = newImage; 
	}
	public int getX(){
		return this.x;
	}
	public void setX(int newX){
		this.x = newX; 
	}
	public int getY(){
		return this.y;
	}
	public void setY(int newY){
		this.y = newY; 
	}
	public boolean getNeverMoved(){
		return this.neverMoved; 
	}
	public void setNeverMoved(boolean newNeverMoved){
		this.neverMoved = newNeverMoved;
	}
	public boolean getOnBoard(){
		return this.onBoard; 
	}
	public void setOnBoard(boolean newOnBoard){
		this.onBoard = newOnBoard; 
	}
	public boolean getIsKing(){
		return this.isKing; 
	}
	public void setIsKing(boolean newIsKing){
		this.isKing = newIsKing;
	}

	public char getUnicode() {
		return unicode;
	}

	public void setUnicode(char unicode) {
		this.unicode = unicode;
	}
}
