package cs342project5;

import java.util.ArrayList;

import rummy.Game;
import rummy.LaidDownSetsOfCards;

public class GameState {
	private rummy.DiscardPile discardPile;
	private rummy.Deck deck;
	private ArrayList<rummy.LaidDownSetsOfCards> arrayOfLaidDownSets = new ArrayList<rummy.LaidDownSetsOfCards>();
	private int id;

	public rummy.DiscardPile getDiscardPile() {
		return discardPile;
	}
	public rummy.Deck getDeck() {
		return deck;
	}
	public ArrayList<LaidDownSetsOfCards> getArrayOfLaidDownSets() {
		return arrayOfLaidDownSets;
	}
	public int getId() {
		return id;
	}
	public GameState(int i, rummy.Deck d, rummy.DiscardPile dp, ArrayList<rummy.LaidDownSetsOfCards> lds){
		id=i;
		deck=d;
		discardPile=dp;
		arrayOfLaidDownSets=lds;
		
	}
}
