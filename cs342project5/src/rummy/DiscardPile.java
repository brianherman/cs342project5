package rummy;

import java.util.ArrayList;

/**
 * Created by bob on 4/18/14.
 */
public class DiscardPile implements java.io.Serializable
{
    private ArrayList<Card> discardDeck;

    public ArrayList<Card> getDiscardDeck()
    {
        return discardDeck;
    }

    public DiscardPile()
    {
        discardDeck = new ArrayList<Card>();
    }

    /**
     * This function adds a card to the top of the pile
     * @param card
     */
    public void putCardOnDiscardPile(Card card)
    {
        discardDeck.add(card);
    }


    /**
     * This function draws a card from the deck
     * @return Card
     */
    public Card drawCard()
    {
        Card tempCard = discardDeck.get(discardDeck.size()-1);
        discardDeck.remove(discardDeck.size()-1);
        return tempCard;
    }

    public Card getCurrentDiscardCard()
    {
        return discardDeck.get(discardDeck.size()-1);
    }


    /**
     * For use in debugging, this method displays what is in the discard pile
     */
    public void displayDiscardPile()
    {
        int deckSize = discardDeck.size();
        System.out.println("Current discard pile:");
        for (int i = 0; i < deckSize; i++)
        {
            Card card = discardDeck.get(i);
            String cardString = card.getCardString();
            System.out.print(cardString + "\t");
            System.out.println();
        }
    }
}
