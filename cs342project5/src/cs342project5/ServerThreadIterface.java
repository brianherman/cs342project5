package cs342project5;


import java.util.Vector;
/**
 * Allows for a callback so each server thread can call the main server class.
 * @author brianherman
 *
 */
public interface ServerThreadIterface {
	public void send(Envelope m);
	public void send(GameFrame g);
	public Vector<String> getUsers();
	public Vector<GameFrame> getGame();
	public void addGame(GameFrame g);
	public void remove(String user);
	public void log(String log);
}
