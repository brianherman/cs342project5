package cs342project5;
import java.util.ArrayList;

/**
 * Created by bob on 4/18/14.
 * This is the class that drives the entire game.
 * We play the game, using an ASCII representation, and from
 * time to time we update the gui,
 */
public class GameFrame
{
    static GameDeck deck;
    static DiscardPile discardPile;
    static int numPlayers = 2;
    static GamePlayer player1;
    static GamePlayer player2;
    static GamePlayer player3;
    static GamePlayer player4;

    public static void main(String[] args)
    {
        boolean gameIsNotOver = true;

        // create an shuffle the deck
        deck = new GameDeck();
        deck.printDeck();
        discardPile = new DiscardPile();
        addFirstCardToDiscardPile(); // done once per game, unless we run out of cards
        deck.printDeck(); // just to make sure that one was taken off the top

        // deal the cards
        declarePlayers();
        dealTheCards();
        sortHandsSmallToLarge();
        displayHandASCII();

        // update the GUI
        for (int i = 0; i < numPlayers; i++)
        {
            // we're gonna need to send some variables along with the methods. The discard and the table
            Card discard = discardPile.getCurrentDiscardCard();
            if      (0 == i) player1.updateGui(discard);
            else if (1 == i) player2.updateGui(discard);
            else if (2 == i) player3.updateGui(discard);
            else             player4.updateGui(discard);
        }

        /*
         * Now we will actually start the player's turns.
         * Each turn will consist of all kinds of stuff.
         * I suppose that we should do this in a turn based thing,
         */

        while (gameIsNotOver)
        {
            for (int i = 0; i < numPlayers; i++)
            {
                if (0 == i)
                {
                    player1.playerTurn();
                }
                else if (1 == i)
                {
                    player2.playerTurn();
                }
                else if (2 == i)
                {
                    player3.playerTurn();
                }
                else
                {
                    player4.playerTurn();
                }
            }
            gameIsNotOver = false; // just put this in for a moment
        }

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
            player1 = new GamePlayer();
            player1.setPlayerID(1);
        }
        else if (2 == numPlayers)
        {
            player1 = new GamePlayer();
            player2 = new GamePlayer();
            player1.setPlayerID(1);
            player2.setPlayerID(2);
        }
        else if (3 == numPlayers)
        {
            player1 = new GamePlayer();
            player2 = new GamePlayer();
            player3 = new GamePlayer();
            player1.setPlayerID(1);
            player2.setPlayerID(2);
            player3.setPlayerID(3);
        }
        else if (4 == numPlayers)
        {
            player1 = new GamePlayer();
            player2 = new GamePlayer();
            player3 = new GamePlayer();
            player4 = new GamePlayer();
            player1.setPlayerID(1);
            player2.setPlayerID(2);
            player3.setPlayerID(3);
            player4.setPlayerID(4);
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

    /**
     * The physical act of dealing the cards.  This is also where we declare the
     * players, as this is the first time that we see them.
     * Note that I'm coding for up to four players, although we're only starting
     * with two.
     */
    private static void dealTheCards()
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
}
