package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Deck implements Serializable{
	private ArrayList<Card> deck = new ArrayList<Card>();
	public Deck(){
		
	}
	public void generateDeck(){
		Random r = new Random();

		ArrayList<Integer> randomCard = new ArrayList<Integer>();

		for(int i=0; i<52; i++)
		{
			randomCard.add(i+1);
		}

		Collections.shuffle(randomCard,r);

		for(int i=0; i<52; i++){
			try{
				Card c = new Card(randomCard.get(i)%13+2,randomCard.get(i)%4+1);
				deck.add(c);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void add(Card c)
	{
		deck.add(c);
	}
	public void remove(Card c)
	{
		deck.remove(c);
	}
	public ArrayList<Card> deal()
	{
		ArrayList<Card> deal = new ArrayList<Card>();
		for(int i=0; i<4; i++)
		{
			deal.add(deck.get(deck.size()));
		}
		return deal;
	}
}
