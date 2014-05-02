package cs342project5;

import java.util.ArrayList;

import rummy.Card;
import rummy.Deck;
import rummy.DiscardPile;
import rummy.Game;
import rummy.LaidDownSetsOfCards;
import rummy.Player;

public class GameState implements java.io.Serializable{
	private rummy.DiscardPile discardPile;
	private rummy.Deck deck;
	private ArrayList<rummy.Laydowns> arrayOfLaidDownSets = new ArrayList<rummy.Laydowns>();
	private int id;
	private int whichPlayer;
	private boolean unlockNextPlayer;
	private int turn;
	private Card discard;
	public Card discard(){
		return discard;
	}
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
	public int turn(){
		return turn;
	}
	public GameState(int i, rummy.Deck d, rummy.DiscardPile dp, ArrayList<rummy.Laydowns> lds, int w, boolean unp, int t, Card fc){
		id=i;
		deck=d;
		discardPile=dp;
		arrayOfLaidDownSets=lds;
		whichPlayer = w;
		unlockNextPlayer=unp;
		turn = t;
		discard = fc;
	}
	public boolean getUnlockNextPlayer() {
		return unlockNextPlayer;
	}

}
