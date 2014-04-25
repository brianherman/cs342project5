package cs342project5;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	private String name;
	private rummy.Player player;
	public Player(String n)
	{
		name = n;
	}
	public String name(){
		return name;
	}
	public void setRummyPlayer(rummy.Player c){
		player = c;
	}
	public rummy.Player getRummyPlayer(){
		return player;
	}
}
