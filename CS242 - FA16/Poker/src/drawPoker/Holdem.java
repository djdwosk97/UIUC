package drawPoker;

import java.util.Arrays;
import java.util.Scanner;

public class Holdem {
	public static void holdem() {
		int numPlayers = Controller.getNumPlayers();
		int computerPlayers = 0;
		if(numPlayers < 5)
			computerPlayers = Controller.getNumComputers(numPlayers);
		int numCards = 2; 
		int numSuits = 4; 
		Player[] players = Controller.getPlayers(numPlayers, computerPlayers, numCards);
		Player dealer = new Player("Dealer", -1, 5);
		int gameCount = 0; 
		boolean jokers = false;
		boolean playAgain = true; 
		
		while(playAgain){  
			//initialization 
			System.out.println("Now playing game " + gameCount + ".");
			Card[] deck = Deck.initializeDeck(jokers, numSuits);
			deck = Deck.shuffleDeck(deck);
			int betPool = 0;
			
			//Ante
			betPool = Controller.takeAndPrintBets(players, betPool, 0, dealer);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//Distribute hands
			int deckPos = Controller.initializeHands(deck, players, numCards);
			deckPos = drawDealerHand(deck, deckPos, dealer);
			
			//print player hands
			System.out.println("Player hands:");
			System.out.println(PrintCard.printHands(players));
			
			//pre-flop bets
			betPool = Controller.takeAndPrintBets(players, betPool, 1, dealer);
			if(Controller.gameOver(players, betPool)){
				gameCount++;
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//First flop bets
			System.out.println(PrintCard.printPartialHand(dealer, 3));
			betPool = Controller.takeAndPrintBets(players, betPool, 2, dealer);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//second flop and bets
			System.out.println(PrintCard.printPartialHand(dealer, 4));
			betPool = Controller.takeAndPrintBets(players, betPool, 3, dealer);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//third flop 
			System.out.println(PrintCard.printPartialHand(dealer, 5));
			betPool = Controller.takeAndPrintBets(players, betPool, 4, dealer);
			if(Controller.gameOver(players, betPool)){
				gameCount++; 
				playAgain = Controller.playAgain();
				continue; 
			}
			
			//get winner and prepare for next round
			getAndUpdateWinner(players, dealer, betPool);	
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
	 * @param deck
	 * @param deckPos
	 * @param dealer
	 * @return the dealer's hand
	 */
	public static int drawDealerHand(Card[] deck, int deckPos, Player dealer){
		//burn 1 card
		dealer.setCard(deck[deckPos+1], 0);
		dealer.setCard(deck[deckPos+2], 1);
		dealer.setCard(deck[deckPos+3], 2);
		//burn 1 card
		dealer.setCard(deck[deckPos+5], 3);
		//burn 1 card
		dealer.setCard(deck[deckPos+7], 4);
		
		return (deckPos+=8);
	}
	
	/**
	 * At least one player must not have forfeited if this is called.
	 * @param players
	 * @param dealer
	 * @param betPool
	 * @return the number of winners
	 */
	public static int getAndUpdateWinner(Player[] players, Player dealer, int betPool){
		if(players[0].getCard(0) == null)
			return 0;

		int numWinners = 0; 
		Player[] currWinners = getWinningPlayers(players, dealer);
		for(int i=0; i<currWinners.length; i++){
			if(currWinners[i] == null)
				break;
			numWinners ++;
		}
		
		for(int i=0; i < numWinners; i++){
			currWinners[i].setScore(currWinners[i].getScore() + 1);
			currWinners[i].setChips(currWinners[i].getChips() + betPool);
			System.out.println(currWinners[i].getName() + " won " + betPool + " chips with a " + PrintCard.printHighestSet(currWinners[i]) + " on this hand: " + PrintCard.printHand(currWinners[i]));
		}
		return numWinners;
	}
	
	/**
	 * 
	 * @param players
	 * @param dealer
	 * @return a null terminated array of winning players
	 */
	public static Player[] getWinningPlayers(Player[] players, Player dealer){
		setHighestSet(players, dealer);
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
			
			int equal = compareHands(players[i], currWinners[0], dealer);
			if(equal == 0){ //equal
				currWinners[numWinners] = players[i];
				numWinners++;
			} else if(equal == 1){ //players[i]'s hand is higher
				currWinners[0] = players[i]; 
				numWinners = 1; 
			}
		}
		return currWinners; 
	}
	
	/**
	 * @param players
	 * @param currWinners
	 * @return 0 if the hands are equal, 1 if players hand is greater, -1 if the currWinner hand is greater
	 */
	public static int compareHands(Player player, Player currWinner, Player dealer){
		if(player.getHighestSet() > currWinner.getHighestSet())
			return 1;
		if(player.getHighestSet() == currWinner.getHighestSet()){
			int[] sets = Controller.createSetsArray(getDealerPlayer(player, dealer).getHand());
			sets[0] = Controller.checkHighCard(player.getHand());
			int[] winnerSets = Controller.createSetsArray(getDealerPlayer(currWinner, dealer).getHand());
			winnerSets[0] = Controller.checkHighCard(currWinner.getHand());
			for(int i = sets.length - 1; i >= 0; i--){
				if(sets[i] > winnerSets[i])
					return 1;
				if(winnerSets[i] > sets[i])
					return -1; 
			}
		}
		
		//at this point, all sets are equivalent, compare hands
		if(Controller.checkOnePair(player.getHand()) > Controller.checkOnePair(currWinner.getHand()))
			return 1;
		else if(Controller.checkOnePair(player.getHand()) < Controller.checkOnePair(currWinner.getHand()))
			return -1;
		
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
	
	/**
	 * @param player
	 * @param dealer
	 * @return an aggregated player and dealer hand
	 */
	public static Player getDealerPlayer(Player player, Player dealer){
		Player dealerPlayer = new Player("dealerPlayer", -2, 7);
		for(int j=0; j < player.getHand().length; j++){
			dealerPlayer.setCard(player.getCard(j), j);
		}
		for(int j=0; j< dealer.getHand().length; j++){
			dealerPlayer.setCard(dealer.getCard(j), j+2);
		}
		return dealerPlayer;
	}
	
	/**
	 * Set the highest set for all players to the highest set that they have
	 * Helper functions will return -1 if the set does not exist, otherwise, it will return the highest card of the set.
	 * @param players
	 */
	public static void setHighestSet(Player[] players, Player dealer){
		for(int i = 0; i < players.length; i++){
			//create a fake hand combining player's 2 cards + dealer's 5 cards
			Player dealerPlayer = getDealerPlayer(players[i], dealer);
			
			//create sets array
			int[] sets = Controller.createSetsArray(dealerPlayer.getHand());
			sets[0] = Controller.checkHighCard(players[i].getHand());
			int[] dealerSets = Controller.createSetsArray(dealer.getHand());
			int[] playerSets = Controller.createSetsArray(players[i].getHand());
			
			for(int j=sets.length - 1; j >= -1; j--){
				if((sets[j] != -1 && sets[j] != dealerSets[j]) || (playerSets[j] != -1)){
					players[i].setHighestSet(j);
					players[i].setHighCard(sets[j]);
					break;
				}
			}
		}
		return;
	}
	
}
