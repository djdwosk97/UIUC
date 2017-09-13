package testbench;

import org.junit.Test;
import static org.junit.Assert.*;
import drawPoker.Controller;
import drawPoker.Deck;
import drawPoker.Card;
import drawPoker.DrawPoker;
import drawPoker.Player;
import drawPoker.PrintCard;
import drawPoker.Computer;

public class testbenchAI {
	
	@Test
	public void cardsToComplete(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int[] sets = Computer.numCardsToCompleteSet(players[0]);
		
		assertTrue(sets[9] == 4);
		assertTrue(sets[8] == 3);
		assertTrue(sets[7] == 2);
		assertTrue(sets[6] == 1);
		assertTrue(sets[5] == 3);
		assertTrue(sets[4] == 3);
		assertTrue(sets[3] == 1);
		assertTrue(sets[2] == 0);
		assertTrue(sets[1] == 0);
		assertTrue(sets[0] == 0);
	}

	@Test
	public void cardsToHighCard(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToHighCard(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	
	@Test
	public void zeroCardsToOnePair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToOnePair(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardToOnePair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 7);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToOnePair(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	
	@Test
	public void zeroCardToTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 4);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToTwoPair(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardToTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 3);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 4);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToTwoPair(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardToTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToTwoPair(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	
	@Test
	public void zeroCardToThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToThreeKind(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardToThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 0);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToThreeKind(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardToThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToThreeKind(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	
	@Test
	public void zeroCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 3);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void oneInternalCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 7);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoInternalCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void twoHighCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 10);
		deck[1] = new Card(0, 0, 1, 11);
		deck[2] = new Card(1, 1, 2, 13);
		deck[3] = new Card(2, 1, 3, 13);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void threeCardsToStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 1, 1);
		deck[2] = new Card(1, 1, 2, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraight(players[0].getHand());
		assertTrue(cardsNeeded == 3);
	}
	
	@Test
	public void zeroCardsToFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 3, 1);
		deck[2] = new Card(1, 1, 3, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFlush(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 1);
		deck[2] = new Card(1, 1, 3, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardsToFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 0, 1);
		deck[2] = new Card(1, 1, 3, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFlush(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void threeCardsToFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 0, 1);
		deck[2] = new Card(1, 1, 1, 7);
		deck[3] = new Card(2, 1, 3, 8);
		deck[4] = new Card(1, 1, 3, 13);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFlush(players[0].getHand());
		assertTrue(cardsNeeded == 3);
	}
	
	@Test
	public void zeroCardsToFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 0);
		deck[2] = new Card(1, 1, 3, 1);
		deck[3] = new Card(2, 1, 3, 1);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFullHouse(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 2);
		deck[2] = new Card(1, 1, 3, 1);
		deck[3] = new Card(2, 1, 3, 1);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFullHouse(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardsToFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 4);
		deck[1] = new Card(0, 0, 3, 0);
		deck[2] = new Card(1, 1, 3, 3);
		deck[3] = new Card(2, 1, 3, 1);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFullHouse(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void threeCardsToFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 2);
		deck[2] = new Card(1, 1, 3, 4);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFullHouse(players[0].getHand());
		assertTrue(cardsNeeded == 3);
	}
	
	@Test
	public void zeroCardsToFourKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 0);
		deck[2] = new Card(1, 1, 3, 0);
		deck[3] = new Card(2, 1, 3, 0);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFourKind(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToFourKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 1);
		deck[2] = new Card(1, 1, 3, 0);
		deck[3] = new Card(2, 1, 3, 0);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFourKind(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardsToFourKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 0);
		deck[2] = new Card(1, 1, 3, 3);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFourKind(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void threeCardsToFourKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 0, 0);
		deck[1] = new Card(0, 0, 3, 5);
		deck[2] = new Card(1, 1, 3, 4);
		deck[3] = new Card(2, 1, 3, 2);
		deck[4] = new Card(1, 1, 3, 1);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToFourKind(players[0].getHand());
		assertTrue(cardsNeeded == 3);
	}
	
	@Test
	public void zeroCardsToStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 3, 1);
		deck[2] = new Card(1, 1, 3, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 3, 2);
		deck[2] = new Card(1, 1, 3, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void oneCardsToStraightFlush2(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 2, 1);
		deck[2] = new Card(1, 1, 3, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void oneCardsToStraightFlush3(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 2, 2);
		deck[2] = new Card(1, 1, 3, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardsToStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 1, 4);
		deck[2] = new Card(1, 1, 2, 2);
		deck[3] = new Card(2, 1, 3, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	@Test
	public void threeCardsToStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 1, 4);
		deck[2] = new Card(1, 1, 2, 2);
		deck[3] = new Card(2, 1, 0, 3);
		deck[4] = new Card(1, 1, 3, 4);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 3);
	}
	@Test
	public void fourCardsToStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 1, 4);
		deck[2] = new Card(1, 1, 2, 2);
		deck[3] = new Card(2, 1, 0, 3);
		deck[4] = new Card(1, 1, 3, 8);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 4);
	}
	@Test
	public void fourCardsToStraightFlush2(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 0);
		deck[1] = new Card(0, 0, 1, 13);
		deck[2] = new Card(1, 1, 2, 5);
		deck[3] = new Card(2, 1, 0, 3);
		deck[4] = new Card(1, 1, 3, 8);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToStraightFlush(players[0].getHand());
		assertTrue(cardsNeeded == 4);
	}
	
	@Test
	public void zeroCardsToRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 12);
		deck[2] = new Card(1, 1, 3, 11);
		deck[3] = new Card(2, 1, 3, 10);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToRoyalFlush(players[0].getHand());
		assertTrue(cardsNeeded == 0);
	}
	@Test
	public void oneCardsToRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 3, 11);
		deck[3] = new Card(2, 1, 3, 10);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToRoyalFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void oneCardsToRoyalFlush2(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 2, 8);
		deck[2] = new Card(1, 1, 3, 11);
		deck[3] = new Card(2, 1, 3, 10);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToRoyalFlush(players[0].getHand());
		assertTrue(cardsNeeded == 1);
	}
	@Test
	public void twoCardsToRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 10);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		int cardsNeeded = Computer.cardsToRoyalFlush(players[0].getHand());
		assertTrue(cardsNeeded == 2);
	}
	
//	@Test
//	public void soreHandByValue(){
//		Player[] players = new Player[1];
//		players[0] = new Computer("Daniel", 0, 5);
//		Card[] deck = new Card[5];
//		deck[0] = new Card(0, 0, 3, 12);
//		deck[1] = new Card(0, 0, 3, 8);
//		deck[2] = new Card(1, 1, 2, 11);
//		deck[3] = new Card(2, 1, 3, 10);
//		deck[4] = new Card(1, 1, 3, 9);
//		Controller.initializeHands(deck, players, 5);
//		Controller.sortByValHigh(players[0].getHand());
//		for(int i=0, j=8; i<5; i++, j++){
//			int currCard = Math.max(players[0].getCard(i).getValue(), players[0].getCard(i).getAceHighValue());
//			assertTrue(currCard == j);
//		}
//	}
	
	@Test
	public void unusedCardsInRoyalFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 7);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInRoyalFlush(players[0].getHand(), 3).equals("2,3,4"));
	}
	
	@Test
	public void unusedCardsInStraightFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 8);
		deck[1] = new Card(0, 0, 3, 7);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 5);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInStraightFlush(players[0].getHand(), 3).equals("3"));
	}
	@Test
	public void unusedCardsInFourKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 13);
		deck[2] = new Card(1, 1, 2, 12);
		deck[3] = new Card(2, 1, 3, 7);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInFourKind(players[0].getHand(), 3).equals("3,4,5"));
	}
	@Test
	public void unusedCardsInFullHouse(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 3, 7);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInFullHouse(players[0].getHand(), 3).equals("2,4,5"));
	}
	@Test
	public void unusedCardsInFlush(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 13);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 11);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 9);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInFlush(players[0].getHand(), 2).equals("3,4"));
	}
	@Test
	public void unusedCardsInStraight(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 13);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInStraight(players[0].getHand(), 1).equals("3,4"));
	}
	@Test
	public void twoUnusedCardsInThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInThreeKind(players[0].getHand(), 2).equals("1,2,4,5"));
	}
	@Test
	public void oneUnusedCardsInThreeKind(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 9);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInThreeKind(players[0].getHand(), 1).equals("3,4,5"));
	}
	@Test
	public void twoUnusedCardsInTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInTwoPair(players[0].getHand(), 2).equals("2,4,5"));
	}
	@Test
	public void oneUnusedCardsInTwoPair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 9);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInTwoPair(players[0].getHand(), 1).equals("4,5"));
	}
	@Test
	public void oneUnusedCardsInOnePair(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 9);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 2, 0);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		assertTrue(Computer.unusedCardsInTwoPair(players[0].getHand(), 1).equals("1,2,4,5"));
	}
	
	@Test
	public void getGarbageCards(){
		Player[] players = new Player[1];
		players[0] = new Computer("Daniel", 0, 5);
		Card[] deck = new Card[5];
		deck[0] = new Card(0, 0, 3, 8);
		deck[1] = new Card(0, 0, 3, 8);
		deck[2] = new Card(1, 1, 3, 1);
		deck[3] = new Card(2, 1, 2, 7);
		deck[4] = new Card(1, 1, 3, 6);
		Controller.initializeHands(deck, players, 5);
		String garbageCards = Computer.getGarbageCards((Computer)players[0]);
		System.out.println(garbageCards);
		assertTrue(garbageCards.equals("4"));
	}
}
