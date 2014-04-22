package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
	private int id;
	private GameDeck discard;
	private GameDeck active;
	private ArrayList<Player> Players;
	private final int[] HowManyToDeal = { 10, 10, 7, 7 ,6, 6};
	public Game(int i){
		active = new GameDeck();
		active.generateDeck();
		discard = new GameDeck();
		Players = new ArrayList<Player>();
		id=i;
	}
	public GameDeck discard(){
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
