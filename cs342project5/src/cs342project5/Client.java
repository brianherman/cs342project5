package cs342project5;


import java.net.*;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

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
	private JMenuItem leave;
	private JMenu gameMenu;
	private JMenuItem joinGame;
	private JMenuItem newGame;
	private GameFrame game;
	private String name;
	
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
		chat.setEditable(false);
		message = new JTextField();
		ClientActionListener CAL = new ClientActionListener();
		message.addActionListener(CAL);
		
		menuBar = new JMenuBar();
		file = new JMenu("File");
		connect = new JMenuItem("Connect");
		
		leave = new JMenuItem("Leave");
		quit = new JMenuItem("Quit");
		gameMenu = new JMenu("Game");
		joinGame = new JMenuItem("Join Game");
		newGame = new JMenuItem("New Game");
		
		leave.addActionListener(CAL);
		connect.addActionListener(CAL);
		quit.addActionListener(CAL);
		joinGame.addActionListener(CAL);
		newGame.addActionListener(CAL);
		
		file.add(connect);
		file.add(leave);
		file.add(quit);

		gameMenu.add(newGame);
		gameMenu.add(joinGame);

		
		menuBar.add(file);
		menuBar.add(gameMenu);
		
		setJMenuBar(menuBar);
		
		add(chat, BorderLayout.CENTER);
		add(listScroller,BorderLayout.EAST);
		add(message, BorderLayout.SOUTH);

		setSize(640,480);
		addWindowListener(new ClientWindowListener());
		setVisible(true);
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
			}

			if(joinGame == e.getSource()){
				String s = (String)JOptionPane.showInputDialog(
				                    null,
				                    "Enter the game you want to join.",
				                    "Join Game",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    null,
				                    "0");
				if(s == null)
				{
					return;
				}
				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) {
					Vector<String> recipiants = new Vector<String>();
					recipiants.add("Server");
					send(new Envelope(name, "JoinGame. "+ s  , recipiants));
				}
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
	public void send(GameFrame g){
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
	public synchronized void updateGame(GameFrame g)
	{
		game = g;
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
			GameFrame g = null;
			try {
				Object o = null;
				/*
				 * Loop to check for incoming messages.
				 */
				while((o=in.readObject()) != null)
				{
					if(o instanceof Envelope){
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
						//Print out the message.
						chat.setText(chat.getText() + e.sender() + ": "+ e.message() +"\n");
					}else if(o instanceof GameFrame){
						g = (GameFrame) g;
						updateGame(g);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}

	}
	private class ClientWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			leave();
			close();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			
		}
		
	}
}
