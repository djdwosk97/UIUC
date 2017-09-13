package drawPoker;

public class PrintCard {

	public static String printHands(Player[] players){
		String hands = "";
		for(int i=0; i<players.length; i++){
			hands += printHand(players[i]);
		}
		return hands; 
	}
	
	public static String printHand(Player player){
		String hand = ""; 
		hand = player.getName() + ": [";
		for(int j=0; j < player.getHand().length; j++){
			String printString = PrintCard.printCard(player.getCard(j));
			hand += printString;
			if(j < player.getHand().length - 1)
				hand += ", ";
		}
		hand += "]\n";
		return hand;
	}
	
	public static String printPartialHand(Player player, int numCards){
		String hand = ""; 
		hand = player.getName() + ": [";
		for(int j=0; (j<player.getHand().length) && (j < numCards); j++){
			String printString = PrintCard.printCard(player.getCard(j));
			hand += printString;
			if((j < player.getHand().length - 1) && (j < numCards - 1))
				hand += ", ";
		}
		hand += "]\n";
		return hand;
	}
	
	public static String printCard(Card card){
		//print: suit, value 
		String suit; 
		switch (card.getSuit()){
			case 0:
				suit = "Hearts";
				break;
			case 1: 
				suit = "Clubs";
				break;
			case 2: 
				suit = "Diamonds";
				break;
			case 3: 
				suit = "Spades";
				break;
			case 4: 
				suit = "WILD";
				break;
			default: 
				suit = "This will never happen";
				break;
		}
		
		String value;
		switch (card.getValue()){
			case 0:
				value = "Ace";
				break;
			case 10: 
				value = "Jack";
				break;
			case 11: 
				value = "Queen";
				break;
			case 12: 
				value = "King";
				break;
			case 13: 
				value = "Ace";
				break;
			case 14:
				value = "JOKER";
				break;
			default: 
				value = Integer.toString(card.getValue() + 1);
				break;
		}
		String printString = "(" + suit + ", " + value + ")";
		return printString;
	}
	
	public static String printBets(Player[] players){
		String betString = "";
		for(int i=0; i<players.length; i++){
			if(players[i].isFold())
				//System.out.println(players[i].getName() + ": (Folded)");
				betString += players[i].getName() + ": (Folded)\n";
			else 
				//System.out.println(players[i].getName() + ": (" + players[i].getCurrentBet() + ")");
				betString += players[i].getName() + ": (" + players[i].getCurrentBet() + ")\n";
		}
		return betString;
	}
	
	public static String printHighestSet(Player player){
		String set = "";
		switch(player.getHighestSet()){
			case 0:
				set = "High Card";
				break;
			case 1: 
				set = "One Pair";
				break;
			case 2: 
				set = "Two Pair";
				break;
			case 3: 
				set = "Three of a kind";
				break;
			case 4: 
				set = "Straight";
				break; 
			case 5: 
				set = "Flush";
				break;
			case 6: 
				set = "Full House";
				break;
			case 7: 
				set = "Four of a Kind";
				break; 
			case 8: 
				set = "Straight Flush";
				break;
			case 9: 
				set = "Royal Flush";
				break;
			default: 
				set = "This will never happen";
				break;
		}
		return set;
	}
}
