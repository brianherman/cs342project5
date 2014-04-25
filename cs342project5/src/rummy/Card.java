package rummy;
/**
 * Created by bob on 4/18/14.
 * Pretty self explanatory -- a card class.
 */
public class Card
{
    // Ace through Kng (Ace is low card)
    private int rank;
    public int getRank() {return rank;}
    public void setRank(int rank) {this.rank = rank;}

    // the order is: 1 = Clubs, 2 = Hearts, 3 = Diamond, 4 = Spades
    private int suit;
    public int getSuit() {return suit;}
    public void setSuit(int suit) {this.suit = suit;}

    // makes it easier to identify cards
    private String cardString;
    public String getCardString() {return cardString;}
    public void setCardString(String cardString) {this.cardString = cardString;}
    
}
