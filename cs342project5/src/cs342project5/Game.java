package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
	private int id;
	private Deck discard;
	private Deck active;
	private ArrayList<Player> Players;
	private final int[] HowManyToDeal = { 10, 10, 7, 7 ,6, 6};
	public Game(int i){
		active = new Deck();
		active.generateDeck();
		discard = new Deck();
		Players = new ArrayList<Player>();
		id=i;
	}
	public Deck discard(){
		return discard;
	}
	public void addPlayer(String name){
		Player p = new Player(name);
		Players.add(p);
	}
	public void deal(){
		for(Player p : Players)
			p.setCardsInHand(active.deal(HowManyToDeal[Players.size()]));
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
