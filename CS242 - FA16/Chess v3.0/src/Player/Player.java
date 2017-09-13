package Player;
import java.awt.Color;
import pieces.Piece;

public class Player {
	private Color color; 
	private int move = 0; //move #
	private boolean inCheck;
	private boolean inCheckmate; 
	private String name; 
	private Piece king;
	private int score = 0;
	
	
	/**
	 * Player constructor. 
	 * Assign a color to a player.
	 * @param color
	 */
	public Player(Color color, String name){
		this.color = color; 
		this.inCheck = false;
		this.inCheckmate = false;
		this.move = 0; 
		this.name = name;
	}
	
	
	/**
	 * Getters and setters
	 */
	public Color getColor(){
		return this.color; 
	}
	public void setColor(Color color){
		this.color = color; 
	}
	public Piece getKing(){
		return this.king; 
	}
	public void setKing(Piece king){
		this.king = king;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoveNumber(){
		return this.move; 
	}
	public void setMoveNumber(int move){
		this.move = move;
	}
	public boolean getInCheck(){
		return this.inCheck;
	}
	public void setInCheck(boolean inCheck){
		this.inCheck = inCheck;
	}
	public boolean getInCheckmate(){
		return this.inCheckmate;
	}
	public void setInCheckMate(boolean inCheckmate){
		this.inCheckmate = inCheckmate;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
