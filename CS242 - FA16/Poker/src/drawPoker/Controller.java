package drawPoker;
import java.util.Arrays;
import java.util.Scanner; 

public class Controller {
	
	public static void main(String[] args) {	
		int gameMode;
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.println("Welcome to Java Poker.\n" +
					"What game would you like to play?\n" +
					"Press 0 for Texas Hold'em.\n" +
					"Press 1 for Five Card Draw?");
			if(reader.hasNextInt()){
				gameMode = reader.nextInt();
				if(gameMode == 0){
					Holdem.holdem();
					break;
				} else if(gameMode == 1){
					DrawPoker.drawPoker();					
					break;
				} else {
					System.out.println("That is not a valid input.");
				}
			} else { 
				System.out.println("The game number must be a number.");
			}
		}
		System.out.println("Thank You for playing Java Poker.");
	}
	
	public static boolean playAgain() {
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.println("Would you like to play again? [Y/N]");
			String input = reader.nextLine();
			input = input.toUpperCase();
			if(input.equals("Y")) 
				return true; 
			else if(input.equals("N"))
				return false;
			else 
				System.out.println("That is not a valud input.");	
		}
	}

	/**
	 * Set the highest set for all players to the highest set that they have
	 * Helper functions will return -1 if the set does not exist, otherwise, it will return the highest card of the set.
	 * @param players
	 */
	public static void setHighestSet(Player[] players){
		for(int i = 0; i < players.length; i++){
			int[] sets = createSetsArray(players[i].getHand());
			
			for(int j=sets.length - 1; j >= -1; j--){
				if(sets[j] != -1){
					players[i].setHighestSet(j);
					players[i].setHighCard(sets[j]);
					break;
				}
			}
		}
		return;
	}
	
	public static int[] buildSortedValueArray(Card[] hand){
		int[] valArr = new int[hand.length];
		for(int i=0; i<hand.length; i++){
			valArr[i] = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
		}
		Arrays.sort(valArr);
		return valArr;
	}
	
	public static int[] buildSuitsArray(Card[] hand){
		int[] suitArr = {0, 0, 0, 0};
		for(int i=0; i<hand.length; i++)
			if(hand[i].getSuit() != -1)
				suitArr[hand[i].getSuit()]++;
		return suitArr;
	}
	public static int getMaxSuit(int[] suitArr){
		int maxSuit = 0;
		for(int i=0; i < suitArr.length; i++){
			if(suitArr[i] > suitArr[maxSuit])
				maxSuit = i;
		}
		return maxSuit;
	}

	/**
	 * @param player
	 * @return an array of ints (of length 10) containing the highest value in each set 
	 */
	public static int[] createSetsArray(Card[] hand){
		int[] sets = {
						Controller.checkHighCard(hand), 
						Controller.checkOnePair(hand), 
						Controller.checkTwoPair(hand), 
						Controller.checkThreeKind(hand),
						Controller.checkStraight(hand), 
						Controller.checkFlush(hand), 
						Controller.checkFullHouse(hand), 
						Controller.checkFourKind(hand),
						Controller.checkStraightFlush(hand), 
						Controller.checkRoyalFlush(hand)
					};
		return sets; 
	}

	public static int checkRoyalFlush(Card[] hand) {
		int straightFlush = checkStraightFlush(hand);
		if(straightFlush >= 13) { //highest card is ace or joker
			return straightFlush;
		}
		return -1; 
	}

	public static int checkStraightFlush(Card[] hand) {
		int flushExists = checkFlush(hand);
		int straightExists = checkStraight(hand);
		if(flushExists != -1 && straightExists != -1)
			return flushExists;
		return -1; 
	}

	public static int checkFourKind(Card[] hand) {
		int highestCard = -1; 
		for(int i = 0; i < hand.length; i++){
			for(int j = i+1; j < hand.length; j++){
				for(int k = j+1; k < hand.length; k++){
					for(int l = k+1; l < hand.length; l++){
						if(hand[i].getValue() == hand[j].getValue() && hand[i].getValue() == hand[k].getValue() &&
								hand[i].getValue() == hand[l].getValue())
							highestCard = Math.max(hand[i].getValue(), hand[i].getAceHighValue()); 
					}
				}
			}
		}
		return highestCard;
	}

	public static int checkFullHouse(Card[] hand) {
		int twoPair = checkTwoPair(hand);
		int threeKind = checkThreeKind(hand);
		if(threeKind != -1 && twoPair != -1){
			return threeKind;
		}
		return -1;
	}

	public static int checkFlush(Card[] hand) {
		int[] suitArr = new int[hand.length];
		int[] valueArr = new int[hand.length];
		for(int i = 0; i < hand.length; i++){
			if(hand[i] != null){
				suitArr[i] = hand[i].getSuit();
				valueArr[i] = hand[i].getValue();
			}
		}
		Arrays.sort(suitArr);
		Arrays.sort(valueArr);
		
		int count = 1; 
		int highCard = -1; 
		for(int i = 0; i < suitArr.length - 1; i++){
			count++;
			if(suitArr[i+1] != (suitArr[i]))
				count = 1; 
			if(count >= 5)
				highCard = valueArr[i+1];
		}
		
		return highCard;
	}

	public static int checkStraight(Card[] hand) {
		int low = checkLowHighStraight(hand, false);
		int high = checkLowHighStraight(hand, true);
		return Math.max(low, high);
	}
	
	public static int checkLowHighStraight(Card[] hand, boolean high){
		int[] valueArr = new int[hand.length];
		for(int i = 0; i < hand.length; i++){
			if(high)
				valueArr[i] = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
			else 
				valueArr[i] = hand[i].getValue();
		}
		Arrays.sort(valueArr);
		int count = 1; 
		int highCard = -1;
		for(int i = 0; i < valueArr.length - 1; i++){
			count++; 
			if(valueArr[i+1] != (valueArr[i] + 1))
				count = 1; 
			if(count >= 5)
				highCard = valueArr[i+1];
		}
		
		return highCard;
	}

	public static int checkThreeKind(Card[] hand) {
		int highestCard = -1; 
		for(int i = 0; i < hand.length; i++){
			for(int j = i+1; j < hand.length; j++){
				for(int k = j+1; k < hand.length; k++){
					if(hand[i].getValue() == hand[j].getValue() && hand[i].getValue() == hand[k].getValue())
						highestCard = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
				}
			}
		}
		return highestCard;
	}

	public static int checkTwoPair(Card[] hand) {
		int highestCard = -1;
		int secondPair = -1; 
		int[] valueArr = new int[hand.length];
		for(int i = 0; i < hand.length; i++){
			valueArr[i] = hand[i].getValue();
		}
		Arrays.sort(valueArr);
		for(int i = 0; i < valueArr.length - 1; i++){
			if(valueArr[i+1] == valueArr[i]){
				int cardVal = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
				if(cardVal > highestCard){
					secondPair = highestCard; 
					highestCard = cardVal; 
				} else { 
					secondPair = cardVal;
				}
				i++;
			}
				
		}
		
		if(secondPair == -1)
			return -1; 
		return highestCard;
	}

	public static int checkOnePair(Card[] hand) {
		int highestCard = -1; 
		for(int i = 0; i < hand.length; i++){
			for(int j = i+1; j < hand.length; j++){
				if(hand[i].getValue() == hand[j].getValue()){
					int cardVal = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
					if(cardVal > highestCard)
						highestCard = cardVal;
				}
			}
		}
		return highestCard;
	}

	public static int checkHighCard(Card[] hand) {
		int highestCard = -1;
		for(int i = 0; i < hand.length; i++){
			int cardVal = Math.max(hand[i].getValue(), hand[i].getAceHighValue());
			if(cardVal > highestCard)
				highestCard = cardVal;
		}
		return highestCard;
	}

	/**
	 * Reset all players for the next round
	 * @param players
	 */
	public static void resetForNextRound(Player[] players){
		for(int i = 0; i < players.length; i++){
			players[i].setFold(false);
			players[i].setCurrentBet(0);
			players[i].setHighCard(-1);
			players[i].setHighestSet(-1);
			for(int j = 0; j < players[i].getHand().length; j++){
				Card[] hand = players[i].getHand();
				hand[j] = null;
			}
		}
	}
	
	/**
	 * @param players
	 * @return true if there is <= 1 remaining player, else false
	 */
	public static boolean gameOver(Player[] players, int betPool) {
		int activePlayers = getActivePlayers(players);
		if(activePlayers == 0){
			System.out.println("All players have forfeited and no one wins this round.");
		} else if(activePlayers == 1) {
			for(int i=0; i<players.length; i++){
				if(!players[i].isFold()){
					players[i].setScore(players[i].getScore() + 1);
					players[i].setChips(players[i].getChips() + betPool);
					System.out.println(players[i].getName() + " won " + betPool + " chips by default.");
					break;
				}
			}
		} else 
			return false;
		resetForNextRound(players);
		return true;
	}

	/**
	 * @param players
	 * @return the number of active players
	 */
	public static int getActivePlayers(Player[] players) {
		int activePlayers = 0; 
		for(int i=0; i<players.length; i++)
			if(!players[i].isFold())
				activePlayers += 1;
		return activePlayers;
	}


	/**
	 * Gives each player a hand
	 * Give #(cardsToDeal) cards -- one at a time
	 */
	public static int initializeHands(Card[] deck, Player[] players, int cardsToDeal) {
		int deckPos = 0; 
		for(int cardsDealt=0; cardsDealt < cardsToDeal; cardsDealt ++){
			for(int j=0; j<players.length; j++){
				players[j].setCard(deck[deckPos], cardsDealt);
				deckPos++;
			}
		}
		return deckPos;
	}
	
	/**
	 * BetTypes: Ante = 0, pre-flop = 1, first-flop = 2, second-flop = 3, third-flop = 4
	 * Gets and prints all player's bets
	 * @param players
	 * @param betPool
	 * @param betType
	 * @return the size of the bet pool
	 */
	public static int takeAndPrintBets(Player[] players, int betPool, int betType, Player dealer){
		if(betType == 0){
			System.out.println("Ante betting round:");
		} else if(betType == 1){
			System.out.println("pre-flop betting round:");
		} else if(betType == 2){
			System.out.println("first-flop betting round:");
		} else if(betType == 3){
			System.out.println("second-flop betting round:");
		} else if(betType == 4){
			System.out.println("third-flop betting round:");
		} else if(betType == 5){
			System.out.println("Post-hand betting");
		}
		betPool = getBets(players, betPool, dealer);
		System.out.println("Player bets:");
		System.out.println(PrintCard.printBets(players));
		
		return betPool;
	}

	/**
	 * @param an array of players players
	 * @param the size of the current betPool
	 * @return the size of the new betPool
	 */
	public static int getBets(Player[] players, int betPool, Player dealer) {
		int currentMaxBet = 0; 
		do {
			for(int i=0; i<players.length; i++){
				if(getActivePlayers(players) == 1)
					return betPool;
				if(players[i].isFold())
					continue;
				while(true){
					int bet = 0; 
					if(players[i].getAI() == true){
						bet = Computer.getBet(players[i], currentMaxBet, dealer);
					} else { 
						System.out.println("The current max bet is: " + currentMaxBet + ".");
						System.out.println("How much would you like to bet, " + players[i].getName() + "?");
						int betToCall = Math.max(currentMaxBet - players[i].getCurrentBet(), 0);
						System.out.println("Bet " + betToCall + " to call.\nEnter -1 to fold.");
						Scanner reader = new Scanner(System.in);
						if(reader.hasNextInt())
							bet = reader.nextInt();
						else {
							System.out.println("The bet must be a number.");
							continue; 
						}
					}	
					
					if(bet == -1){
						players[i].setFold(true);
						break;
					} else if(bet <= players[i].getChips()){
						if(bet + players[i].getCurrentBet() >= currentMaxBet){
							currentMaxBet = bet + players[i].getCurrentBet();
							players[i].setCurrentBet(players[i].getCurrentBet() + bet);
							players[i].setChips(players[i].getChips() - bet);
							betPool += bet;  
							break;
						} else {
							System.out.println("Your bet must at least meet the previous highest bet (" + currentMaxBet + ").");
						}
					} else {
						System.out.println("You only have " + players[i].getChips() + "remaining.");
						continue;
					}
				}
			}
		} while(betsNotEqual(players));
		return betPool;
	}
	
	
	/**
	 * 
	 * @param players
	 * @return true if all active bets are not equal, else false
	 */
	public static boolean betsNotEqual(Player[] players){
		int firstBet = -1;
		for(int i=0; i < players.length; i++){
			if(players[i].isFold())
				continue; 
			if(firstBet == -1)
				firstBet = players[i].getCurrentBet();
			if(players[i].getCurrentBet() != firstBet)
				return true;
		}
		return false; 
	}


	/**
	 * @return the number of players
	 */
	public static int getNumPlayers(){
		int numPlayers = 0;
		int maxPlayers = 5; 
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.println("How many human players will there be? (between 1 and 5)");
			if(reader.hasNextInt()){
				numPlayers = reader.nextInt();
				if(numPlayers >= 1 && numPlayers <= maxPlayers)
					return numPlayers;
				else {
					System.out.println("The number of players must be between 1 and 5.");
					continue;
				}
			} else { 
				System.out.println("The number of players must be a number.");
				continue; 
			}
		}
	}
	
	
	/**
	 * @param numPlayers
	 * @return an initialized player array of length numPlayers
	 */
	public static Player[] getPlayers(int numPlayers, int computerPlayers, int numCards){
		Player[] players = new Player[numPlayers+computerPlayers];
		for(int i=0; i<numPlayers; i++){
			while(true){
				Scanner reader = new Scanner(System.in);
				System.out.println("Please enter a player name.");
				String name = reader.nextLine();
				if(name.length() > 0) {
					players[i] = new Player(name, i, numCards);
					break;
				} else {
					System.out.println("A name must be at least one character long.");
					continue;
				}
			}
		}
		for(int i=0; i<computerPlayers; i++){
			String name = "Computer" + i;
			players[numPlayers + i] = new Computer(name, numPlayers + i, numCards);
		}
		return players;
	}
	
	/**
	 * @return the number of players
	 */
	public static int getNumComputers(int humanPlayers){
		int numPlayers = 0;
		int maxPlayers = 5-humanPlayers;
		int minPlayers = Math.max(2-humanPlayers, 0);
		
		while(true){
			Scanner reader = new Scanner(System.in);
			System.out.println("How many Computer players will there be? (between " + minPlayers + " and " + maxPlayers +")");
			if(reader.hasNextInt()){
				numPlayers = reader.nextInt();
				if(numPlayers >= minPlayers && numPlayers <= maxPlayers)
					return numPlayers;
				else {
					System.out.println("The number of players must be between " + minPlayers + " and " + maxPlayers + ".");
					continue;
				}
			} else { 
				System.out.println("The number of players must be a number.");
				continue; 
			}
		}
	}
}
