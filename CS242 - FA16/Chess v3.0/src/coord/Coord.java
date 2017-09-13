package coord;

public class Coord {
	private boolean validDestination; 
	private int x; 
	private int y;
	
	
	/**
	 * Constructor 
	 * @param validDestination
	 * @param x
	 * @param y
	 */
	public Coord(boolean validDestination, int x, int y){
		this.setValidDestination(validDestination); 
		this.setX(x); 
		this.setY(y); 
	}


	
	/**
	 * Getters and Setters
	 */
	public boolean getValidDestination() {
		return validDestination;
	}
	public void setValidDestination(boolean validDestination) {
		this.validDestination = validDestination;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
	
}
