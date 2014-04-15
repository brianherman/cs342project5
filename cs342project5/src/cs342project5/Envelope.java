package cs342project5;


import java.io.Serializable;
import java.util.Vector;

public class Envelope implements Serializable{
	private String message;
	private String sender;
	private Vector<String> Recipiants = new Vector<String>();
	/**
	 * Constructor for envelope.
	 * @param s, sender
	 * @param m, message
	 * @param r, recipiants.
	 */
	public Envelope(String s, String m, Vector<String> r)
	{
		sender = s;
		message = m;
		Recipiants = r;
	}
	/**
	 * returns the sender.
	 * @return
	 */
	public String sender()
	{
		return sender;
	}
	/**
	 * Returns the message.
	 * @return
	 */
	public String message()
	{
		return message;
	}
	/**
	 * Retruns the arraylist of recipiants.
	 * @return
	 */
	public Vector<String> recipiants()
	{
		return Recipiants;
	}
	@Override
	public String toString(){
		
		return sender +": "+ message + Recipiants;
	}
}
