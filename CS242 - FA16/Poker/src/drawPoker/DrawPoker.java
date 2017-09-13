package drawPoker;
import java.util.Arrays;
import java.util.Scanner; 

public class DrawPoker {
	
	public static void drawPoker() {
		int numPlayers = Controller.getNumPlayers();
		int computerPlayers = 0;
		if(numPlayers < 5)
			computerPlayers = Controller.getNumComputers(numPlayers);
		int numCards = 5; 
		int numSuits = 4; 
		boolean jokers = false; 
		Player[] players = Controller.getPlayers(numPlayers, computerPlayers, numCards);
		int gameCount = 0; 
		boolean playAgain = true; 
		
		while(playAgain){  
			System.out.println("Now playing game " + gameCount + ".");
			Card[] deck = Deck.initializeDeck(jokers, numSuits);
			deck = Deck.shuffleDeck(deck);
			
			//ante betting round
			int betPool = Controller.takeAndPrintBets(players, 0, 0, null);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
				
			//initialize and print hands
			int deckPos = Controller.initializeHands(deck, players, numCards);
			System.out.println("Player hands:");
			System.out.println(PrintCard.printHands(players));
			
			//second betting round
			betPool = Controller.takeAndPrintBets(players, betPool, 5, null);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//draw cards and print updated hands
			deckPos = drawCards(deck, players, deckPos);
			System.out.println("Player hands:");
			System.out.println(PrintCard.printHands(players));
			
			getAndUpdateWinner(players, betPool);
			Controller.resetForNextRound(players); 
			gameCount++; 
			playAgain = Controller.playAgain();
		}
		
		String summary = "Games Played: " + gameCount + "\n"; 
		for(int i=0; i < players.length; i++){
			summary += players[i].getName() + " won " + players[i].getScore() + " games and ended with " + players[i].getChips() + " chips.\n";
		}
		System.out.println(summary);
		
	}

	/**
	 * Set the highest set for all players to the highest set that they have
	 * Helper functions will return -1 if the set does not exist, otherwise, it will return the highest card of the set.
	 * @param players
	 */
	public static void setHighestSet(Player[] players){
		for(int i = 0; i < players.length; i++){
			Card[] hand = players[i].getHand();
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

	/**
	 * 
	 * @param deck
	 * @param players
	 * @param deckPos
	 * @return the updated position of the top of the deck (deckPos + number of cards drawn)
	 */
	public static int drawCards(Card[] deck, Player[] players, int deckPos){
		for(int i=0; i<players.length; i++){
			if(players[i].isFold())
				continue; 
			while(true){
				String input; 
				if(players[i].getAI() == true){
					input = Computer.getGarbageCards((Computer)players[i]);
				} else {
					System.out.println("There are " + (deck.length - deckPos) + " cards remaining in the deck.");
					System.out.println(players[i].getName() + ", what cards would you like to swap? Enter 0 to swap nothing. (ex: 1,2 would swap the first and second card)");
					Scanner reader = new Scanner(System.in);
					input = reader.nextLine();
				}
				if(input.length() % 2 != 1)
					System.out.println("That input is not valid.");
				else if(input.equals("0"))
					break; 
				else {
					for(int j=0; j < input.length(); j+=2){
						int curr = Character.getNumericValue(input.charAt(j));
						players[i].setCard(deck[deckPos], curr-1);
						deckPos++;
					}
					break;
				}
			}
		}
		return deckPos;
	}
	
	/**
	 * At least one player must not have forfeited if this is called.
	 * @param players
	 * @param dealer
	 * @param betPool
	 * @return the number of winners
	 */
	public static int getAndUpdateWinner(Player[] players, int betPool){
		if(players[0].getCard(0) == null)
			return 0;
		
		int numWinners = 0; 
		Player[] currWinners = getWinningPlayers(players); 
		for(int i=0; i<currWinners.length; i++){
			if(currWinners[i] == null)
				break;
			numWinners++; 
		}
			
		for(int i=0; i < numWinners; i++){
			currWinners[i].setScore(currWinners[i].getScore() + 1);
			currWinners[i].setChips(currWinners[i].getChips() + betPool);
			System.out.println(currWinners[i].getName() + " won " + betPool + " chips with a " + PrintCard.printHighestSet(currWinners[i]) + " on this hand: " + PrintCard.printHand(currWinners[i]));
		}
		return numWinners;
	}
	
	/**
	 * @param players
	 * @return an array of winning players that is terminated by a null (the first null entry indicates the end of winners)
	 */
	public static Player[] getWinningPlayers(Player[] players){
		setHighestSet(players);
		Player[] currWinners = new Player[players.length]; 
		int numWinners = 0;
		
		for(int i = 0; i < players.length; i++){
			if(players[i].isFold())
				continue;
			else if(currWinners[0] == null){
				currWinners[0] = players[i];
				numWinners++; 
				continue;
			}
			
			int equal = compareHands(players[i], currWinners[0]);
			if(equal == 0){ //equal
				currWinners[numWinners] = players[i];
				numWinners++;
			} else if(equal == 1){ //players[i]'s hand is higher
				currWinners[0] = players[i]; 
				numWinners = 1; 
			}
		}
		currWinners[numWinners] = null; 
		return currWinners;
	}
	
	/**
	 * @param players
	 * @param currWinners
	 * @return 0 if the hands are equal, 1 if players hand is greater, -1 if the currWinner hand is greater
	 */
	public static int compareHands(Player player, Player currWinner){
		if(player.getHighestSet() > currWinner.getHighestSet())
			return 1;
		if(player.getHighestSet() == currWinner.getHighestSet()){
			int[] sets = Controller.createSetsArray(player.getHand());
			int[] winnerSets = Controller.createSetsArray(currWinner.getHand());
			for(int i = sets.length - 1; i >= 0; i--){
				if(sets[i] > winnerSets[i])
					return 1;
				if(winnerSets[i] > sets[i])
					return -1; 
			}
		}
		
		//at this point, all sets are equivalent, compare cards
		int[] playerHand = new int[player.getHand().length];
		int[] currWinnerHand = new int[currWinner.getHand().length];
		for(int i=0; i<player.getHand().length; i++){
			playerHand[i] = Math.max(player.getCard(i).getValue(), player.getCard(i).getAceHighValue());
			currWinnerHand[i] = Math.max(currWinner.getCard(i).getValue(), currWinner.getCard(i).getAceHighValue());
		}
		Arrays.sort(playerHand);
		Arrays.sort(currWinnerHand);
		for(int i = playerHand.length - 1; i >= 0; i--){
			if(playerHand[i] > currWinnerHand[i])
				return 1;
			else if(currWinnerHand[i] > playerHand[i])
				return -1; 
		}
		return 0; 
	}
}
