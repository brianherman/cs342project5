package cs342project5;


import java.util.ArrayList;
/**
 * Allows for a callback so each server thread can call the main server class.
 * @author brianherman
 *
 */
public interface ServerThreadIterface {
	public void send(Envelope m);
	public void send(Game g);
	public ArrayList<String> getUsers();
	public ArrayList<Game> getGame();
	public void addGame(Game g);
	public void remove(String user);
	public void log(String log);
}
