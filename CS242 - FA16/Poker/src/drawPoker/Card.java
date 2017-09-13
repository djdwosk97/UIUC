package drawPoker;

public class Card {
	private int owner; 
	private int color; 
	private int suit; 
	private int value;
	private int aceHighValue;
	
	public Card(int owner, int color, int suit, int value){
		setOwner(owner);
		setColor(color);
		setSuit(suit);
		setValue(value);
		if(value == 0)
			setAceHighValue(13);
		else 
			setAceHighValue(-1);
	}
	
	//GETTERS AND SETTERS
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public int getSuit() {
		return suit;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public int getAceHighValue() {
		return aceHighValue;
	}

	public void setAceHighValue(int aceHighValue) {
		this.aceHighValue = aceHighValue;
	}
}
