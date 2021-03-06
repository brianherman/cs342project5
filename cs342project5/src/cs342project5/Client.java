package cs342project5;


import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import rummy.Game;

public class Client extends JFrame{

	private JList Users;
	private JTextArea chat;
	private JTextField message;
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private DefaultListModel usersModel;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem connect;
	private JMenuItem quit;
	private JMenuItem startGame;

	private JMenuItem leave;
	private JMenu gameMenu;
	private JMenuItem joinGame;
	private JMenuItem newGame;
	private cs342project5.Game game;
	private rummy.Game rummy;
	public static GameState gameState;
	private String name;
	private int playerID;

	public Client(){
		super("Client");
		setLayout(new BorderLayout());
		usersModel = new DefaultListModel();
		Users = new JList(usersModel);
		Users.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		Users.setLayoutOrientation(JList.VERTICAL);
		Users.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(Users);
		listScroller.setPreferredSize(new Dimension(80, 80));
		chat = new JTextArea();
		JScrollPane textScroller = new JScrollPane(chat);

		chat.setEditable(false);

		message = new JTextField();
		ClientActionListener CAL = new ClientActionListener();
		message.addActionListener(CAL);

		menuBar = new JMenuBar();
		file = new JMenu("File");
		connect = new JMenuItem("Connect");

		startGame = new JMenuItem("start");

		leave = new JMenuItem("Leave");
		quit = new JMenuItem("Quit");
		gameMenu = new JMenu("Game");
		joinGame = new JMenuItem("Join Game");
		newGame = new JMenuItem("New Game");

		startGame.addActionListener(CAL);
		leave.addActionListener(CAL);
		connect.addActionListener(CAL);
		quit.addActionListener(CAL);
		joinGame.addActionListener(CAL);
		newGame.addActionListener(CAL);

		file.add(connect);
		file.add(leave);
		file.add(quit);

		gameMenu.add(newGame);
		gameMenu.add(startGame);
		gameMenu.add(joinGame);


		menuBar.add(file);
		menuBar.add(gameMenu);

		setJMenuBar(menuBar);

		add(textScroller, BorderLayout.CENTER);
		add(listScroller,BorderLayout.EAST);
		add(message, BorderLayout.SOUTH);

		setSize(640,480);
		setVisible(true);
	}
	public int getId(){
		return playerID;
	}
	public void setGame(rummy.Game g){
		System.out.println("setgame called");
		rummy = g;
	}
	/**
	 * Listens for events made by the user.
	 * @author brianherman
	 *
	 */
	private class ClientActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(connect == e.getSource())
			{
				connect();
			}
			if(leave == e.getSource())
			{
				leave();
			}
			if(quit == e.getSource()){
				leave();
				close();
				System.exit(0);
			}

			if(newGame == e.getSource()){
				//If a string was returned, say so.
				Vector<String> recipiants = new Vector<String>();
				recipiants.add("Server");
				send(new Envelope(name, "Announce.", recipiants));
				//				cs342project5.Game g = new Game(game);
				//				send(game);
				//				cs342project5.GameState gs = new cs342project5.GameState(getId(), Game.deck, Game.discardPile, Game.player1.arrayOfLaidDownSets, getPlayerID());
				//				gameState = gs;
				//				send(gameState);

			}
			if(startGame == e.getSource())
			{
				rummy.startGame();
				if(Game.deck== null)
					System.err.println("DECK NULL");
				cs342project5.GameState gs = new cs342project5.GameState(getId(), rummy.deck, rummy.discardPile, rummy.player1.laydownArray, getPlayerID(), true, 0, null);
				gameState = gs;
				send(gameState);
			}
			if(joinGame == e.getSource()){

				Vector<String> recipiants = new Vector<String>();
				recipiants.add("Server");
				send(new Envelope(name, "JoinGame. 0"  , recipiants));
				rummy.joinGame(gameState);
				send(new Envelope(name, "Lock"  , recipiants));

			}
			/*
			 * If the user pressed enter in the message text box.
			 */
			if(message == e.getSource())
			{
				//Retrieve the selected users from the users JList.
				Object selectedUsers[] = Users.getSelectedValues();
				Vector<String> recipiants = new Vector<String>();
				//Add it to the recipiants.
				for(int i=0; i<selectedUsers.length; i++)
				{	
					recipiants.add((String)selectedUsers[i]);
					System.out.println("adding "+ selectedUsers[i]);
				}
				//If no users are selected send to everyone.
				if(Users.getSelectedIndex() == -1)
				{
					for(int i=0; i<usersModel.size(); i++)
					{
						recipiants.add((String)usersModel.getElementAt(i));
					}
				}
				//Send the envelope.
				Envelope ev = new Envelope(name,message.getText(),recipiants);
				try {
					out.writeObject(ev);
					out.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				message.setText("");
				Users.clearSelection();
			}
		}

	}
	public Vector<String> getRecipiants(){
		Vector<String> recipiants = new Vector<String>();
		for(int i=0; i<usersModel.size(); i++)
		{
			recipiants.add((String)usersModel.getElementAt(i));
		}
		return recipiants;
	}
	public String getName(){
		return name;
	}
	/**
	 * Connects to the server.
	 */
	public void connect(){
		//Get the server's ip address.
		String ipAddress = (String)JOptionPane.showInputDialog(
				null,
				"Enter the server's ip address:\n",
				"Customized Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"127.0.0.1");
		if(ipAddress == null)
			return;
		//Get the name of the user.
		name = (String)JOptionPane.showInputDialog(
				null,
				"Enter the username you wish to use:\n",
				"Customized Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				"brian");
		if(name == null)
			return;
		this.setTitle(name);
		try{
			//Open the socket and get the input/output streams.
			socket = new Socket(ipAddress,25565);
			out = new ObjectOutputStream(socket.getOutputStream());
			in  = new ObjectInputStream(socket.getInputStream());
			//Send inital connection envelope.
			Vector<String> recipiants = new Vector<String>();
			recipiants.add("Server");
			Envelope e = new Envelope(name, "Initial Connection.", recipiants);
			send(e);
			//Retrieve the people connected.
			e = (Envelope)in.readObject();
			for(String s : e.recipiants())
				usersModel.addElement(s);

		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Sends a leave message to the server.
	 */
	public void leave(){
		Vector<String> recipiants = new Vector<String>();
		recipiants.add("Server");
		Envelope end = new Envelope(name,"Leave.", recipiants);
		send(end);
	}
	public void send(Envelope e){
		try {
			out.writeObject(e);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public void send(cs342project5.Game g){
		try {
			out.writeObject(g);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * Starts listening for the connection.
	 */
	public void listen(){
		new Thread(new ClientThread()).start();
	}
	/**
	 * Closes the connection.
	 */
	public void close(){
		try{
			if(out != null || in != null)
			{
				out.close();
				in.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void send(GameState gs) {
		//rummy.update(gs);
		//playerID=gs.whichPlayer();
		try {
			out.writeObject(gs);
			out.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * This thread listens for incoming messages.
	 * @author brianherman
	 *
	 */
	private class ClientThread implements Runnable {
		@Override
		public void run() {
			Envelope e = null;
			try {
				Object o = null;
				/*
				 * Loop to check for incoming messages.
				 */
				while((o=in.readObject()) != null)
				{ 
					if(o instanceof GameState){
						gameState = (GameState)o;
						if(gameState.getUnlockNextPlayer()){
							rummy.update(gameState);
						}
						chat.append("gamestate recieved \n");
					}else if(o instanceof cs342project5.Game){
						game = (cs342project5.Game) o;
						chat.append("game recieved \n");
					}else if(o instanceof Envelope){
						e = (Envelope) o;
						//Special server message that adds to the user list.
						if(e.sender().equals("Server") && e.message().equals("Join."))
						{
							usersModel.removeAllElements();
							for(String s : e.recipiants())
								usersModel.addElement(s);
						}
						//Special server message that removes from the user list.
						if(e.message().equals("Leave."))
						{
							System.out.println(e.sender() + "left.");
							usersModel.removeElement(e.sender());
						}
						if(e.message().startsWith("JoinGame"))
						{
							String getNumber[] = e.message().split(" ");
							System.out.println(getNumber[0]);
							System.out.println(getNumber[1]);
							
							playerID=Integer.parseInt(getNumber[1]);
						}
						if(e.message().equals("Unlock")&& !e.sender().equals(name)){
							Game.player1.unlock();
							if(gameState != null){
								if(gameState.getUnlockNextPlayer()){
									rummy.update(gameState);
								}
							}
							//			continue;
						}
						if(e.message().equals("Lock") && !e.sender().equals(name)){
							Game.player1.lock();
							if(gameState != null){
								if(gameState.getUnlockNextPlayer()){
									rummy.update(gameState);
								}
							}
							//		continue;
						}
						//Print out the message.
						chat.setText(chat.getText() + e.sender() + ": "+ e.message() +"\n");
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}

	}
	public int getPlayerID() {
		return playerID;
	}
}
