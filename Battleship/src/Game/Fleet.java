package Game;

import java.util.ArrayList;

/**
 * Handles all ships of one player as a unit
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Fleet
{
	private ArrayList<Ship> ships;

	/**
	 * Construct all ships within a list
	 */
	public Fleet()
	{
		ships = new ArrayList<>();
		ships.add(new Ship(5, "Carrier"));
		ships.add(new Ship(4, "Battleship"));
		ships.add(new Ship(3, "Submarine"));
		ships.add(new Ship(3, "Cruiser"));
		ships.add(new Ship(2, "Destroyer"));
	}

	/**
	 * Return the list with all ships 
	 */
	public ArrayList<Ship> getShips()
	{
		return ships;
	}

	/**
	 * Check if the whole fleet is sunk
	 * @return true if all ships have been sunk,
	 *         false otherwise
	 */
	public boolean isSunk()
	{
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if the whole fleet is placed
	 * @return true if all ships have been placed,
	 *         false otherwise
	 */
	public boolean isPlaced()
	{
		for (Ship ship : ships) {
			if (!ship.isPlaced()) {
				return false;
			}
		}
		return true;
	}
}
