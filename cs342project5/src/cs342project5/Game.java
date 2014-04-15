package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
	private int id;
	private Deck discard;
	private Deck active;
	private ArrayList<Player> Players;
	public Game(int i){
		active = new Deck();
		active.generateDeck();
		discard = new Deck();
		Players = new ArrayList<Player>();
		id= i;
	}
	public Deck discard(){
		return discard;
	}
	public void addPlayer(String name){
		ArrayList<Card> delt = active.deal();
		Player p = new Player(name, delt);
		Players.add(p);
	}
	public Player getPlayer(String name)
	{
		for(Player p : Players)
			if(p.name().equals(name))
				return p;
		return null;
	}
	public ArrayList<Player> getPlayers(){
		return Players;
	}
	public int id()
	{
		return id;
	}
}
