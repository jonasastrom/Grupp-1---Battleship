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
		Iterator<Ship> it = fleet.getShips().iterator();
		Ship ship = null;

		while (it.hasNext()){
			ship = it.next();
			if (ship.getName().equals(name)) break;
		}

		int length = ship.getLength();

		if (x.length != length || y.length != length) {
			// The provided coordinates doesn't
			// match the length of the ship
			return false;
		}

		boolean xEquals = true;
		for (int i = 1; i < length; i++) {
			if (x[0] != x[i])
				xEquals = false;
		}

		boolean yEquals = true;
		for (int i = 1; i < length; i++) {
			if (y[0] != y[i])
				yEquals = false;
		}

		if (!xEquals && !yEquals) return false;

		int[] c;
		if (xEquals) c = x;
		else c = y;

		Arrays.sort(c);

		for (int i = 0; i < length; i++) {
			if (c[i]+1 != c[i+1])
				return false;
		}

		for (int i = 0; i < length; i++) {
			battlefield.setShip(x[i],y[i],ship);
		}

		return true;
	}
}