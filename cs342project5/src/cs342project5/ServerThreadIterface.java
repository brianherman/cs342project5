package cs342project5;


import java.util.Vector;
/**
 * Allows for a callback so each server thread can call the main server class.
 * @author brianherman
 *
 */
public interface ServerThreadIterface {
	public void send(Envelope m);
	public void send(Game g);
	public void send(GameState g);
	public Vector<String> getUsers();
	public Vector<Game> getGame();
	public void addGame(Game g);
	public void setGameState(GameState g);
	public void remove(String user);
	public void log(String log);
	public GameState getGameStates();
	public void setTurn(int turn);
	public int getTurn();
}
