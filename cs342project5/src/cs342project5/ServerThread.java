package cs342project5;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
/**
 * Handles a request from a client.
 * @author brianherman
 *
 */
public class ServerThread implements Runnable {
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private ServerThreadIterface callback = null;
	private String name = null;

	public ServerThread(Socket s, ServerThreadIterface stc)
	{
		socket = s;
		callback = stc;
	}
	@Override
	public void run() {
		callback.log("Connection accepted");
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());

			while(true){
				Envelope m = null;
				Game g = null;
				//read in a evenlope.
				Object o = in.readObject();
				if(o instanceof Envelope){
					m = (Envelope)o;
				}else if(o instanceof Game){
					g = (Game)o;
				}
				/*
				 * Special commands.
				 */
				if(m.recipiants().get(0).equals("Server") && m != null){
					if(m.sender() != null && m.message().equals("Initial Connection."))
					{	
						name = m.sender();
						callback.send(new Envelope("Server","Join.",callback.getUsers()));
					}
					else if(m.sender() != null && m.message().equals("Leave."))
					{
						callback.send(new Envelope(name,"Leave.",callback.getUsers()));
						close();
						return;
					}else
					if(m.sender() != null && m.message().equals("Announce.")){
						Game game2Add = new Game(callback.getGame().size());
						game2Add.addPlayer(new Player(name));
						m = new Envelope("Server", "Announce. "+ callback.getGame().size() , callback.getUsers());
						
						callback.send(m);
						callback.addGame(game2Add);
						send(game2Add);
					}else
					if(m.sender() != null && m.message().startsWith("JoinGame.")){
						String getNumber[] = m.message().split(" ");
						//callback.log(getNumber[1]);
						Vector<Game> games = callback.getGame();
						callback.log(" " + games.size());
						for(Game ga : games){
							if(ga != null)
								if( ga.id()==Integer.parseInt(getNumber[1]))
								{
									ga.addPlayer(new Player(name));
									m = new Envelope("Server", "Successful Join. " + getNumber[1],callback.getUsers());
									callback.send(ga);
									callback.send(m);
								}
						}
					}
				}
				
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String name(){
		return name;
	}
	/**
	 * Allows the server to send an envelope to this client.
	 * @param m, the message to be sent.
	 */
	public void send(Envelope m) 
	{
		try {
			callback.log(m.sender() +" : " + m.message());
			out.writeObject(m);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Allows the server to close this connection.
	 */
	public void close(){
		try{
			out.close();
			in.close();
			socket.close();
			callback.remove(name);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void send(Game g) {
		try {
			callback.log("New Gamestate Sent to game " + g.id());
			out.writeObject(g);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}