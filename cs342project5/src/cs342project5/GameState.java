package cs342project5;

import java.util.ArrayList;

import rummy.Deck;
import rummy.DiscardPile;
import rummy.Game;
import rummy.LaidDownSetsOfCards;

public class GameState implements java.io.Serializable{
	private rummy.DiscardPile discardPile;
	private rummy.Deck deck;
	private ArrayList<rummy.Laydowns> arrayOfLaidDownSets = new ArrayList<rummy.Laydowns>();
	private int id;
	private int whichPlayer;
	public rummy.DiscardPile getDiscardPile() {
		return discardPile;
	}
	public rummy.Deck getDeck() {
		return deck;
	}
	public ArrayList<rummy.Laydowns> getArrayOfLaidDownSets() {
		return arrayOfLaidDownSets;
	}
	public int getId() {
		return id;
	}
	public int whichPlayer(){
		return whichPlayer;
	}
	public GameState(int i, rummy.Deck d, rummy.DiscardPile dp, ArrayList<rummy.Laydowns> lds, int w){
		id=i;
		deck=d;
		discardPile=dp;
		arrayOfLaidDownSets=lds;
		whichPlayer = w;
	}

}
