package cs342project5;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CardPanel extends JPanel {

	//Special thanks to http://opengameart.org/content/playing-cards-vector-png
	private ArrayList<Card> display = new ArrayList<Card>();
	private Image images[] = new Image[52];
	private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> selection = new ArrayList<Rectangle>();

	public CardPanel(ArrayList<Card> c){
		String suit[] = {"clubs","diamonds","hearts","spades" }; 
		String face[] = {"2","3","4","5","6","7","8","9","10","jack", "queen","king","ace"};
		display = c;
		try {
			int counter = 0;
			for(int j=0; j<4; j++){
				for(int i=0; i<=12; i++){
					System.out.println("/"+face[i]+"_of_"+suit[j]+".png");
					Image img = ImageIO.read(getClass().getResource("/"+face[i]+"_of_"+suit[j]+".png"));
					images[counter]=img;
					counter++;
				}
			}
			repaint();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		addMouseListener(new PanelMouseListener());
	}
	public void setCards(ArrayList<Card> c){
		display = c;
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int i = 0;
		rectangles.clear();
		for(Card c : display)
		{
			rectangles.add(new Rectangle(i*88,0,88,128));
			g.drawImage(images[(c.getSuit()-1)*12 + c.getRank()], i*88, 0, null);
			i++;
		}
		for(Rectangle r : selection){
			g.setColor(Color.GREEN);
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}
	private class PanelMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			for(Rectangle r : rectangles){
				if(r.contains(e.getX(), e.getY()))
					selection.add(r);
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {		}

		@Override
		public void mouseExited(MouseEvent e) {			}

		@Override
		public void mousePressed(MouseEvent e) {		}

		@Override
		public void mouseReleased(MouseEvent e) {		}
	}
}
