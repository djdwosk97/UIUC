package testbench;

import org.junit.Test;
import static org.junit.Assert.*;
import drawPoker.Controller;
import drawPoker.Deck;
import drawPoker.Card;
import drawPoker.Player;
import drawPoker.PrintCard;
import drawPoker.DrawPoker;
import drawPoker.Holdem; 

public class TestbenchHoldem {
	
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
	public void testAceHighCardInHand(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		deck[5] = new Card(2, 1, 3, 7);
		deck[6] = new Card(1, 1, 3, 8);
		deck[7] = new Card(2, 1, 3, 9);
		deck[8] = new Card(1, 1, 3, 10);
		deck[9] = new Card(2, 1, 3, 11);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		int highCard = Controller.checkHighCard(players[0].getHand());
		assertTrue(highCard == deck[0].getAceHighValue());
	}
	
	@Test
	public void testAceHighCardInFlop(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 1);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		deck[5] = new Card(2, 1, 3, 5);
		deck[6] = new Card(1, 1, 3, 6);
		deck[7] = new Card(2, 1, 3, 7);
		deck[8] = new Card(1, 1, 3, 8);
		deck[9] = new Card(2, 1, 3, 9);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		int highCard = Controller.checkHighCard(players[0].getHand());
		assertTrue(highCard == deck[1].getAceHighValue());
	}
	
	@Test
	public void testOtherHighCardInFlop(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 1);
		deck[1] = new Card(0, 0, 1, 2);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		deck[5] = new Card(2, 1, 3, 5);
		deck[6] = new Card(1, 1, 3, 6);
		deck[7] = new Card(2, 1, 3, 7);
		deck[8] = new Card(1, 1, 3, 8);
		deck[9] = new Card(2, 1, 3, 9);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		int highCard = Controller.checkHighCard(players[0].getHand());
		assertTrue(highCard == deck[1].getValue());
	}
	
	@Test
	public void testSetHighestSetRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 9);
		deck[1] = new Card(0, 0, 1, 2);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 10);
		deck[5] = new Card(2, 1, 3, 11);
		deck[6] = new Card(1, 1, 3, 1);
		deck[7] = new Card(2, 1, 3, 12);
		deck[8] = new Card(1, 1, 3, 2);
		deck[9] = new Card(2, 1, 3, 13);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		Holdem.setHighestSet(players, dealer);
		assertTrue(players[0].getHighestSet() == 9);
		assertTrue(players[0].getHighCard() == 13);
	}
	
	@Test
	public void testSetHighestSetRoyalFlushFlopOnly(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 3);
		deck[1] = new Card(0, 0, 1, 2);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 9);
		deck[4] = new Card(1, 1, 3, 10);
		deck[5] = new Card(2, 1, 3, 11);
		deck[6] = new Card(1, 1, 3, 1);
		deck[7] = new Card(2, 1, 3, 12);
		deck[8] = new Card(1, 1, 3, 4);
		deck[9] = new Card(2, 1, 3, 13);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		Holdem.setHighestSet(players, dealer);
		assertFalse(players[0].getHighestSet() == 9);
		assertFalse(players[0].getHighCard() == 13);
	}
	
	@Test
	public void testSetHighestSetHighCard(){
		Player[] players = new Player[1];
		players[0] = new Player("Daniel", 0, 2);
		Player dealer = new Player("Dealer", -1, 5);
		Card[] deck = new Card[10];
		deck[0] = new Card(0, 0, 0, 12);
		deck[1] = new Card(0, 0, 1, 2);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 9);
		deck[4] = new Card(1, 1, 0, 5);
		deck[5] = new Card(2, 1, 1, 11);
		deck[6] = new Card(1, 1, 2, 1);
		deck[7] = new Card(2, 1, 3, 3);
		deck[8] = new Card(1, 1, 0, 4);
		deck[9] = new Card(2, 1, 1, 13);
		int deckPos = Controller.initializeHands(deck, players, 2);
		deckPos = Holdem.drawDealerHand(deck, deckPos, dealer);
		
		Holdem.setHighestSet(players, dealer);
		assertTrue(players[0].getHighestSet() == 0);
		assertTrue(players[0].getHighCard() == 12);
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
}
