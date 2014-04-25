package rummy;
import javax.swing.*;

import cs342project5.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by bob on 4/18/14. This is the player class -- however, we're also
 * going to make it the GUI class as well, being that all of the info that we
 * need for the gui will be held in the players' data.\ So what do we want to do
 * first? (1) We want to set it up so that when the cards are dealt, we get a
 * screen to our liking. Like this:
 * 
 */
public class Player extends JFrame implements ActionListener {
	// the array of cards that is the player's hand
	private ArrayList<Card> hand;
	private TableButton[][] buttons = new TableButton[10][13];
	// declare the array of sets
	static ArrayList<LaidDownSetsOfCards> arrayOfLaidDownSets = new ArrayList<LaidDownSetsOfCards>();
	public TableButton button;

	// the player's ID
	private int playerID;

	// declare the tabbed pane
	private JTabbedPane tabbedPane = new JTabbedPane();

	// declare the tabs for the tabbed pane
	private JPanel deckView = new JPanel();
	private JPanel handView = new JPanel();
	private JPanel tableView = new JPanel();

	// put this at the top for easy access
	ImageIcon faceDownCard = new ImageIcon("facedown_card.png");
	ImageIcon questionMark = new ImageIcon("question_mark.png");
	ImageIcon continueImage = new ImageIcon("continue.png");

	// declare all of the buttons
	public JButton discardPileButton, deckButton, card1Button, card2Button,
			card3Button, card4Button, card5Button, card6Button, card7Button,
			card8Button;

	public JButton continueButton;

	// this will help us go through the stages
	boolean inStageOne = true;
	boolean inStageTwo = true;
	boolean inStateThree = true;
	public DiscardPile discardPile;
	private cs342project5.Game rummy;
	
	public int getStage(){
		if(inStageOne)
			return 1;
		if(inStageTwo)
			return 2;
		if(inStateThree)
			return 3;
		
		return 0;
	}
	public Player(cs342project5.Game r) {
		rummy=r;
		// add the tabs to the tabbedPane
		tabbedPane.add("Deck and Discard", deckView);
		tabbedPane.add("Your Hand", handView);
		tabbedPane.add("Shared Cards", tableView);
		add(tabbedPane);

		// this creates spots for the cards to go, but DOESN'T populate them
		createDeckTab();
		createHandTab();
		createTableTab();

		// and the rest of the paperwork -- close upon closing of window, size,
		// visible
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(880, 1000);
		this.setVisible(true);
	}

	private void createTableTab() {
		// set the layout (notice that it's ROW, COLUMN for the argument)
		tableView.setLayout(new GridLayout(10, 13));

		// create each button
		for (int row = 0; row < 10; row++)
			for (int col = 0; col < 13; col++) {
				// instantiate the button
				button = new TableButton();

				// put the button into out 2d array of buttons
				buttons[row][col] = button;

				// set the button as "unoccupied"
				buttons[row][col].setOccupiedButton(false);

				// set the default icon (question mark)
				button.setIcon(questionMark);

				// add it to the panel
				tableView.add(button);
			}
	}

	private void createHandTab() {
		// set the layout
		handView.setLayout(new FlowLayout());

		// create the seven buttons (this is the default -- when they're empty,
		// they'll have a facedown card
		card1Button = new JButton();
		card2Button = new JButton();
		card3Button = new JButton();
		card4Button = new JButton();
		card5Button = new JButton();
		card6Button = new JButton();
		card7Button = new JButton();
		card8Button = new JButton();

		// give the cards default images
		ImageIcon faceDownCard = new ImageIcon("facedown_card.png");
		card1Button.setIcon(faceDownCard);
		card2Button.setIcon(faceDownCard);
		card3Button.setIcon(faceDownCard);
		card4Button.setIcon(faceDownCard);
		card5Button.setIcon(faceDownCard);
		card6Button.setIcon(faceDownCard);
		card7Button.setIcon(faceDownCard);
		card8Button.setIcon(faceDownCard);

		// add the card buttons to the panel
		handView.add(card1Button);
		handView.add(card2Button);
		handView.add(card3Button);
		handView.add(card4Button);
		handView.add(card5Button);
		handView.add(card6Button);
		handView.add(card7Button);
		handView.add(card8Button);
	}

	/**
	 * This just creates the tab for the "deck view" -- the discard.
	 */
	private void createDeckTab() {
		// set the layout
		deckView.setLayout(new FlowLayout());

		// create the buttons that hold the cards (one of them is always
		// just an facedown card, the other is the top of the discard pile)
		deckButton = new JButton();
		discardPileButton = new JButton();
		continueButton = new JButton();

		// display the face down card to represent the deck and, for
		// the time being, the discard pile
		deckButton.setIcon(faceDownCard);
		discardPileButton.setIcon(faceDownCard);
		continueButton.setIcon(continueImage);

		deckView.add(deckButton);
		deckView.add(discardPileButton);
		deckView.add(continueButton);
		
	}

	/**
	 * This updates the Gui -- resets the everything that needs to be resetted
	 * (whether it needs it or not)
	 */
	public void populateGui(Card discard) {
		// first let's populate the discard button
		String discardString = discard.getCardString();
		ImageIcon discardCard = new ImageIcon(discardString);
		discardPileButton.setIcon(discardCard);

		// then let's populate the hand
		ArrayList<Card> tempArr = this.getHand();
		updateGuiHand(tempArr);

	}

	public void updateGuiHand(ArrayList<Card> hand) {
		int handSize = hand.size();
		int count = 0;
		for (int i = 0; i < 8; i++) {
			if (count < handSize) {
				Card card = hand.get(i);
				String cardString = card.getCardString();
				ImageIcon cardIcon = new ImageIcon(cardString);
				if (0 == i)
					card1Button.setIcon(cardIcon);
				else if (1 == i)
					card2Button.setIcon(cardIcon);
				else if (2 == i)
					card3Button.setIcon(cardIcon);
				else if (3 == i)
					card4Button.setIcon(cardIcon);
				else if (4 == i)
					card5Button.setIcon(cardIcon);
				else if (5 == i)
					card6Button.setIcon(cardIcon);
				else if (6 == i)
					card7Button.setIcon(cardIcon);
				else
					card8Button.setIcon(cardIcon);
			} else {
				if (0 == i)
					card1Button.setIcon(faceDownCard);
				else if (1 == i)
					card2Button.setIcon(faceDownCard);
				else if (2 == i)
					card3Button.setIcon(faceDownCard);
				else if (3 == i)
					card4Button.setIcon(faceDownCard);
				else if (4 == i)
					card5Button.setIcon(faceDownCard);
				else if (5 == i)
					card6Button.setIcon(faceDownCard);
				else if (6 == i)
					card7Button.setIcon(faceDownCard);
				else
					card8Button.setIcon(faceDownCard);
			}
			count++;
		}
	}

	/**
	 * Bubble sorts a players hand
	 * 
	 * @return ArrayList<Card>
	 */
	public ArrayList<Card> sortHand() {
		// bubble sort the hand into order as per the rank of the card
		int sizeMinusOne = hand.size() - 1;
		boolean loop = true;
		while (loop) {
			loop = false;
			for (int j = 0; j < sizeMinusOne; j++) {
				Card cardA = hand.get(j);
				Card cardB = hand.get(j + 1);
				int rankA = cardA.getRank();
				int rankB = cardB.getRank();
				if (rankA > rankB) {
					Collections.swap(hand, j, j + 1);
					loop = true;
				}
			}
		}
		return hand;
	}

	/**
	 * This method will drive us through an entire player's turn. We will
	 * probably update the GUI periodically throughout the turn. We break the
	 * turn up into three stages: Stage (1) The user chooses either a card off
	 * of the deck or a card off of the discard pile Stage (2) The user chooses
	 * to drop a set or not Stage (3) The user discards Note that this is like
	 * the Russian Doll of methods -- Stage2 has to be called in Stage1, Stage3
	 * has to be called in Stage2.
	 */
	public void playerTurn() {
		// shows a "welcome to Rummy" message

		// we do stage 1 (which will call stage2 (which will call stage3)))
		stage1();
	}

	private void stage1() {
		// a while loop that takes us through the first stage in the game

		tabbedPane.setSelectedIndex(0);
		showStageOneMessage();
		/*
		 * If the user clicks the deck button, then we add the card from the
		 * deck to her hand. We also include -- in the action listener itself --
		 * a removal of the action listener when it's called.
		 */
		deckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// first, we get the card, then we add it to the player's hand,
				// then we update the gui
				Card card = Game.deck.drawCardFromDeck();
				hand.add(card);
				populateGui(Game.discardPile.getDiscardDeck().get(
						Game.discardPile.getDiscardDeck().size() - 1));

				// black box code -- removes the action listener
				for (ActionListener act : discardPileButton
						.getActionListeners())
					discardPileButton.removeActionListener(act);
				for (ActionListener act : deckButton.getActionListeners())
					deckButton.removeActionListener(act);
				stage2();
			}
		});
		discardPileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// if the size of the deck is one, we're taking the ONLY
				// card in the discard pile! Therefore we have to
				// make the discard pile blank
				if (Game.discardPile.getDiscardDeck().size() == 1) {
					Card card = Game.discardPile.drawCard();
					hand.add(card);
					populateGui(card);
					discardPileButton.setIcon(faceDownCard);
				} else {
					Card topDiscard = Game.discardPile.drawCard();
					hand.add(topDiscard);

					String discardString = topDiscard.getCardString();
					ImageIcon discardCard = new ImageIcon(discardString);
					populateGui(Game.discardPile.getCurrentDiscardCard());
				}
				// black box code -- removes the used action listener
				for (ActionListener act : discardPileButton
						.getActionListeners())
					discardPileButton.removeActionListener(act);
				for (ActionListener act : deckButton.getActionListeners())
					deckButton.removeActionListener(act);

				// call for stage2
				stage2();
			}
		});
		inStageOne = false;
		
	}// END WHILE LOOP

	private void stage2() {
		// user has to choose whether to create a new set or add to existing set
		boolean isNewSet = false;

		// variable that shows three things: (1) legality of set, (2) run, (3) n
		// of a kind
		// this forces a particular tab to open --the "hand" tab
		tabbedPane.setSelectedIndex(1);

		// user chooses what cards to lay down (if any)
		String discardString = userDiscards();

		System.out.println("\n\n\n" + discardString);
		// if the user chooses to not lay down any cards, we move on to stage
		// three
		if (discardString.equals("0")) {
			stage3();
		}

		// else we determine if the user is laying down a new set or not
		else {
			isNewSet = userChoiceIsNewSet();
			if (isNewSet)
				makeNewSet(discardString, isNewSet);
			else
				addToExistingSet(discardString, isNewSet);
			stage3();
		}
		
		/*
		 * If the user wants to make a new set, we call makeNewSet. This method
		 * accomplishes a lot -- it checks the legit of the chosen cards and
		 * updates the gui. It's NOT a black box. The same goes for
		 * "addToExistingSet", as it's not exactly the simplest method either.
		 */

		// this is where we need a pause -- this will lead us back to the loop
		// allowTheUserToExamineCards();
		
	}

	private void allowTheUserToExamineCards() {
		tabbedPane.setSelectedIndex(1); // switch the pane back to discard
		JOptionPane
				.showMessageDialog(
						this,
						"This is a good spot for a pause.\n\n"
								+ "Take a moment to look at your hand and the shared cards,\n"
								+ "to decide if and what cards you would like to lay down next.\n\n"
								+ "When you have decided, hit the blue \"continue\" button in\n"
								+ "the \"Deck and Discard\" view.");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// we are in an infinite loop until the user enters a zero or
				// <enter>
				stage2();
			}
		});
	}

	private void addToExistingSet(String discardString, boolean isNewSet) {
		// create a temporary array to examine the cards
		ArrayList<Card> tempCardArray = putUserChosenCardsIntoArray(
				discardString, isNewSet);
		int size = tempCardArray.size();
		int numberOfSets = arrayOfLaidDownSets.size();
		boolean addToRun = false;
		int numCardsInSet = 0;

		// check all of the sets that are runs to see if any fit
		for (int i = 0; i < size; i++) {
			// get the card that we're looking at
			Card currentCard = tempCardArray.get(i);

			// go through each set, trying to fit it in the "run" sets
			for (int setNumber = 0; setNumber < numberOfSets; setNumber++) {
				// first, only look at the sets with "runs"
				if (arrayOfLaidDownSets.get(setNumber).isConsecutiveCardSet()) {
					// get the set
					LaidDownSetsOfCards set = arrayOfLaidDownSets
							.get(setNumber);

					// get the lowest card in the set
					Card lowestCardInSet = set.getCardInLaidDownSet(0);

					// compare the ranks (must be one less), compare the suits
					if (compareRanksOfCards(currentCard, lowestCardInSet))
						if (compareSuitsOfCards(currentCard, lowestCardInSet)) {
							addToRun = true;
							set.addCardToBeginningOfSet(currentCard);
							addCardToGui(currentCard, setNumber, addToRun,
									numCardsInSet);
						}

					// next we'll check if we can fit it in AFTER the set
					// get the highest card in the set
					Card highestCardInSet = set.getCardInLaidDownSet(set
							.getSizeOfSet() - 1);

					// compare the ranks (must be one MORE), compare the suits
					if (compareRanksOfCards(currentCard, highestCardInSet))
						if (compareSuitsOfCards(currentCard, highestCardInSet)) {
							addToRun = false;
							set.addCardToEndOfSet(currentCard);
							addCardToGui(currentCard, setNumber, addToRun,
									numCardsInSet);
						}
				}
			}// END OF FOR LOOP THAT GOES THROUGH ALL "RUN" SETS IN SHARED TABLE

			// go through each "nOfAKind" set, trying to fit it the card
			for (int setNumber = 0; setNumber < numberOfSets; setNumber++) {
				// first, only look at the sets with "nOfAKind"
				if (arrayOfLaidDownSets.get(setNumber).isnOfAKindSet()) {
					// get the set
					LaidDownSetsOfCards set = arrayOfLaidDownSets
							.get(setNumber);

					// get the number of the "nOfAKind"
					Card nOfKindCard = set.getCardInLaidDownSet(0);

					// compare the cards (must be equal)
					if (compareRanksOfCards(currentCard, nOfKindCard)) {
						numCardsInSet = set.getSizeOfSet();
						set.addCardToEndOfSet(currentCard);
						addCardToGui(currentCard, setNumber, addToRun,
								numCardsInSet);
					} else
						giveErrorMessageStartStage2Over();
				}
			}
		}// END OF FOR LOOP THAT GOES THROUGH ALL CARDS IN SET
		stage3();
	}

	private boolean compareRanksOfCards(Card cardA, Card cardB) {
		int rankCardA = cardA.getRank();
		int rankCardB = cardB.getRank();
		if (rankCardA == rankCardB)
			return true;
		return false;
	}

	private boolean compareSuitsOfCards(Card cardA, Card cardB) {
		int suitCardA = cardA.getSuit();
		int suitCardB = cardB.getSuit();
		if (suitCardA == suitCardB)
			return true;
		return false;
	}

	private void addCardToGui(Card currentCard, int setNumber,
			boolean addToRun, int numCardsInSet) {
		int col = 0;
		String cardString = currentCard.getCardString();
		ImageIcon cardImage = new ImageIcon(cardString);
		if (addToRun)
			col = currentCard.getRank() - 1;
		else
			col = numCardsInSet;
		int row = setNumber;
		buttons[row][col].setOccupiedButton(true);
		buttons[row][col].setIcon(cardImage);
		button.setRowOfCard(row);
		button.setColumnOfCard(col);
	}

	/**
	 * This is a driver method to make a new set -- we need to get the set,
	 * check that the cards are legit, and add it to the gui
	 */
	private void makeNewSet(String discardString, boolean isNewSet) {
		boolean isConsecutiveCardSet = false;

		// create the array for the new Set
		ArrayList<Card> set = new ArrayList<Card>();

		// create a temporary array to examine the cards
		ArrayList<Card> tempCardArray = putUserChosenCardsIntoArray(
				discardString, isNewSet);

		// what to do if the hand IS a run
		if (arrayIsLegitRun(tempCardArray)) {
			isConsecutiveCardSet = true;
			removeSetCardsFromHand(tempCardArray);
			addLaidDownCardsToSetAndButtonArray(tempCardArray,
					isConsecutiveCardSet);
		}
		// else we check for an "nOfAKind"
		else if (arrayIsLegit_nOfAKind(tempCardArray)) {
			removeSetCardsFromHand(tempCardArray);
			addLaidDownCardsToSetAndButtonArray(tempCardArray,
					isConsecutiveCardSet);
		}
		// else they have not given a legit group of cards
		else
			giveErrorMessageStartStage2Over();
	}

	private ArrayList<Card> putUserChosenCardsIntoArray(String discardString,
			boolean isNewSet) {
		// simple count variable to count the number of cards in the set
		int count = 0;

		// parse the user's string to get the cards
		int size = discardString.length();
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			// if the character in the string is a digit
			if (Character.isDigit(discardString.charAt(i))) {
				// get the digit
				int cardNumber = Character.getNumericValue(discardString
						.charAt(i));

				// check that it's within range of 1 to 8
				if (cardNumber < 1 || cardNumber > 8)
					invalidEntry(cardNumber);

				// have to decrement the cardNumber so we can start at zero with
				// the array
				cardNumber--;

				// add the card number to a temp array, increment count
				tempArray.add(cardNumber);
				count++;
			}
		}

		// check that there are enough cards in the new set
		if (count < 3 && isNewSet)
			invalidSizeForNewSet(count);

		// next we put the chosen cards into a temp card array and organize them
		ArrayList<Card> tempCardArray = new ArrayList<Card>(size);
		for (int i = 0; i < count; i++) {
			Card card = hand.get(tempArray.get(i));
			tempCardArray.add(card);
		}
		sortTempHand(tempCardArray);
		return tempCardArray;
	}

	/**
	 * This is easier. If the cards have the same rank, then they ARE nOfAKind.
	 * 
	 * @param tempCardArray
	 * @return boolean
	 */
	private boolean arrayIsLegit_nOfAKind(ArrayList<Card> tempCardArray) {
		int size = tempCardArray.size();
		ArrayList<Integer> ranksOfCards = new ArrayList<Integer>(size);

		// get all the ranks of the cards and check them against each other
		for (int i = 0; i < size; i++) {
			Card card = tempCardArray.get(i);
			ranksOfCards.add(card.getRank());
		}
		for (int i = 0; i < size - 1; i++) {
			int rankA = ranksOfCards.get(i);
			int rankB = ranksOfCards.get(i + 1);
			if (rankA != rankB)
				return false;
		}
		return true;
	}

	private void addLaidDownCardsToSetAndButtonArray(
			ArrayList<Card> tempCardArray, boolean isRun) {
		// create a new laid down set
		LaidDownSetsOfCards set = new LaidDownSetsOfCards();

		// populate it with the cards from tempArray
		set.setLaidDownSetOfCards(tempCardArray);

		// set it's size (might be useful later)
		set.setSizeOfSet(tempCardArray.size());

		// add the set to the "set of sets"
		arrayOfLaidDownSets.add(set);

		// element number of the newly created set -- can act as a set ID
		set.setSetID(arrayOfLaidDownSets.size());

		int row = set.getSetID() - 1; // since the rows start at zero
		int size = set.getSizeOfSet();

		// set that it's a "run" or "nOfAKind"
		if (isRun) {
			// update the array of sets
			set.setConsecutiveCardSet(true);
			set.setnOfAKindSet(false);

			// update the gui
			for (int i = 0; i < size; i++) {
				Card card = set.getCardInLaidDownSet(i);
				String cardString = card.getCardString();
				ImageIcon cardImage = new ImageIcon(cardString);
				int col = card.getRank() - 1; // since the columns start at zero
				buttons[row][col].setOccupiedButton(true); // set the button as
															// "occupied"
				buttons[row][col].setIcon(cardImage);
				button.setRowOfCard(row);
				button.setColumnOfCard(col);
			}
		} else {
			// update the array of sets
			set.setConsecutiveCardSet(false);
			set.setnOfAKindSet(true);

			// update the gui
			for (int i = 0; i < size; i++) {
				Card card = set.getCardInLaidDownSet(i);
				String cardString = card.getCardString();
				ImageIcon cardImage = new ImageIcon(cardString);
				int col = i; // the variable is redundant, but this makes it
								// more readable
				buttons[row][col].setOccupiedButton(true);
				buttons[row][col].setIcon(cardImage);
			}
		}
	}

	private void removeSetCardsFromHand(ArrayList<Card> tempCardArray) {
		int size = tempCardArray.size();
		for (int i = 0; i < size; i++) {
			hand.remove(tempCardArray.get(i));
		}
		sortTempHand(hand);
		updateGuiHand(hand);
	}

	private boolean arrayIsLegitRun(ArrayList<Card> tempCardArray) {
		// declare an array of ints that we will put the ranks of the
		// tempCardArray into
		int size = tempCardArray.size();
		ArrayList<Integer> suitOfTempCardArray = new ArrayList<Integer>(size);
		ArrayList<Integer> rankOfTempCardArray = new ArrayList<Integer>(size);

		/*
		 * first, if the cards have different suits, then they can't be a run.
		 * So we put the suits into an array and see if any are different. If
		 * they are, then we can check "run" off the list.
		 */
		for (int i = 0; i < size; i++) {
			Card card = tempCardArray.get(i);
			int suit = card.getSuit();
			suitOfTempCardArray.add(suit);
		}
		for (int i = 0; i < size - 1; i++) {
			int suitA = suitOfTempCardArray.get(i);
			int suitB = suitOfTempCardArray.get(i + 1);
			if (suitA != suitB)
				return true;
		}

		/*
		 * Now that we've determined that all the suits are equal, we just need
		 * to see if the ranks are consecutive. First, create the rank array.
		 * Then, if each card's rank is one more than the one before it, it's a
		 * run.
		 */
		for (int i = 0; i < size; i++) {
			Card card = tempCardArray.get(i);
			int rank = card.getRank();
			rankOfTempCardArray.add(rank);
		}
		for (int i = 1; i < size; i++) {
			int rankB = rankOfTempCardArray.get(i);
			int rankA = rankOfTempCardArray.get(i - 1);
			if (rankB != rankA + 1)
				return true;
		}

		// the array MUST be a run
		return true;
	}

	private ArrayList<Card> sortTempHand(ArrayList<Card> tempCardArray) {
		int sizeMinusOne = tempCardArray.size() - 1;
		boolean loop = true;
		while (loop) {
			loop = false;
			for (int j = 0; j < sizeMinusOne; j++) {
				Card cardA = tempCardArray.get(j);
				Card cardB = tempCardArray.get(j + 1);
				int rankA = cardA.getRank();
				int rankB = cardB.getRank();
				if (rankA > rankB) {
					Collections.swap(tempCardArray, j, j + 1);
					loop = true;
				}
			}
		}
		return tempCardArray;
	}

	private void invalidSizeForNewSet(int count) {
		JOptionPane.showMessageDialog(this,
				"I'm sorry, but you need to have more cards to\n"
						+ "start a new set. " + count
						+ " cards ain't gonna do it -- you\n"
						+ "need at least three cards to start a new set.\n\n"
						+ "Please try that again...");
		stage2();
	}

	private void invalidEntry(int cardNumber) {
		JOptionPane.showMessageDialog(this, "I'm sorry, but " + cardNumber
				+ " is not between 1 and 8.\n\n" + "Please try that again...");
		stage2();
	}

	private void giveErrorMessageStartStage2Over() {
		JOptionPane.showMessageDialog(this,
				"I'm sorry, but the cards you selected are not valid as a new set.\n\n"
						+ "Please try that again...");
		stage2();
	}

	/**
	 * Questions the user: new or existing set?
	 * 
	 * @return boolean
	 */
	private boolean userChoiceIsNewSet() {
		// create the "choice" objects that the user picks from
		Object[] options = { "New Set", "Existing Set" };

		// window that asks the question "New or Existing set?"
		int n = JOptionPane.showOptionDialog(this,
				"Are you starting a new set, or is this an existing set?",
				"New Set or Existing Set?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, // the icon to use (we use
													// none)
				options, // the button titles
				options[0]); // default button titles
		if (0 != n)
			return false;
		return true;
	}

	/**
	 * This is a simple stage. The user simply chooses a card from the deck to
	 * discard.
	 */
	private void stage3() {

		// this forces a particular tab to open --the "hand" tab
		tabbedPane.setSelectedIndex(1);

		JOptionPane.showMessageDialog(this,
				"Select a card from your hand to discard");

		card1Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(0);
				hand.remove(0);
				card1Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card2Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(1);
				hand.remove(1);
				card2Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card3Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(2);
				hand.remove(2);
				card3Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card4Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(3);
				hand.remove(3);
				card4Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card5Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(4);
				hand.remove(4);
				card5Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card6Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(5);
				hand.remove(5);
				card6Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card7Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(6);
				hand.remove(6);
				card7Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});
		card8Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// add an action listener to each card in the hand

				Card card = hand.get(7);
				hand.remove(7);
				card8Button.setIcon(faceDownCard);
				Game.discardPile.putCardOnDiscardPile(card);
				stage3RemoveActionListeners();
				Game.updateGui(card);
				Game.setNextPlayerTurn();
				inStageOne = true;
			}
		});

	}

	public void stage3RemoveActionListeners() {

		for (ActionListener act : card1Button.getActionListeners())
			card1Button.removeActionListener(act);
		for (ActionListener act : card2Button.getActionListeners())
			card2Button.removeActionListener(act);
		for (ActionListener act : card3Button.getActionListeners())
			card3Button.removeActionListener(act);
		for (ActionListener act : card4Button.getActionListeners())
			card4Button.removeActionListener(act);
		for (ActionListener act : card5Button.getActionListeners())
			card5Button.removeActionListener(act);
		for (ActionListener act : card6Button.getActionListeners())
			card6Button.removeActionListener(act);
		for (ActionListener act : card7Button.getActionListeners())
			card7Button.removeActionListener(act);
		for (ActionListener act : card8Button.getActionListeners())
			card8Button.removeActionListener(act);

	}

	/**
	 * The user enters the cards that they would like to discard as a string
	 * 
	 * @return String
	 */
	private String userDiscards() {
		String discardString = (String) JOptionPane.showInputDialog(this,
				"Cards are numbered 1 through 8.\n\n"
						+ "If you are laying down a new set,\n"
						+ "use three cards.\n\n" + "Use the format \"# # #\".");
		return discardString;
	}

	private void showStageOneMessage() {
		JOptionPane.showMessageDialog(Player.this, "It is now your turn!\n\n"
				+ "Please click on the deck to draw a card,\n"
				+ "or click on the discard pile to draw the\n"
				+ "last discarded card.", "Draw a Card",
				JOptionPane.PLAIN_MESSAGE);

	}

	public boolean checkForEndOfGame() {
		if (hand.size() == 0)
			return true;
		return false;
	}

	// this is the getter and setter for the player's hand
	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	// this is the getter and setter for the player's ID
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

	}

}
