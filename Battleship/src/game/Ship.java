package game;

/**
 * Handles a single ship.
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Ship
{
	private int length;
	private String name;
	private int health;
	private boolean isPlaced = false;
	private boolean isSunk = false;

	/**
	 * Construct a ship.
	 * @param length Length of this ship
	 * @param name Name of this ship
	 */
	public Ship(int length, String name)
	{
		this.length = length;
		this.name = name;
		health = length;
	}

	/**
	 * Get the length of this ship.
	 * @return The length
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Get the name of this ship.
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Check if the ship has been placed.
	 * @return true if this ship is placed,
	 *         false otherwise
	 */
	public boolean isPlaced()
	{
		return isPlaced;
	}

	/**
	 * Set this ship as placed.
	 */
	public void setPlaced()
	{
		isPlaced = true;
	}

	/**
	 * Check if the ship is sunk.
	 * @return true if the ship is sunk,
	 *         false if it still floats
	 */
	public boolean isSunk()
	{
		return isSunk;
	}

	/**
	 * Hit this ship with a bomb and
	 * decrease it's health, set it to
	 * sunk when the health reaches zero.
	 */
	public void hit()
	{
		if (--health == 0) isSunk = true;
	}
}
