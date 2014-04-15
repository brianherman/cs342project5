package cs342project5;

public class Card implements Comparable<Card>{
	private int suit;
	private int rank;
	char suit2char[]={' ', 'C','D','H','S'}; 
	char rank2char[]={' ', ' ','A','2','3','4','5','6','7','8','9','T','J','Q','K'};
	/**
	 * Generates a new card 
	 * @param r, rank (1-13)
	 * @param s, suit (1-4)
	 * @throws InvalidCardException
	 */
	public Card(int r, int s) throws InvalidCardException
	{
		if(check(r,s) == false)
		{
			System.err.println("r:" + r +" s:" +s);
			throw new InvalidCardException();
			
		}
		suit = s;
		rank = r;
	}
	/**
	 * Checks if a card is in the right range 1-4 for suit 1-13 for rank
	 * @param r
	 * @param s
	 * @return
	 */
	private boolean check(int r, int s) 
	{
		boolean suitCheck = false;
		boolean rankCheck = false;
		if(r >= 2 && r<=15){
			suitCheck = true;
		}
		if(s>=1 && s<=4 )
		{
			rankCheck = true;
		}
		return (suitCheck && rankCheck);
	}
	/**
	 * Returns the suit.
	 * @return int
	 */
	public int getSuit()
	{
		return suit;
	}
	/**
	 * Returns the rank
	 * @return int
	 */
	public int getRank()
	{
		return rank;
	}
	@Override
	public boolean equals(Object o)
	{
		 if (!(o instanceof Card)) {
			    return false;
		}
		Card c = (Card)o;
		return (rank == c.getRank()) && (suit == c.getSuit());
	}
	@Override
	public String toString(){
		return   rank2char[rank] +" " + suit2char[suit];
	}
	@Override
	public int compareTo(Card c) {
		if(getSuit()==c.getSuit())
	    	{
	    		if(getRank() < c.getRank())
	    			return -1;
	    		else if(getRank() > c.getRank() )
	    			return 1;
	    		else
	    			return 0;
	    	}else{
	    		
			if(getSuit() < c.getSuit())
				return -1;
			else if(getSuit() > c.getSuit() )
				return 1;
			else
				return 0;
	    }
	}
}
