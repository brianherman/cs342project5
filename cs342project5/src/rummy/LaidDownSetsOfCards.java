package rummy;

import java.util.ArrayList;

/**
 * Created by bob on 4/22/14.
 */
public class LaidDownSetsOfCards implements java.io.Serializable
{
    private ArrayList<Card> laidDownSetOfCards;
    public ArrayList<Card> getLaidDownSetOfCards() {return laidDownSetOfCards;}
    public void setLaidDownSetOfCards(ArrayList<Card> laidDownSetOfCards) {this.laidDownSetOfCards = laidDownSetOfCards;}

    private boolean consecutiveCardSet;
    public boolean isConsecutiveCardSet() {return consecutiveCardSet;}
    public void setConsecutiveCardSet(boolean consecutiveCardSet) {this.consecutiveCardSet = consecutiveCardSet;}

    private boolean nOfAKindSet;
    public boolean isnOfAKindSet() {return nOfAKindSet;}
    public void setnOfAKindSet(boolean nOfAKindSet) {this.nOfAKindSet = nOfAKindSet;}

    private int sizeOfSet;
    public int getSizeOfSet() {return sizeOfSet;}
    public void setSizeOfSet(int sizeOfSet) {this.sizeOfSet = sizeOfSet;}

    private int setID;
    public int getSetID() {return setID;}
    public void setSetID(int setID) {this.setID = setID;}

    public Card getCardInLaidDownSet(int index)
    {
        return laidDownSetOfCards.get(index);
    }

    public void addCardToBeginningOfSet(Card card)
    {
        laidDownSetOfCards.add(0, card);
    }

    public void addCardToEndOfSet(Card card)
    {
        int lastElementNumber = laidDownSetOfCards.size()-1;
        laidDownSetOfCards.add(lastElementNumber, card);
    }

}
