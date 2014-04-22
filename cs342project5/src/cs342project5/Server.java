package cs342project5;

import java.net.*;
import java.util.Vector;
import java.io.*;

import javax.swing.*;

public class Server extends JFrame implements ServerThreadIterface{
	private static Vector<ServerThread> threads = new Vector<ServerThread>();
	private JList log;
	private DefaultListModel logModel;
	private Vector<GameFrame> games = new Vector<GameFrame>();
	/**
	 * GUI for the server.
	 */
	public Server(){
		super("Server");
		logModel = new DefaultListModel();
		log = new JList(logModel);
		log.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		log.setLayoutOrientation(JList.VERTICAL);
		log.setVisibleRowCount(-1);

		add(log);
		setSize(800,600);
		setVisible(true);
		
		ServerSocket serverSocket = null;
		try{
			serverSocket = new ServerSocket(25565);
			log ("Server started open on Port: " +
					serverSocket.getLocalPort());
			InetAddress addr = InetAddress.getLocalHost();
			log("Local Host Name: " + addr.getHostName());
			log("Local Host Address: " + addr.getHostAddress());
			log("Listening...");
			try{
				//Listening loop.
				while(true){
					try{
						//Create a new thread and start it on each accept.
						ServerThread st = new ServerThread(serverSocket.accept(),this);
						new Thread(st).start();
						threads.add(st);
					}catch(SocketTimeoutException ste){
						System.err.println("Timeout Occured");
					}
				}
			}catch(IOException e){
				System.err.println("Accept failed.");
				System.exit(-1);
			}
		}catch(IOException e){
			System.err.println("Couldn't listen on port.");
			System.exit(-1);
		}finally{
			try{
				serverSocket.close();
			}catch(IOException e){
				System.err.println("Couldn't close port. 25565");
				System.exit(-1);
			}
		}

	}
	/** 
	 * Returns a list of users retrieved from the thread Arraylist.
	 * @see cs342project4.ServerThreadIterface#getUsers()
	 */
	public Vector<String> getUsers()
	{
		Vector<String> users = new Vector<String>();
		
		for(ServerThread st : threads)
			users.add(st.name());
		
		return users;
	}
	@Override
	public void send(Game g) {
		for(ServerThread s: threads)
		{
			for(Player p : g.getPlayers())
			{
				if(s.name().equals(p.name())){
					s.send(g);
				}
			}
		}
	}
	/**
	 * Sends a message to every recipiant in the envelope.
	 * @see cs342project4.ServerThreadIterface#send(cs342project4.Envelope)
	 */
	@Override
	public void send(Envelope m) {
		for(ServerThread s: threads)
		{
			for(String r : m.recipiants())
			{
				if(s.name().equals(r)){
					s.send(m);
				}
			}
		}
	}
	/**
	 * Removes a user from the threads arraylist.
	 */
	@Override
	public void remove(String user)
	{
		log("User "+user+" Left.");
		for(int i=0; i<threads.size(); i++){
			if(threads.get(i).name().equals(user))
			{
				threads.remove(i);
			}
		}
	}
	/**
	 * Logs a message.
	 */
	@Override
	public void log(String l)
	{
		logModel.addElement(l);
	}
	@Override
	public Vector<GameFrame> getGame() {
		return games;
	}
	@Override
	public void addGame(GameFrame g) {
		games.add(g);
	}
	
}
