package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Handles the human player.
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class Human extends Player
{
	private Battlefield battlefield;
	private Fleet fleet;

	/**
	 * Construct the human player.
	 */
	public Human(ZoneListener zoneListener)
	{
		super("human", zoneListener);
		battlefield = getBattlefield();
		fleet = getFleet();
	}

	/**
	 * Called by the GameEngine when this
	 * player should place it's ships.
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
	 * A debug version of placeShips,
	 * called by the GameEngine.
	 */
	public void placeShipsDebug()
	{
		ArrayList<Ship> ships = fleet.getShips();

		// carrier
		battlefield.setShip(0, 0, ships.get(0));
		battlefield.setShip(1, 0, ships.get(0));
		battlefield.setShip(2, 0, ships.get(0));
		battlefield.setShip(3, 0, ships.get(0));
		battlefield.setShip(4, 0, ships.get(0));
		ships.get(0).setPlaced();

		// battleship
		battlefield.setShip(0, 1, ships.get(1));
		battlefield.setShip(1, 1, ships.get(1));
		battlefield.setShip(2, 1, ships.get(1));
		battlefield.setShip(3, 1, ships.get(1));
		ships.get(1).setPlaced();

		// submarine
		battlefield.setShip(0, 2, ships.get(2));
		battlefield.setShip(1, 2, ships.get(2));
		battlefield.setShip(2, 2, ships.get(2));
		ships.get(2).setPlaced();

		// cruiser
		battlefield.setShip(0, 3, ships.get(3));
		battlefield.setShip(1, 3, ships.get(3));
		battlefield.setShip(2, 3, ships.get(3));
		ships.get(3).setPlaced();

		// destroyer
		battlefield.setShip(0, 4, ships.get(4));
		battlefield.setShip(1, 4, ships.get(4));
		ships.get(4).setPlaced();
	}

	/**
	 * Called by the GUI when a ship has been
	 * placed on the battlefield.
	 * @param name The name of the ship
	 * @param x An array with all x-coordinates
	 * @param y An array with all y-coordinates
	 * @return true if the placement is valid,
	 *         false otherwise
	 */
	public boolean placeShip(String name, int[] x, int[] y)
	{
		boolean error = false;

		Iterator<Ship> it = fleet.getShips().iterator();
		Ship ship = null;

		while (it.hasNext()) {
			ship = it.next();
			if (ship.getName().equals(name)) break;
		}

		int length = ship.getLength();

		if (x.length != length || y.length != length)
			throw new IllegalArgumentException(
					"The coordinates doesn't match the length of the ship");

		// Checks if position 0 in x is equal to all the
		// other positions If so, set xEquals to false
		boolean xEquals = true;
		for (int i = 1; i < length; i++) {
			if (x[0] != x[i]) xEquals = false;
		}

		// Checks if position 0 in y is equal to all the
		// other positions If so, set yEquals to false 
		boolean yEquals = true;
		for (int i = 1; i < length; i++) {
			if (y[0] != y[i]) yEquals = false;
		}

		if (!xEquals && !yEquals) error = true;

		int[] c = new int[length];

		// Checks if xEqual is true. If it is not all
		// the entries in x[] are the same and the for
		// loop is executed and fills the c[] with
		// all the entries in x[]
		if (yEquals) {
			for (int i = 0; i < length; i++) {
				c[i] = x[i];
			}
		}

		// Checks if yEqual is true. If it is not all
		// the entries in y[] are the same and the for
		// loop is executed and fills the c[] with
		// all the entries in y[]
		else if (xEquals) {
			for (int i = 0; i < length; i++) {
				c[i] = y[i];
			}
		}

		// Sort the coordinates in numerical order
		Arrays.sort(c);

		// Checks if c of one position incremented with
		// one is equal to c of the next position.
		// If all the y-coordinates is after each
		// other If they are not, set error = true
		for (int i = 0; i < length-1; i++) {
			if (c[i]+1 != c[i+1]) error = true;
		}

		// If error was set, go thru every coordinate and
		// set it clear else set ship to all the coordinates
		if (error) {
			for (int i = 0; i < length; i++) {
				battlefield.setClear(x[i],y[i]);
			}
			return false;
		} else {
			for (int i = 0; i < length; i++) {
				battlefield.setShip(x[i],y[i],ship);
			}
			ship.setPlaced();
			return true;
		}
	}
}
