package rummy;

import javax.swing.*;

/**
 * Created by bob on 4/22/14.
 */
public class TableButton extends JButton implements java.io.Serializable
{
    // says whether or not the button is occupied with a card
    // note that if a card in on the row, the entire row is then "occupied"
    private boolean isOccupiedButton;
    public boolean isOccupiedButton() {return isOccupiedButton;}
    public void setOccupiedButton(boolean isOccupiedButton) {this.isOccupiedButton = isOccupiedButton;}

    // each button will have a card or not (depending on it's status as occupied)
    private Card cardInButton;
    public Card getCardInButton() {return cardInButton;}
    public void setCardInButton(Card cardInButton) {this.cardInButton = cardInButton;}

    private int rowOfCard;
    public int getRowOfCard() {return rowOfCard;}
    public void setRowOfCard(int rowOfCard) {this.rowOfCard = rowOfCard;}

    private int columnOfCard;
    public int getColumnOfCard() {return columnOfCard;}
    public void setColumnOfCard(int columnOfCard) {this.columnOfCard = columnOfCard;}
}
