package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private String name;
	private Game game; 
	public Player(String n)
	{
		name = n;
	}
	public String name(){
		return name;
	}
	public void setGame(Game c){
		game = c;
	}
	public Game getGame(){
		return game;
	}
}
