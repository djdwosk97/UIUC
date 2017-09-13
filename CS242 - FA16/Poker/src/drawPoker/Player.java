package drawPoker;

public class Player {
	private boolean fold;
	private int chips; 
	private int currentBet; 
	private int playerNum;
	private int score;
	private String name; 
	private Card[] hand;
	private int highestSet;
	private int highCard;
	private boolean AI;
	
	public Player(String name, int playerNum, int numCards){
		setName(name);
		setScore(0);
		setPlayerNum(playerNum);
		this.hand = new Card[numCards];
		setChips(1000);
		setCurrentBet(0);
		setFold(false);
	}
	
	public Player(String name, int playerNum, int numCards, boolean AI){
		setName(name);
		setScore(0);
		setPlayerNum(playerNum);
		this.hand = new Card[numCards];
		setChips(50);
		setCurrentBet(0);
		setFold(false);
		setAI(true);
	}
	
	//GETTERS AND SETTERS
	public int getPlayerNum() {
		return playerNum;
	}
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Card[] getHand(){
		return hand; 
	}
	public Card getCard(int cardNum) {
		return hand[cardNum];
	}
	public void setCard(Card card, int cardNum) {
		this.hand[cardNum] = card;
	}

	public int getChips() {
		return chips;
	}
	public void setChips(int chips) {
		this.chips = chips;
	}

	public int getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	public boolean isFold() {
		return fold;
	}

	public void setFold(boolean fold) {
		this.fold = fold;
	}

	public int getHighestSet() {
		return highestSet;
	}

	public void setHighestSet(int j) {
		this.highestSet = j;
	}

	public int getHighCard() {
		return highCard;
	}

	public void setHighCard(int highCard) {
		this.highCard = highCard;
	}

	public boolean getAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	} 
}
