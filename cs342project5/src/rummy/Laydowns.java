package rummy;

import java.io.Serializable;
import java.util.ArrayList;

public class Laydowns implements Serializable {


	private ArrayList<Card> laydown;
	private boolean run;

	Laydowns( ArrayList<Card> inlaydown, boolean inrun){

		laydown=inlaydown;
		run=inrun;
	}

	public ArrayList<Card> getLaydown(){
		return laydown;
	}

	public boolean getIfRun(){
		return run;
	}

	public void addCard(Card card){
		laydown.add(card);
	}

	public void addCardToBeginning(Card card){
		laydown.add(0, card);
	}
}
