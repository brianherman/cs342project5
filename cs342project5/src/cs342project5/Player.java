package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private String name;
	private ArrayList<Card> cardsInHand = new ArrayList<Card>();
	public Player(String n)
	{
		name = n;
	}
	public String name(){
		return name;
	}
	public void setCardsInHand(ArrayList<Card> c){
		cardsInHand = c;
	}
	public ArrayList<Card> getCardsInHand(){
		return cardsInHand;
	}
}
