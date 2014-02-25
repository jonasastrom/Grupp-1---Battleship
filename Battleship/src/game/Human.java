package game;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Handles the human player
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class Human extends Player
{
	private Battlefield battlefield;
	private Fleet fleet;

	/**
	 * Construct the human player
	 */
	public Human(ZoneListener zoneListener)
	{
		super("human", zoneListener);
		battlefield = getBattlefield();
		fleet = getFleet();
	}

	/**
	 * Called by the GameEngine when
	 * this player should place it's ships
	 */
	public void placeShips()
	{
		while (!fleet.isPlaced()) {
			// Sleep for 1 second
			try { Thread.sleep(1000); }
			catch (InterruptedException e) {}
		}
	}

	/**
	 * Called by the GUI when a ship has been
	 * placed on the battlefield
	 * @param name The name of the ship
	 * @param x An array with all x-coordinates
	 * @param y An array with all y-coordinates
	 * @return true if the placement is valid,
	 *         false otherwise
	 */
	public boolean placeShip(String name, int[] x, int[] y)
	{
		boolean error = false;
		int length;
		Iterator<Ship> it = fleet.getShips().iterator();
		Ship ship = null;

		while (it.hasNext()) {
			ship = it.next();
			if (ship.getName().equals(name)) break;
		}
		length = ship.getLength();

		/*
		 * The provided coordinates doesn't 
		 * match the length of the ship
		 */
		if (x.length != length || y.length != length) return false;

		/*
		 * Checks if position 0 in x is equal to all the other positions
		 * If so, set xEquals to false
		 */
		boolean xEquals = true;
		for (int i = 1; i < length; i++) 
		{
			if (x[0] != x[i]) xEquals = false;
		}

		/*
		 * Checks if position 0 in y is equal to all the other positions
		 * If so, set yEquals to false 
		 */
		boolean yEquals = true;
		for (int i = 1; i < length; i++) 
		{
			if (y[0] != y[i]) yEquals = false;
		}

		if(!xEquals && !yEquals) error = true;
		
		int[] c = new int[x.length];
		
		/*
		 * Checks if xEqual is true. If it is not all the entries in x[] are 
		 * the same and the for loop is executed and fills the c[] with
		 * all the entries in x[]
		 */
		if (yEquals)
		{
			for(int a=0; a < x.length; a++)
			{
				c[a] = x[a];
			}
		}
		/*
		 * Checks if yEqual is true. If it is not all the entries in y[] are 
		 * the same and the for loop is executed and fills the c[] with
		 * all the entries in y[]
		 */
		else if (xEquals){
			for(int a=0; a < y.length; a++)
			{
				c[a] = y[a];
			}
		}

		Arrays.sort(c);

		/*
		 * Checks if c of one position incremented with one is equal to 
		 * c of the next position. If all the y-coordinates is after each other
		 * If they are not, set error = true
		 */
		for (int i = 0; i < length-1; i++) 
		{
			if (c[i]+1 != c[i+1]) error = true;
		}

		/*
		 * If error was set, go thru every coordinate and set it clear
		 * else set ship to all the coordinates
		 */
		if (error) {
			for (int i = 0; i < length; i++) 
			{
				battlefield.setClear(x[i],y[i]);
			}
			return false;
		}
		else {
			for (int i = 0; i < length; i++) 
			{
				battlefield.setShip(x[i],y[i],ship);
			}
			ship.setPlaced();
			return true;
		}
	}
}
