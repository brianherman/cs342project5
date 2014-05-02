package rummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by bob on 4/18/14.
 * This is the deck -- an array of 52 cards.
 * We will approach this dynamically, so when a card is taken
 * from the deck, it really is taken from the array.
 */
public class Deck implements java.io.Serializable
{
    public ArrayList<Card> deck = new ArrayList<Card>();
    
	
    // deck constructor
    public Deck()
    {

        // create the deck
        for (int i = 1; i < 5; i++)
            for (int j = 1; j < 14; j++)
            {
                Card card = new Card();
                card.setSuit(i);
                card.setRank(j);

                String cardString;
                if      (1 == j)  cardString = "ace_of_";
                else if (2 == j)  cardString = "2_of_";
                else if (3 == j)  cardString = "3_of_";
                else if (4 == j)  cardString = "4_of_";
                else if (5 == j)  cardString = "5_of_";
                else if (6 == j)  cardString = "6_of_";
                else if (7 == j)  cardString = "7_of_";
                else if (8 == j)  cardString = "8_of_";
                else if (9 == j)  cardString = "9_of_";
                else if (10 == j) cardString = "10_of_";
                else if (11 == j) cardString = "jack_of_";
                else if (12 == j) cardString = "queen_of_";
                else              cardString = "king_of_";

                if      (1 == i) cardString = cardString.concat("clubs.png");
                else if (2 == i) cardString = cardString.concat("hearts.png");
                else if (3 == i) cardString = cardString.concat("diamonds.png");
                else             cardString = cardString.concat("spades.png");

                card.setCardString(cardString);
                deck.add(card);
            }
        // then we shuffle the deck -- made easy by Java!
        Collections.shuffle(deck);
    }
    public void setDeck(ArrayList<Card> d)
    {
    	deck=d;
    }
    /**
     * This function takes a card from the top of the deck and returns it where
     * it needs to go -- the the player's hand.
     * @return Card
     */
    public Card drawCardFromDeck()
    {
        Card tempCard = deck.remove(0); // get the top card in the deck
        System.out.println(tempCard.getCardString());             				    // remove it from the deck
        return tempCard;                // give the card to the player
    }
    public int getSize(){
    	return deck.size();
    }
    /**
     * For use in debugging, this method displays what's in the deck
     */
    public void printDeck()
    {
        System.out.println("The shuffed deck of cards:");
        int deckSize = deck.size();
        for (int i= 0; i < deckSize; i++)
        {
            Card card = deck.get(i);
            String cardString = card.getCardString();
            System.out.print((i+1) + ":\t" + cardString + "\t");
            if (0 == i%3 && 1!=i) System.out.println();
        }
        System.out.println();
    }
}
