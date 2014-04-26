package rummy;

import javax.swing.*;

import cs342project5.Client;
import cs342project5.GameState;

import java.util.ArrayList;

/**
 * Created by bob on 4/18/14.
 * This is the class that drives the entire game.
 * We play the game, using an ASCII representation, and from
 * time to time we update the gui,
 */
public class Game implements java.io.Serializable
{
	static Deck deck;
	static DiscardPile discardPile;

	public static Player player1;
	public static Player player2;
	public static Player player3;
	public static Player player4;
	static int playerTurn = 1;
	static int numPlayers = 1;
	private static Client rummy;
	public Game(cs342project5.Client g){
		rummy = g;
	}
	public void update(GameState gs){
		player1.arrayOfLaidDownSets = gs.getArrayOfLaidDownSets();
		discardPile = gs.getDiscardPile();
		deck = gs.getDeck();
	}
	public void joinGame(GameState gs)
	{
		int whichPlayerToUpdate = rummy.getPlayerID();
	
		if(numPlayers > 4)
			System.exit(-1);
		
		numPlayers++;
		if(whichPlayerToUpdate==1){
			player1.arrayOfLaidDownSets = gs.getArrayOfLaidDownSets();
		}else if(whichPlayerToUpdate==2){
			player2.arrayOfLaidDownSets = gs.getArrayOfLaidDownSets();
		}else if(whichPlayerToUpdate==3){
			player3.arrayOfLaidDownSets = gs.getArrayOfLaidDownSets();
		}else if(whichPlayerToUpdate==4){
			player4.arrayOfLaidDownSets = gs.getArrayOfLaidDownSets();
		}
		discardPile = gs.getDiscardPile();
		deck = gs.getDeck();

		declarePlayers();
		dealTheCards();
		sortHandsSmallToLarge();
		//          displayHandASCII();


		// update the GUI
		for (int i = 0; i < numPlayers; i++)
		{
			// we're gonna need to send some variables along with the methods. The discard and the table
			Card discard = discardPile.getCurrentDiscardCard();
			if      (0 == i) player1.populateGui(discard);
			else if (1 == i) player2.populateGui(discard);
			else if (2 == i) player3.populateGui(discard);
			else             player4.populateGui(discard);
		}

	}
	/*
	 * stage1
	 * update deck and the discard pile depending which it gets drawn from
	 * stage2
	 * drop your cards
	 * update tableview
	 * stage3
	 * update discard pile
	 * if the deck is 0 update deck
	 */
	public void startGame()
	{    	
		// create an shuffle the deck
		deck = new Deck();
		deck.printDeck();
		discardPile = new DiscardPile();
		addFirstCardToDiscardPile(); // done once per game, unless we run out of cards
		//  deck.printDeck(); // just to make sure that one was taken off the top


		// deal the cards
		declarePlayers();
		dealTheCards();
		sortHandsSmallToLarge();
		//        displayHandASCII();


		// update the GUI
		for (int i = 0; i < numPlayers; i++)
		{
			// we're gonna need to send some variables along with the methods. The discard and the table
			Card discard = discardPile.getCurrentDiscardCard();
			if      (0 == i) player1.populateGui(discard);
			else if (1 == i) player2.populateGui(discard);
			else if (2 == i) player3.populateGui(discard);
			else             player4.populateGui(discard);
		}


		//starts player 1s turn, he will start player 2s turn and so on
		player1.playerTurn();       

	}

	public static void endGame()
	{
		System.out.println("Can't figure out why it won't let me show a window!!!!");
		System.out.println("The game is over, I just wanna show it!");
		System.exit(1);
	}

	/**
	 * Sorts the hands into rank order
	 */
	private static void sortHandsSmallToLarge()
	{
		for (int i = 0; i < numPlayers; i++)
		{
			if      (0 == i) player1.sortHand();
			else if (1 == i) player2.sortHand();
			else if (2 == i) player3.sortHand();
			else             player4.sortHand();
		}
	}


	/**
	 * Displays the cards in ASCII for debugging purposes
	 */
	private static void displayHandASCII()
	{
		int size = 0;
		ArrayList<Card> tempArr;
		for (int i = 0; i < numPlayers; i++)
		{
			if      (0 == i) tempArr = player1.getHand();
			else if (1 == i) tempArr = player2.getHand();
			else if (2 == i) tempArr = player3.getHand();
			else             tempArr = player4.getHand();
			size = tempArr.size();
			System.out.print("Hand of player " + (i + 1) + ":\t");
			for (int j = 0; j < size; j++)
			{
				Card card = tempArr.get(j);
				String cardString = card.getCardString();
				System.out.print(cardString + "\t");
			}
			System.out.println();
		}
	}

	private static void declarePlayers()
	{
		if (1 == numPlayers)
		{
			player1 = new Player(rummy);
			player1.setPlayerID(1);
		}
		else if (2 == numPlayers)
		{
			player1 = new Player(rummy);
			player2 = new Player(rummy);
			player1.setPlayerID(1);
			player2.setPlayerID(2);
		}
		else if (3 == numPlayers)
		{
			player1 = new Player(rummy);
			player2 = new Player(rummy);
			player3 = new Player(rummy);
			player1.setPlayerID(1);
			player2.setPlayerID(2);
			player3.setPlayerID(3);
		}
		else if (4 == numPlayers)
		{
			player1 = new Player(rummy);
			player2 = new Player(rummy);
			player3 = new Player(rummy);
			player4 = new Player(rummy);
			player1.setPlayerID(1);
			player2.setPlayerID(2);
			player3.setPlayerID(3);
			player4.setPlayerID(4);
		}
	}





	//updates the gui for all the players
	public static void updateGui(Card discard){
		System.out.println("Updating gui from game class");

		for (int i = 0; i < numPlayers; i++)
		{
			// we're gonna need to send some variables along with the methods. The discard and the table

			if      (0 == i) {player1.populateGui(discard); player1.updateGuiHand(player1.getHand()); }
			else if (1 == i) {player2.populateGui(discard); player2.updateGuiHand(player2.getHand()); }
			else if (2 == i) {player3.populateGui(discard); player3.updateGuiHand(player3.getHand()); }
			else             {player4.populateGui(discard); player4.updateGuiHand(player4.getHand()); }
		}


	}
	/**
	 * The physical act of dealing the cards.  This is also where we declare the
	 * players, as this is the first time that we see them.
	 * Note that I'm coding for up to four players, although we're only starting
	 * with two.
	 */
	private void dealTheCards()
	{
		for (int i = 0; i < numPlayers; i++)
		{
			ArrayList<Card> tempArray = new ArrayList<Card>(7);
			for (int j = 0; j < 7; j++) // 7 card in the hand
			{
				// we put the hand ito a temporary array
				Card card = deck.drawCardFromDeck();
				tempArray.add(card);
			}
			// then we set that temp array to the proper player
			if      (0 == i) player1.setHand(tempArray);
			else if (1 == i) player2.setHand(tempArray);
			else if (2 == i) player3.setHand(tempArray);
			else             player4.setHand(tempArray);
		}
	}

	//sets the next players turn.  This is called in stage 3
	public static void setNextPlayerTurn(){
		boolean gameOver = false;
		if (playerTurn==numPlayers)
			playerTurn=1;	
		else 
			playerTurn++;	
		System.out.println("Player turn is now: "+ playerTurn);

		if (playerTurn == 1)
		{
			player1.playerTurn();
			cs342project5.GameState gs = new cs342project5.GameState(rummy.getId(), Game.deck, Game.discardPile, player1.arrayOfLaidDownSets, rummy.getPlayerID());
			rummy.send(gs);
			gameOver = player1.checkForEndOfGame();
		}
		else if (playerTurn == 2)
		{
			player2.playerTurn();
			cs342project5.GameState gs = new cs342project5.GameState(rummy.getId(), Game.deck, Game.discardPile, player2.arrayOfLaidDownSets, rummy.getPlayerID());
			rummy.send(gs);
			gameOver = player2.checkForEndOfGame();
		}
		else if (playerTurn == 3)
		{
			player3.playerTurn();
			cs342project5.GameState gs = new cs342project5.GameState(rummy.getId(), Game.deck, Game.discardPile, player3.arrayOfLaidDownSets, rummy.getPlayerID());
			rummy.send(gs);
			gameOver = player3.checkForEndOfGame();
		}
		else
		{
			player4.playerTurn();
			cs342project5.GameState gs = new cs342project5.GameState(rummy.getId(), Game.deck, Game.discardPile, player4.arrayOfLaidDownSets, rummy.getPlayerID());
			rummy.send(gs);
			gameOver = player4.checkForEndOfGame();
		}
		if (gameOver)
		{
			endGame();
		}
	}
	/**
	 * When you deal the cards, you take one and turn it over -- this is the first card in the discard pile.
	 */
	private static void addFirstCardToDiscardPile()
	{
		Card card = deck.drawCardFromDeck();
		discardPile.putCardOnDiscardPile(card);
	}


}