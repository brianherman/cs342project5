package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
	private int id;
	private ArrayList<Player> Players;
	private final int[] HowManyToDeal = { 10, 10, 7, 7 ,6, 6};
	public Game(int i){
		Players = new ArrayList<Player>();
		id=i;
	}
	public void addPlayer(Player p){
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
