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
public class Player extends JFrame implements java.io.Serializable{
	// the array of cards that is the player's hand
	private ArrayList<Card> hand;
	private TableButton[][] buttons = new TableButton[10][13];
	// declare the array of sets
//	public static ArrayList<LaidDownSetsOfCards> laydownArray = new ArrayList<LaidDownSetsOfCards>();
	public ArrayList<Laydowns> laydownArray = new ArrayList<Laydowns>();

	public TableButton button;
	private boolean finished=false;

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
	public JLabel deckViewFooter;

	public JButton continueButton;

	// this will help us go through the stages
	boolean inStageOne = true;
	boolean inStageTwo = true;
	boolean inStateThree = true;
	public DiscardPile discardPile;
	private cs342project5.Client client;
	
	public void lock(){
		discardPileButton.setEnabled(false);
		deckButton.setEnabled(false);
		card1Button.setEnabled(false);
		card2Button.setEnabled(false);
		card3Button.setEnabled(false);
		card4Button.setEnabled(false);
		card5Button.setEnabled(false);
		card6Button.setEnabled(false);
		card7Button.setEnabled(false);
		card8Button.setEnabled(false);
	}
	public void unlock(){
		discardPileButton.setEnabled(true);
		deckButton.setEnabled(true);
		card1Button.setEnabled(true);
		card2Button.setEnabled(true);
		card3Button.setEnabled(true);
		card4Button.setEnabled(true);
		card5Button.setEnabled(true);
		card6Button.setEnabled(true);
		card7Button.setEnabled(true);
		card8Button.setEnabled(true);
	}
	public Player(Client r, boolean vis) {
		client=r;
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
		this.setVisible(vis);
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

	private void createTableTab() {
		// set the layout (notice that it's ROW, COLUMN for the argument)
		tableView.setLayout(new GridLayout(4, 13));

		// create each button
		for (int row = 0; row < 4; row++){
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

		// display the face down card to represent the deck and, for
		// the time being, the discard pile
		deckButton.setIcon(faceDownCard);
		discardPileButton.setIcon(faceDownCard);

		// set up the footer
		deckViewFooter = new JLabel();

		deckView.add(deckButton);
		deckView.add(discardPileButton);
		deckView.add(deckViewFooter);
	}

	/**
	 * This updates the Gui -- resets the everything that needs to be resetted
	 * (whether it needs it or not)
	 */
	public void updateGui(Card discard) {
		// first let's populate the discard button
		String discardString = discard.getCardString();
		ImageIcon discardCard = new ImageIcon(discardString);
		discardPileButton.setIcon(discardCard);

		// then let's populate the hand
		
		int handSize = hand.size();
		
		for (int i = 0; i < handSize; i++) {
			
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
		}
			 for (int i=handSize; i<8; i++) {
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
			
		}

	

	/**
	 * Bubble sorts a players hand
	 * 
	 * @return ArrayList<Card>
	 */

//	public ArrayList<Card> sortAnyHand(ArrayList<Card> hand) {
//
//		int sizeMinusOne = hand.size() - 1;
//		boolean loop = true;
//		while (loop) {
//			loop = false;
//			for (int j = 0; j < sizeMinusOne; j++) {
//				Card cardA = hand.get(j);
//				Card cardB = hand.get(j + 1);
//				int rankA = cardA.getRank();
//				int rankB = cardB.getRank();
//				if (rankA > rankB) {
//					Collections.swap(hand, j, j + 1);
//					loop = true;
//				}
//			}
//		}
//		return hand;
//	}
//
//	public ArrayList<Card> sortHand() {
//		// bubble sort the hand into order as per the rank of the card
//		// there's no need to actually read this code - treat it as a black box
//		// that returns a sorted hand
//		int sizeMinusOne = hand.size() - 1;
//		boolean loop = true;
//		while (loop) {
//			loop = false;
//			for (int j = 0; j < sizeMinusOne; j++) {
//				Card cardA = hand.get(j);
//				Card cardB = hand.get(j + 1);
//				int rankA = cardA.getRank();
//				int rankB = cardB.getRank();
//				if (rankA > rankB) {
//					Collections.swap(hand, j, j + 1);
//					loop = true;
//				}
//			}
//		}
//		return hand;
//	}

	/**
	 * This method will drive us through an entire player's turn. We will
	 * probably update the GUI periodically throughout the turn. We break the
	 * turn up into three stages: Stage (1) The user chooses either a card off
	 * of the deck or a card off of the discard pile Stage (2) The user chooses
	 * to drop a set or not Stage (3) The user discards That's the end of the
	 * turn, We may update the gui periodically throughout the turn, or not.
	 */
	public void playerTurn() {
		// shows a "welcome to Rummy" message
		//showStageOneMessage();

		// we do stage 1 (which will call stage2 (which will call stage3)))
		
		
		stage1();
		
	}

	private void stage1() {
		// a while loop that takes us through the first stage in the game

		while (inStageOne) {
			/*
			 * If the user clicks the deck button, then we add the card from the
			 * deck to her hand. We also include -- in the action listener
			 * itself -- a removal of the action listener when it's called.
			 */
			
			deckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					// first, we get the card, then we add it to the player's
					// hand, then we update the gui
					Card card = Game.deck.drawCardFromDeck();
					hand.add(card);
					updateGui(Game.discardPile.getDiscardDeck().get(
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
						updateGui(card);
						discardPileButton.setIcon(faceDownCard);
					} else {
						Card topDiscard = Game.discardPile.drawCard();
						hand.add(topDiscard);

						String discardString = topDiscard.getCardString();
						ImageIcon discardCard = new ImageIcon(discardString);
						updateGui(Game.discardPile.getCurrentDiscardCard());
					}
					// black box code -- removes the used action listener
					for (ActionListener act : discardPileButton
							.getActionListeners())
						discardPileButton.removeActionListener(act);
					for (ActionListener act : deckButton.getActionListeners())
						deckButton.removeActionListener(act);
					stage2();
				}

			});
			inStageOne = false;
		}
	}

	private void stage2() {

		tabbedPane.setSelectedIndex(1);

		boolean addToExistingLaydown;

		String s = (String) JOptionPane
				.showInputDialog(
						this,
						"enter the cards you would like to laydown, or 0 for no laydown",
						"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null,
						null, "enter card numbers");

		if (s.equals("0")) {
			stage3();
		}

		else {
			Object[] options = { "New", "Existing" };
			int n = JOptionPane.showOptionDialog(this,
					"Is this a new set or are you adding to an existing set?",
					"new or existing?", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, // do not use a custom
														// Icon
					options, // the titles of buttons
					options[0]); // default button title

			// user wants to add to new set
			if (n == 0) {
				ArrayList<Card> set = new ArrayList<Card>();
				for (int i = 0; i < s.length(); i++)
					if (Character.isDigit(s.charAt(i)))
						// adds the card from the hand to the set to be
						// layeddown
						set.add(hand.get(Character.getNumericValue(s.charAt(i)-1)));
				//set = sortAnyHand(set);
				int setResult = isLegalNewSet(set);

				if (setResult == 0){
					JOptionPane.showMessageDialog(this,
							"Invalid laydown.  Please choose again.");
					stage2();
				}
				else if (setResult == 1){
					laydownArray.add(new Laydowns(set, false));
					System.out.println("you are here! size of first set array is: " + set.size());
					removeCardsAfterLaydown(s);
					updateLaydownTable(new Laydowns(set, false));
					updateGui(Game.discardPile.getCurrentDiscardCard());
					stage3();
				}
				else if (setResult == 2){
					laydownArray.add(new Laydowns(set, true));
					removeCardsAfterLaydown(s);
					updateLaydownTable(new Laydowns(set, true));
					updateGui(Game.discardPile.getCurrentDiscardCard());
					stage3();
				}
			}

			// user wants to add to an existing set
			if (n == 1) {
				if (s.length() != 1) {
					JOptionPane
							.showMessageDialog(this,
									"Please choose 1 card to add to an existing laydown");
					stage2();
				}

				int cardNumber = Integer.parseInt(s);
				Card card = hand.get(cardNumber - 1);
				int result = isLegalAddToExistingSet(card);
				if (result == 0) {
					JOptionPane.showMessageDialog(this,
							"Invalid laydown.  Please choose again.");
					stage2();
				}
				stage3();
			}

		}

		System.out.println(s);

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

                Card card = hand.remove(7);

                card8Button.setIcon(faceDownCard);
                Game.discardPile.putCardOnDiscardPile(card);
                stage3RemoveActionListeners();
                Game.updateGui(card);
                Game.setNextPlayerTurn();
                inStageOne = true;
            }
        });

    }

	private void showStageOneMessage() {
		JOptionPane
				.showMessageDialog(
						Player.this,
						"Hi. Welcome to Rummy, written by some stressed students!\n"
								+ "First you must take a card from either the deck or the top of the discard pile.\n"
								+ "So take a look at your options, and when you are done click on either the face down\n"
								+ "card (the deck) or the card that you can see (the discard pile) to add the card\n"
								+ "to your hand.", "Choices",
						JOptionPane.PLAIN_MESSAGE);

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
		this.setTitle(""+playerID + client.getName());
		this.playerID = playerID;
	}


	// returns 0 if not legal, 1 if a n of a kind, 2 if a run
	// assumes the set is sorted
	public int isLegalNewSet(ArrayList<Card> set) {
		if (set.size() < 3)
			return 0;
		// if 3 of a kind
		else if (set.size() == 3
				&& set.get(0).getRank() == set.get(1).getRank()
				&& set.get(1).getRank() == set.get(2).getRank()) {
			return 1;
		}
		// if 4 of a kind
		else if (set.size() == 4
				&& set.get(0).getRank() == set.get(1).getRank()
				&& set.get(1).getRank() == set.get(2).getRank()
				&& set.get(2).getRank() == set.get(3).getRank())
			return 1;
		// if its a run
		else {
			for (int i1 = 0; i1 < set.size() - 1; i1++)
				// checks suit
				if (set.get(i1).getSuit() != set.get(i1 + 1).getSuit())
					return 0;
			// checks straight
			for (int i2 = 0; i2 < set.size() - 1; i2++)
				if (set.get(i2).getRank()+1 != set.get(i2 + 1).getRank() )
					return 0;
			return 2;
		}

	}

	// lots of confusing code, just assume it works
	// returns 0 if illegal add, returnss 1 if legal add

	public int isLegalAddToExistingSet(Card card) {

		for (int i = 0; i < laydownArray.size(); i++) {
			if (laydownArray.get(i).getIfRun() == false
					&& laydownArray.get(i).getLaydown().size() == 3
					&& laydownArray.get(i).getLaydown().get(0).getRank() == card
							.getRank()) {

				laydownArray.get(i).addCard(card);
				return 1;
			} else if (laydownArray.get(i).getIfRun()
					&& laydownArray.get(i).getLaydown().get(0).getSuit() == card
							.getSuit()
					&& laydownArray.get(i).getLaydown().get(0).getRank() == (card
							.getRank() + 1)) {
				laydownArray.get(i).addCardToBeginning(card);
				return 1;
			} else if (laydownArray.get(i).getIfRun()
					&& laydownArray.get(i).getLaydown().get(0).getSuit() == card
							.getSuit()
					&& laydownArray.get(i).getLaydown()
							.get(laydownArray.get(i).getLaydown().size())
							.getRank() == (card.getRank() - 1)) {
				laydownArray.get(i).addCard(card);
				return 1;
			}

		}
		return 0;
	}
	
	public void stage3RemoveActionListeners(){
		
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
		
		inStageOne=true;
	}
	
	public void removeCardsAfterLaydown(String s){
		
		int count=0;
		for (int i = 0; i < s.length(); i++)
			if (Character.isDigit(s.charAt(i))){
				hand.remove(Character.getNumericValue(s.charAt(i)-1-count));
				count++;
			}
		
	}
	
	public void updateLaydownTable(Laydowns laydown){
		ArrayList<Card> cardArray = new ArrayList<Card>();
		cardArray=laydown.getLaydown();
		for (int i=0; i<cardArray.size(); i++){
			Card card = cardArray.get(i);
			System.out.println("adding card "+ card.getCardString());
			ImageIcon newImage = new ImageIcon(card.getCardString());
			buttons[card.getSuit()-1][card.getRank()-1].setIcon(newImage);
		}
		cs342project5.GameState gs2 = new cs342project5.GameState(client.getId(), Game.deck, discardPile, Game.player1.laydownArray, client.getPlayerID(), true, 0, discardPile.getCurrentDiscardCard());
		client.send(gs2);

	}
	
	public boolean checkForEndOfGame(){
		return finished;
	}
	public void setFinished(boolean fin){
		finished=fin;
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
	 * This updates the Gui -- resets the everything that needs to be resetted
	 * (whether it needs it or not)
	 */
	public void populateGui(Card discard) {
		// first let's populate the discard button
		String discardString = discard.getCardString();
		ImageIcon discardCard = new ImageIcon(discardString);
		discardPileButton.setIcon(discardCard);

		// then let's populate the hand
		if(hand != null)
		updateGuiHand(hand);
	}
}

