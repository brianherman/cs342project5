package cs342project5;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame extends JFrame {
	private JList players;
	private DefaultListModel playersModel;
	private CardPanel playerDisplay;
	private CardPanel display;
	private CardPanel discard;
	private JButton PickupFromDiscard, PickupFromDeck, PlaceCards;
	private ListSelectionModel listSelectionModel;
	public GameFrame(){
		super("Game");
		setLayout(new BorderLayout());
		playersModel = new DefaultListModel();
		PickupFromDiscard = new JButton("Pickup From Discard");
		PickupFromDeck = new JButton("Pickup From Deck");
		PlaceCards = new JButton("Place Cards");
		
		players = new JList(playersModel);

		players.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		players.setLayoutOrientation(JList.VERTICAL);
		players.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(players);
		listScroller.setPreferredSize(new Dimension(80, 80));
		listSelectionModel = players.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListGameFrameListener());
		playersModel.addElement("brian");
		
		add(listScroller,BorderLayout.WEST);
		
		Deck d = new Deck();
		d.generateDeck();
		System.out.println(d.deal(10));
		playerDisplay = new CardPanel(d.deal(10));
		playerDisplay.setPreferredSize(new Dimension(128,128));
		add(playerDisplay, BorderLayout.NORTH);
		
		playersModel.addElement("brian2");
		
		d.generateDeck();
		System.out.println(d.deal(10));
		display = new CardPanel(d.deal(10));
		display.setPreferredSize(new Dimension(128,128));
		discard = new CardPanel(d.deal(5));
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		discard.setPreferredSize(new Dimension(128, 128));
		center.add(discard, BorderLayout.NORTH);
		center.add(PickupFromDiscard,BorderLayout.WEST);

		center.add(PickupFromDeck,BorderLayout.CENTER);
		
		center.add(PlaceCards, BorderLayout.EAST);
		
		add(center);
		add(display, BorderLayout.SOUTH );
		setSize(880,480);
		setVisible(true);
	}
	private class ListGameFrameListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent e) {
				Deck d = new Deck();
				d.generateDeck();
				playerDisplay.setCards(d.deal(10));
				players.getSelectedValue();
		}
		
	}
	public static void main(String args[])
	{
		GameFrame gf = new GameFrame();
		gf.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
