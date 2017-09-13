package drawPoker;

//SUITS: HEARTS = 0, SPADES = 1, DIAMONDS = 2, CLUBS = 3, joker = 4
//COLORS: RED = 0, BLACK = 1, joker = 2
//Card: Ace (1), 2, 3, 4, 5, 6, 7, 8, 9, 10, jack (11), queen (12), king (13)
//Players: deck = 0, board = 1, players >= 2

public class Deck {
	private static Card[] deck; 
	
	public static Card[] initializeDeck(boolean jokers, int numSuits){
		int numCards = 0; 
		if(!jokers){
			deck = new Card[52];
		} else {
			deck = new Card[54];
			deck[52] = new Card(0, 2, 4, 14);
			deck[53] = new Card(0, 2, 4, 14);
		}
		
		for(int suit = 0; suit < numSuits; suit++){
			for(int card = 0; card < 13; card++){
				deck[numCards] = new Card(0, suit%2, suit, card);
				numCards++; 
			}
		}
		return deck;
	}
	
	/**
	 * Shuffles a deck of cards
	 * @param deck
	 * @return
	 */
	public static Card[] shuffleDeck(Card[] deck){
		for(int i=0; i<100000; i++){
			for(int j=0; j<deck.length; j++){
				int rand = (int)(deck.length *Math.random());
				Card tempCard = deck[j];
				deck[j] = deck[rand];
				deck[rand] = tempCard;
			}
		}
		return deck;
	}
	
}
