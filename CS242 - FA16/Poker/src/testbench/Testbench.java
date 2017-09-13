package testbench;

import org.junit.Test;
import static org.junit.Assert.*;
import drawPoker.Controller;
import drawPoker.Deck;
import drawPoker.Card;
import drawPoker.DrawPoker;
import drawPoker.Player;
import drawPoker.PrintCard;

public class Testbench {
	
	@Test
	public void testInitializeDeckJokers(){
		Card[] deck = Deck.initializeDeck(true, 4);
		assertTrue(deck.length == 54);
		for(int i=0; i<deck.length-2; i++){
			assertTrue(deck[i].getValue() == i%13);
		}
		assertTrue(deck[52].getValue() == 14);
		assertTrue(deck[53].getValue() == 14);
	}
	
	@Test
	public void testInitializeDeckNoJokers(){
		Card[] deck = Deck.initializeDeck(false, 4);
		assertTrue(deck.length == 52);
		for(int i=0; i<deck.length; i++){
			assertTrue(deck[i].getValue() == i%13);
		}
	}
	
	@Test
	public void testShuffleDeck(){
		Card[] deck = Deck.initializeDeck(true, 4);
		assertTrue(deck.length == 54);
		deck = Deck.shuffleDeck(deck);
		int[] numSuits = {0, 0, 0, 0, 0};
		
		for(int i=0; i<deck.length; i++){
			numSuits[deck[i].getSuit()] ++; 
		}
		assertTrue(numSuits[0] <= 13);
		assertTrue(numSuits[1] <= 13);
		assertTrue(numSuits[2] <= 13);
		assertTrue(numSuits[3] <= 13);
		assertTrue(numSuits[4] <= 2);
	}

	@Test
	public void testAceHighCard(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkHighCard(players[0].getHand());
		assertTrue(highCard == deck[0].getAceHighValue());
	}
	
	@Test
	public void testOtherHighCard(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkHighCard(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testNoSet(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 10);
		deck[2] = new Card(1, 1, 2, 3);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		int deckPos = Controller.initializeHands(deck, players, 5);
		assertTrue(Controller.checkOnePair(players[0].getHand()) == -1);
		assertTrue(Controller.checkTwoPair(players[0].getHand()) == -1);
		assertTrue(Controller.checkThreeKind(players[0].getHand()) == -1);
		assertTrue(Controller.checkStraight(players[0].getHand()) == -1);
		assertTrue(Controller.checkFlush(players[0].getHand()) == -1);
		assertTrue(Controller.checkFullHouse(players[0].getHand()) == -1);
		assertTrue(Controller.checkFourKind(players[0].getHand()) == -1);
		assertTrue(Controller.checkStraightFlush(players[0].getHand()) == -1);
		assertTrue(Controller.checkRoyalFlush(players[0].getHand()) == -1);
	}
	
	@Test
	public void testOnePair(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkOnePair(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkTwoPair(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkThreeKind(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testStraight(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 10);
		deck[2] = new Card(1, 1, 2, 9);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 7);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkStraight(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testFlush(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 0, 10);
		deck[2] = new Card(0, 1, 0, 4);
		deck[3] = new Card(0, 1, 0, 5);
		deck[4] = new Card(0, 1, 0, 3);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkFlush(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 3);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 1, 3);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkFullHouse(players[0].getHand());
		assertTrue(highCard == deck[2].getValue());
	}
	
	@Test
	public void testFourKind(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 11);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 11);
		deck[4] = new Card(1, 1, 3, 4);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkFourKind(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 9);
		deck[1] = new Card(0, 0, 0, 8);
		deck[2] = new Card(1, 1, 0, 7);
		deck[3] = new Card(2, 1, 0, 6);
		deck[4] = new Card(1, 1, 0, 5);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkStraightFlush(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 13);
		deck[1] = new Card(0, 0, 0, 12);
		deck[2] = new Card(1, 1, 0, 11);
		deck[3] = new Card(2, 1, 0, 10);
		deck[4] = new Card(1, 1, 0, 9);
		int deckPos = Controller.initializeHands(deck, players, 5);
		int highCard = Controller.checkRoyalFlush(players[0].getHand());
		assertTrue(highCard == deck[0].getValue());
	}
	
	@Test
	public void testSetHighestSetRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 13);
		deck[1] = new Card(0, 0, 0, 12);
		deck[2] = new Card(1, 1, 0, 11);
		deck[3] = new Card(2, 1, 0, 10);
		deck[4] = new Card(1, 1, 0, 9);
		int deckPos = Controller.initializeHands(deck, players, 5);
		Controller.setHighestSet(players);
		assertTrue(players[0].getHighestSet() == 9);
		assertTrue(players[0].getHighCard() == 13);
	}
	
	@Test
	public void testSetHighestSetFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 13);
		deck[1] = new Card(0, 0, 1, 13);
		deck[2] = new Card(1, 1, 2, 13);
		deck[3] = new Card(2, 1, 0, 10);
		deck[4] = new Card(1, 1, 1, 10);
		int deckPos = Controller.initializeHands(deck, players, 5);
		Controller.setHighestSet(players);
		assertTrue(players[0].getHighestSet() == 6);
		assertTrue(players[0].getHighCard() == 13);
	}
	
	@Test
	public void testGameOver(){
		Player[] players = new Player[2];
		players[0] = new Player("Daniel", 0, 5);
		players[1] = new Player("Daniel", 1, 5);
		assertFalse(Controller.gameOver(players, 0));
		players[0].setFold(true);
		assertTrue(Controller.gameOver(players, 0));
	}

	@Test
	public void testGetAndUpdateWinner(){
		Player[] players = new Player[3];
		players[0] = new Player("testUser0", 0, 5);
		players[1] = new Player("testUser1", 1, 5);
		players[2] = new Player("testUser2", 2, 5);
		assertTrue(DrawPoker.getAndUpdateWinner(players, 0) == 0);
		Card[] deck = Deck.initializeDeck(false, 4); //top 15 cards are: Hearts(Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king), Clubs(ace, 2)
		Controller.initializeHands(deck, players, 5); //player[0]: Ace,4,7,10,king .......player[1]: 2,5,8,jack,ace ...... player[2]: 3,6,9,queen,2
		assertTrue(DrawPoker.getAndUpdateWinner(players, 0) == 1);
	}
	
	@Test
	public void twoWinners(){
		Player[] players = new Player[3];
		players[0] = new Player("testUser0", 0, 5);
		players[1] = new Player("testUser1", 1, 5);
		players[2] = new Player("testUser2", 2, 5);
		assertTrue(DrawPoker.getAndUpdateWinner(players, 0) == 0);
		Card[] deck = new Card[6];
		deck[0] = new Card(0, 0, 0, 13);
		deck[1] = new Card(0, 0, 1, 13);
		deck[2] = new Card(1, 1, 2, 13);
		deck[3] = new Card(2, 1, 0, 10);
		deck[4] = new Card(1, 1, 1, 10);
		deck[5] = new Card(1, 1, 1, 9);
		for(int i=0; i<5; i++){
			players[0].setCard(deck[i], i);
			players[1].setCard(deck[i], i);
			players[2].setCard(deck[i+1], i);
		}
		assertTrue(DrawPoker.getAndUpdateWinner(players, 0) == 2);
		int numWinners = 0; 
		Player[] winners = DrawPoker.getWinningPlayers(players);
		for(int i=0; i<winners.length; i++){
			if(winners[i] == null)
				break;
			numWinners++; 
		}
		assertTrue(numWinners == 2);
	}
	
	@Test
	public void getTwoWinners(){
		Player[] players = new Player[3];
		players[0] = new Player("testUser0", 0, 5);
		players[1] = new Player("testUser1", 1, 5);
		players[2] = new Player("testUser2", 2, 5);
		assertTrue(DrawPoker.getAndUpdateWinner(players, 0) == 0);
		Card[] deck = new Card[6];
		deck[0] = new Card(0, 0, 0, 13);
		deck[1] = new Card(0, 0, 1, 13);
		deck[2] = new Card(1, 1, 2, 13);
		deck[3] = new Card(2, 1, 0, 10);
		deck[4] = new Card(1, 1, 1, 10);
		deck[5] = new Card(1, 1, 1, 9);
		for(int i=0; i<5; i++){
			players[0].setCard(deck[i], i);
			players[1].setCard(deck[i], i);
			players[2].setCard(deck[i+1], i);
		}
		int numWinners = 0; 
		Player[] winners = DrawPoker.getWinningPlayers(players);
		for(int i=0; i<winners.length; i++){
			if(winners[i] == null)
				break;
			numWinners++; 
		}
		assertTrue(numWinners == 2);
	}
	
	@Test
	public void testInitializeHand(){
		Card[] deck = new Card[5]; 
		Player[] players = new Player[1]; 
		players[0] = new Player("Daniel", 0, 5);
		for(int i=0; i<5; i++)
			deck[i] = new Card(0, 0, 0, i);
		Controller.initializeHands(deck, players, 5);
		Card[] hand = players[0].getHand();
		for(int i=0; i<hand.length; i++)
			assertTrue(hand[i] == deck[i]);
	}
}
