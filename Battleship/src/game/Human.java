package game;

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
		boolean tmp = false;

		while (it.hasNext()){
			ship = it.next();
			if (ship.getName().equals(name)) break;
		}

		int length = ship.getLength();

		// Throw an exception if the provided coordinates
		// doesn't match the length of the ship
		if (x.length != length || y.length != length) {
			throw new IllegalArgumentException();
		}

		for (int i = 1; i <= length; i++) {
			if (x[0] == x[i]) {
				for (int j = 0; j <= length; j++) {
					if (y[j]+1 == y[j+1]) {
						for (int k = 0; k < 10; k++) {	
							for (int l = 0; l < 10; l++) {
								battlefield.setShip(k,l,ship);
								tmp = true;
							}
						}
					}
				}
			}
			if (y[0] == y[i]) {
				for (int j = 1; j <= length; j++) {
					if (x[j] == x[j+1]+1) {
						for (int k = 0; k < 10; k++) {	
							for (int l = 0; l < 10; l++) {
								battlefield.setShip(k,l,ship);
								tmp = true;
							}
						}
					}
				}
			}
		}
		if(tmp){return true;}
		else {return false;}
	}
}
