package game;

import java.util.ArrayList;

/**
 * Handles all ships of one player as a unit.
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Fleet
{
	private ArrayList<Ship> ships;

	/**
	 * Construct all ships for one player.
	 */
	public Fleet()
	{
		ships = new ArrayList<>();
		ships.add(new Ship(5,"carrier"));
		ships.add(new Ship(4,"battleship"));
		ships.add(new Ship(3,"submarine"));
		ships.add(new Ship(3,"cruiser"));
		ships.add(new Ship(2,"destroyer"));
	}

	/**
	 * Get a list with all ships.
	 * @return The list
	 */
	public ArrayList<Ship> getShips()
	{
		return ships;
	}

	/**
	 * Check if the whole fleet is placed.
	 * @return true if all ships have been placed,
	 *         false otherwise
	 */
	public boolean isPlaced()
	{
		for (Ship ship : ships) {
			if (!ship.isPlaced()) return false;
		}
		return true;
	}

	/**
	 * Check if the whole fleet is sunk.
	 * @return true if all ships have been sunk,
	 *         false otherwise
	 */
	public boolean isSunk()
	{
		for (Ship ship : ships) {
			if (!ship.isSunk()) return false;
		}
		return true;
	}
}
