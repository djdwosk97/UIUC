package drawPoker;

import java.util.Arrays;

public class Computer extends Player {

	public Computer(String name, int playerNum, int numCards) {
		super(name, playerNum, numCards);
		setAI(true);
	}


//BETTING: 	
	/**
	 * Returns the computer's bet
	 * @param computer
	 * @param currentBet
	 * @return the computer's bet. -1 to fold, 0 to check, x>0 to raise/bet.
	 */
	public static int getBet(Player computer, int currentBet, Player dealer){
		if(computer.getCard(0) == null)
			return Math.min(currentBet, computer.getChips());
		
		Player[] playerArr = {computer};
		int[] cardsToComplete; 
		int[] sets = Controller.createSetsArray(computer.getHand());
		if(dealer != null){
			Holdem.setHighestSet(playerArr, dealer);
			Player dealerPlayer = Holdem.getDealerPlayer(computer, dealer);
			cardsToComplete = numCardsToCompleteSet(dealerPlayer);
		} else {	
			Controller.setHighestSet(playerArr);
			cardsToComplete = numCardsToCompleteSet(computer);
		}
		if(shouldFold(sets, cardsToComplete, currentBet, computer.getCurrentBet(), computer.getChips()))
			return -1;
		
		int finalBet = getFinalBet(sets, cardsToComplete, computer, currentBet);
		return finalBet;
	}
	
	/**
	 * Fold if the max bet < bet required to call
	 * @param sets
	 * @param cardsToComplete
	 * @param currentBet
	 * @param computerBet
	 * @param remainingChips
	 * @return true if the computer should fold, else false
	 */
	public static boolean shouldFold(int[] sets, int[] cardsToComplete, int currentBet, int computerBet, int remainingChips){
		int maxBet = getMaxBet(sets, cardsToComplete);
		if(computerBet >= currentBet)
			return false;
		if(maxBet < currentBet)
			return true; 
		
		return false;
	}
	
	/**
	 * Return the optimal bet. 
	 * @param sets
	 * @param cardsToComplete
	 * @param computer
	 * @param currentBet
	 * @return the final bet after considering the current bet and the available chips
	 */
	public static int getFinalBet(int[] sets, int[] cardsToComplete, Player computer, int currentBet){
		int maxBet = getMaxBet(sets, cardsToComplete);
		int highestSet = computer.getHighestSet();
		if(currentBet == 0 && highestSet <=1)
			return 0;
		if(currentBet == computer.getCurrentBet())
			return 0;
		
		int finalBet = Math.min(maxBet, (int)(currentBet*1.2 + 1));
		if(highestSet >= 1){
			finalBet = Math.min(maxBet, (int)(currentBet*1.2*(highestSet+1) + 1));
		}
		return Math.min(finalBet, computer.getChips());
	}
	
	/**
	 * Updated algorithm for determining max bet value
	 * @param sets
	 * @param cardsToComplete
	 * @return the maximum amount will to be bet
	 */
	public static int getMaxBet(int[] sets, int[] cardsToComplete){
		int maxBet = 0;
		for(int i=sets.length-1; i>=0; i--){
			int myBet = (int)(Math.pow(i+1, (3-cardsToComplete[i])));
			if(sets[i] > 0)
				myBet += 2*sets[i];
			
			if(myBet > maxBet)
				maxBet = myBet; 
		}
		return maxBet; 
	}
	
	/**
	 * Create an int array with the value of how many cards are required to complete each set. (0 -> hand.length)
	 * @param computer
	 * @return the number of cards needed to complete each set
	 */
	public static int[] numCardsToCompleteSet(Player computer){
		Card[] hand = computer.getHand();
		int[] sets = {
				cardsToHighCard(hand), 
				cardsToOnePair(hand),
				cardsToTwoPair(hand),
				cardsToThreeKind(hand),
				cardsToStraight(hand),
				cardsToFlush(hand),
				cardsToFullHouse(hand),
				cardsToFourKind(hand),
				cardsToStraightFlush(hand),
				cardsToRoyalFlush(hand)
		};
		return sets; 
	}
	
	public static int cardsToHighCard(Card[] hand){
		return 0; 
	}
	public static int cardsToOnePair(Card[] hand){
		if(Controller.checkOnePair(hand) != -1)
			return 0; 
		return 1; 
	}
	public static int cardsToTwoPair(Card[] hand){
		if(Controller.checkTwoPair(hand) != -1) 
			return 0; 
		if(Controller.checkOnePair(hand) != -1)
			return 1; 
		return 2; 
	}
	public static int cardsToThreeKind(Card[] hand){
		if(Controller.checkThreeKind(hand) != -1)
			return 0; 
		if(Controller.checkOnePair(hand) != -1)
			return 1; 
		return 2; 
	}
	public static int cardsToStraight(Card[] hand){
		if(Controller.checkStraight(hand) != -1) 
			return 0; 
		
		int[] highValueArr = new int[hand.length];
		int[] lowValueArr = new int[hand.length];
		for(int i=0; i<hand.length; i++){
			highValueArr[i] = Math.max(hand[i].getAceHighValue(), hand[i].getValue());
			lowValueArr[i] = hand[i].getValue();
		}
		Arrays.sort(highValueArr);
		Arrays.sort(lowValueArr);
		
		return Math.min(distanceToStraight(highValueArr), distanceToStraight(lowValueArr));
	}
	
	/**
	 * Create a temp array where temp[0] = valueArr[i] and temp[0]->temp[length] is a straight starting with temp[0]
	 * Create a temp array where temp[length] = valueArr[i] and temp[0]->temp[length] is a straight starting with temp[0]
	 * Compare the temp array to the valueArr to find the temp (straight) array it is most similar to. 
	 * @param valueArr
	 * @return the number of cards needed to get a straight
	 */
	public static int distanceToStraight(int[] valueArr){
		int mostSimilarHand = 1;
		for(int i=0; i<valueArr.length; i++){
			int[] tempArr = new int[valueArr.length];
			tempArr[0] = valueArr[i];
			for(int j=1; j<tempArr.length; j++){
				tempArr[j] = tempArr[j-1] + 1; 
			}
			mostSimilarHand = Math.max(mostSimilarHand, compareArrays(valueArr, tempArr));
		}
		
		for(int i=valueArr.length-1; i>=0; i--){
			int[] tempArr = new int[valueArr.length];
			tempArr[tempArr.length-1] = valueArr[i];
			for(int j=tempArr.length-2; j>=0; j--){
				tempArr[j] = tempArr[j+1] - 1;
			}
			mostSimilarHand = Math.max(mostSimilarHand, compareArrays(valueArr, tempArr));
		}
		
		return 5-mostSimilarHand; 
	}
	/**
	 * @param valueArr
	 * @param tempArr
	 * @return the number of similarities between two int arrays
	 */
	public static int compareArrays(int[] valueArr, int[] tempArr){
		int count = 0; 	
		for(int i=0; i<tempArr.length; i++){
			for(int j=0; j<valueArr.length; j++){
				if(tempArr[i] == valueArr[j] && valueArr[j] != -1){
					count++;
					break;
				}
			}
		}
		return count; 
	}
	
	public static int cardsToFlush(Card[] hand){
		if(Controller.checkFlush(hand) != -1)
			return 0; 
		int[] suitArr = Controller.buildSuitsArray(hand);
		int maxSuit = Controller.getMaxSuit(suitArr);
		return 5 - suitArr[maxSuit];
	}
	public static int cardsToFullHouse(Card[] hand){
		if(Controller.checkFullHouse(hand) != -1)
			return 0; 
		if(Controller.checkThreeKind(hand) != -1 || Controller.checkTwoPair(hand) != -1)
			return 1;
		if(Controller.checkOnePair(hand) != -1)
			return 2; 
		return 3; 
	}
	public static int cardsToFourKind(Card[] hand){
		if(Controller.checkFourKind(hand) != -1)
			return 0; 
		if(Controller.checkThreeKind(hand) != -1)
			return 1; 
		if(Controller.checkOnePair(hand) != -1)
			return 2; 
		return 3; 
	}
	public static int cardsToStraightFlush(Card[] hand){ 
		if(Controller.checkStraightFlush(hand) != -1)
			return 0; 

		int[] suit = Controller.buildSuitsArray(hand);
		int minCardsNeeded = 5; 
		for(int i=0; i<suit.length;i++){
			Card[] tempHand = new Card[5];
			int k=0;
			for(int j=0; j<hand.length; j++){
				if(hand[j].getSuit() == i){
					tempHand[k] = hand[j];
					k++;
				}
			}
			for(int j=k; j<tempHand.length; j++){//insert dummy card(s)
				tempHand[j] = new Card(0, 0, -1, -1);
			}
			int maxCardsTemp = Math.max(cardsToStraight(tempHand), cardsToFlush(tempHand));
			minCardsNeeded = Math.min(minCardsNeeded, maxCardsTemp);
		}
		return minCardsNeeded;
	}
	
	public static int cardsToRoyalFlush(Card[] hand){
		if(Controller.checkRoyalFlush(hand) != -1)
			return 0; 

		int[] valueArr = Controller.buildSortedValueArray(hand);
		int[] royalStraight = {9, 10, 11, 12, 13};
		int cardsToRoyalStraight = 5 - compareArrays(valueArr, royalStraight);
		
		int count = 0;
		Card[] tempHand = new Card[hand.length];
		for(int i=0; i<valueArr.length; i++){
			if(hand[i].getValue() >= 9 || hand[i].getAceHighValue() >= 9){
				tempHand[count] = hand[i];
				count++; 	
			}
		}
		for(int j=count; j<tempHand.length; j++){//insert dummy card(s)
			tempHand[j] = new Card(0, 0, -1, -1);
		}
		return Math.max(cardsToRoyalStraight, cardsToFlush(tempHand));
	}
	
//CARD SWAPPING:
	/**
	 * Generate a string that will be used by drawpoker's drawCards function to swap the least useful cards
	 * @param computer
	 * @param sets
	 * @param cardsToComplete
	 * @return a comma separated string with the indices of the cards that should be swapped
	 */
	public static String getGarbageCards(Computer computer){ 
		Card[] hand = computer.getHand();
		int[] sets = Controller.createSetsArray(hand);
		int[] cardsToComplete = numCardsToCompleteSet(computer);
		String[] unusedCards = {
				unusedCardsInHighCard(hand, cardsToComplete[0]),
				unusedCardsInOnePair(hand, cardsToComplete[1]),
				unusedCardsInTwoPair(hand, cardsToComplete[2]),
				unusedCardsInThreeKind(hand, cardsToComplete[3]),
				unusedCardsInStraight(hand, cardsToComplete[4]),
				unusedCardsInFlush(hand, cardsToComplete[5]),
				unusedCardsInFullHouse(hand, cardsToComplete[6]),
				unusedCardsInFourKind(hand, cardsToComplete[7]),
				unusedCardsInStraightFlush(hand, cardsToComplete[8]),
				unusedCardsInRoyalFlush(hand, cardsToComplete[9]),
		};
		
		String garbageCards = "";
		for(int i=unusedCards.length-1; i>=0; i--){
			if(i >=8 && sets[i] != -1){ //straight flush/royal flush
				return "0";
			} else if(i == 7 && sets[i] != -1){ //four kind
				int unusedCardIndex = Integer.parseInt(unusedCards[7]);
				if(Math.max(hand[unusedCardIndex].getAceHighValue(), hand[unusedCardIndex].getValue()) < 10) //swap the 5th card if it's not a face card
					return unusedCards[7];
			} else if(i >=4 && sets[i] != -1){ //Straight/Flush/Full House
				return "0";
			} else if(i >= 2 && sets[i] != -1){ //two pair/three kind
				return unusedCards[i];
			}
		}
		for(int i=unusedCards.length-1; i>=0; i--){
			if(i >= 6 && cardsToComplete[i] <= 1){ //straight flush/royal flush/four kind/full house
				garbageCards = unusedCards[i];
				break; 
			} else if(i <= 5 && cardsToComplete[i] <= 2) {//all others
				garbageCards = unusedCards[i];
				break;
			}
		}
		return garbageCards; 
	}
	public static String unusedCardsInRoyalFlush(Card[] hand, int cardsToComplete) {
		if(cardsToComplete == 0)
			return "0"; 
		else if(cardsToComplete == 5)
			return "1,2,3,4,5";

		int[] suitArr = Controller.buildSuitsArray(hand);
		int maxSuit = Controller.getMaxSuit(suitArr); 
		
		Card[] tempHand = new Card[suitArr[maxSuit]];
		for(int i=0, j=0; i < hand.length; i++){
			if(hand[i].getSuit() == maxSuit && Math.max(hand[i].getAceHighValue(), hand[i].getValue()) >= 9){
				tempHand[j] = hand[i];
				j++;
			}
		}
		
		String garbageCards = getGarbageCardsIndices(tempHand, hand);
		return garbageCards;
	}
	public static String unusedCardsInStraightFlush(Card[] hand, int cardsToComplete){
		if(cardsToComplete == 0)
			return "0"; 
		else if(cardsToComplete == 5)
			return "1,2,3,4,5";
		
		int[] suitArr = Controller.buildSuitsArray(hand);
		int maxSuit = Controller.getMaxSuit(suitArr); 
		
		Card[] tempHand = new Card[5];
		int j=0;
		for(int i=0; i < hand.length; i++){
			if(hand[i].getSuit() == maxSuit){
				tempHand[j] = hand[i];
				j++;
			}
		}
		for(int k=j; k<tempHand.length; k++) //dummy cards
			tempHand[k] = new Card(0, 0, -10, -10);
		
		String garbageCards = getGarbageCardsIndices(tempHand, hand);
		return garbageCards;
	}
	public static String unusedCardsInFourKind(Card[] hand, int cardsToComplete){	
		String garbageCards = ""; 
		if(cardsToComplete == 3){
			garbageCards = getGarbageCardsForPairs(hand, Controller.checkHighCard(hand));
		} else if(cardsToComplete == 2){
			garbageCards = getGarbageCardsForPairs(hand, Controller.checkOnePair(hand));
		} else if(cardsToComplete == 1){
			garbageCards = getGarbageCardsForPairs(hand, Controller.checkThreeKind(hand));
		} else if(cardsToComplete == 0){
			int fourKind = Controller.checkFourKind(hand);
			for(int i=0; i < hand.length; i++){
				if(Math.max(hand[i].getValue(), hand[i].getAceHighValue()) != fourKind)
					garbageCards += (i+1) + ",";
			}
		}
		
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInFullHouse(Card[] hand, int cardsToComplete){
		if(cardsToComplete == 0)
			return "0"; 
		
		String garbageCards = "";
		if(cardsToComplete == 3){ //save top two cards
			int[] valArr = Controller.buildSortedValueArray(hand);
			int secondHighCard = valArr[valArr.length-2];
			garbageCards = getGarbageCardsLowerThanHighCard(hand, secondHighCard);
		} else if(cardsToComplete == 2){//save the pair and the high card
			int onePair = Controller.checkOnePair(hand);
			int nextHighCard = getHighCardGivenPair(hand, onePair);
			garbageCards = garbageCardsLessThanHighNotEqualOther(hand, nextHighCard, onePair);
		} else if(cardsToComplete == 1){//Save three kind and highest card or save two pairs
			int threeKind = Controller.checkThreeKind(hand);
			if(threeKind > -1){ //save three kind and highest card
				int nextHighCard = getHighCardGivenPair(hand, threeKind);
				garbageCards = garbageCardsLessThanHighNotEqualOther(hand, nextHighCard, threeKind);
			} else { //save two pairs
				for(int i=0; i<hand.length-1; i++){
					if(hand[i] != hand[i+1])
						garbageCards += (i+1);
				}
			}
		}
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInFlush(Card[] hand, int cardsToComplete){
		if(cardsToComplete == 0)
			return "0";
		
		int[] suitArr = Controller.buildSuitsArray(hand);
		int maxSuit = Controller.getMaxSuit(suitArr); 
		String garbageCards = "";
		for(int i=0; i<hand.length; i++){
			if(hand[i].getSuit() != maxSuit)
				garbageCards += (i+1) + ",";
		}
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInStraight(Card[] hand, int cardsToComplete){
		if(cardsToComplete == 0)
			return "0";
	
		Card[] mostSimilarHand = getMostSimilarHand(hand);
		String garbageCards = getGarbageCardsIndicesSuitless(mostSimilarHand, hand);
		return garbageCards;
	}
	public static Card[] getMostSimilarHand(Card[] hand){
		int mostSimilarHand = 1;
		int[] valueArr = Controller.buildSortedValueArray(hand);
		int[] bestHand = valueArr; 
		for(int i=0; i<valueArr.length; i++){
			int[] tempArr = new int[valueArr.length];
			tempArr[0] = valueArr[i];
			for(int j=1; j<tempArr.length; j++){
				tempArr[j] = tempArr[j-1] + 1; 
			}
			if(compareArrays(valueArr, tempArr) > mostSimilarHand){
				mostSimilarHand = compareArrays(valueArr, tempArr);
				bestHand = tempArr; 
			}
		}
		
		for(int i=valueArr.length-1; i>=0; i--){
			int[] tempArr = new int[valueArr.length];
			tempArr[tempArr.length-1] = valueArr[i];
			for(int j=tempArr.length-2; j>=0; j--){
				tempArr[j] = tempArr[j+1] - 1;
			}
			if(compareArrays(valueArr, tempArr) > mostSimilarHand){
				mostSimilarHand = compareArrays(valueArr, tempArr);
				bestHand = tempArr; 
			}
		}
		
		Card[] finalHand = new Card[bestHand.length];
		for(int i=0; i<bestHand.length; i++){
			finalHand[i] = new Card(0, 0, 0, bestHand[i]);
		}
		
		return finalHand;
	}
	public static String unusedCardsInThreeKind(Card[] hand, int cardsToComplete){
		String garbageCards = "";
		if(cardsToComplete == 2){
			garbageCards = getGarbageCardsLowerThanHighCard(hand, Controller.checkHighCard(hand));
		} else if(cardsToComplete == 1){
			garbageCards = getGarbageCardsForPairs(hand, Controller.checkOnePair(hand));
		} else if(cardsToComplete == 0){
			int threeKind = Controller.checkThreeKind(hand);
			garbageCards = getGarbageCardsForPairs(hand, threeKind);
		}
	
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInTwoPair(Card[] hand, int cardsToComplete){
		String garbageCards = "";
		if(cardsToComplete == 2){//save two highest cards
			int[] valArr = Controller.buildSortedValueArray(hand);
			int secondHighCard = valArr[valArr.length-2];
			garbageCards = getGarbageCardsLowerThanHighCard(hand, secondHighCard);
		} else if(cardsToComplete == 1){//save one pair and highest card
			int onePair = Controller.checkOnePair(hand);
			int nextHighCard = getHighCardGivenPair(hand, onePair);
			garbageCards = garbageCardsLessThanHighNotEqualOther(hand, nextHighCard, onePair);
		} else if(cardsToComplete == 0){
			 garbageCards = getGarbageCardsForPairs(hand, Controller.checkTwoPair(hand));
		}
		
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInOnePair(Card[] hand, int cardsToComplete){
		String garbageCards = getGarbageCardsForPairs(hand, Controller.checkHighCard(hand));
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String unusedCardsInHighCard(Card[] hand, int cardsToComplete){
		int highCard = Controller.checkHighCard(hand);
		if(highCard <= 7)
			return "1,2,3,4,5";
		String garbageCards = getGarbageCardsLowerThanHighCard(hand, Controller.checkHighCard(hand));
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	
	//helper functions
	public static String garbageCardsLessThanHighNotEqualOther(Card[] hand, int highCard, int otherCard){
		String garbageCards = "";
		for(int i=0; i<hand.length; i++){
			if(Math.max(hand[i].getValue(), hand[i].getAceHighValue()) < highCard && Math.max(hand[i].getValue(), hand[i].getAceHighValue()) != otherCard)
				garbageCards += (i+1) + ",";
		}
		return garbageCards;
	}
	public static String getGarbageCardsIndices(Card[] tempHand, Card[] hand){
		boolean found = false;
		String garbageCards = ""; 
		for(int i=0; i<hand.length; i++){
			for(int j=0; j<tempHand.length; j++){
				if(hand[i] == tempHand[j]){
					found = true; 
					break;
				}
			}
			if(!found)
				garbageCards += (i+1) + ",";
			found = false;
		}
		
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String getGarbageCardsIndicesSuitless(Card[] tempHand, Card[] hand){
		boolean found = false;
		String garbageCards = ""; 
		for(int i=0; i<hand.length; i++){
			for(int j=0; j<tempHand.length; j++){
				if(hand[i].getValue() == tempHand[j].getValue() || (hand[i].getAceHighValue() == tempHand[i].getAceHighValue() && hand[i].getAceHighValue() != -1)){
					found = true; 
					break;
				}
			}
			if(!found)
				garbageCards += (i+1) + ",";
			found = false;
		}
		garbageCards = garbageCards.substring(0, Math.max(garbageCards.length()-1, 0));
		return garbageCards;
	}
	public static String getGarbageCardsForPairs(Card[] hand, int highCard){
		String garbageCards = "";
		for(int i=0; i<hand.length; i++){
			if(Math.max(hand[i].getValue(), hand[i].getAceHighValue()) != highCard)
				garbageCards += (i+1) + ",";
		}
		return garbageCards;
	}
	public static String getGarbageCardsLowerThanHighCard(Card[] hand, int highCard){
		String garbageCards = "";
		for(int i=0; i<hand.length; i++){
			if(Math.max(hand[i].getValue(), hand[i].getAceHighValue()) < highCard)
				garbageCards += (i+1) + ",";
		}
		return garbageCards;
	}
	public static int getHighCardGivenPair(Card[] hand, int pair){
		int[] valArr = Controller.buildSortedValueArray(hand);
		for(int i=valArr.length-1; i>=0; i--){
			if(valArr[i] != pair)
				return valArr[i];
		}
		return -1; // unreachable
	}
	public static int[] buildSuitsArrayForRoyal(Card[] hand){
		int[] suitArr = {0, 0, 0, 0};
		for(int i=0; i<hand.length; i++){
			if(Math.max(hand[i].getAceHighValue(), hand[i].getValue()) >= 9){
				suitArr[hand[i].getSuit()]++;
			}
		}
		return suitArr;
	}
}
