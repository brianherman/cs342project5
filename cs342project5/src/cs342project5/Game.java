package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
	ArrayList<Player> Players = new ArrayList<Player>();
	int id;
	public Game(int i)
	{
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