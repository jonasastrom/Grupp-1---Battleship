package Game;

/**
 * Handles a ship
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Ship
{
	private int length;
	private String name;
	private int health;
	private boolean isVertical = false;
	private boolean isPlaced = false;
	private boolean isSunk = false;

	/**
	 * Construct a ship
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
	 * Return the length of this ship
	 */
	public int getLenght()
	{
		return length;
	}
	
	/**
	 * Return the name of this ship
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Check the orientation of this ship
	 * @return true if this ship is vertical,
	 *         false if it is horizontal
	 */
	public boolean isVertical()
	{
		return isVertical;
	}

	/**
	 * Rotates the ship 90 degrees
	 */
	public void rotate()
	{
		isVertical = !isVertical;
	}

	/**
	 * Check if the ship has been placed
	 * @return true if this ship is placed,
	 *         false otherwise
	 */
	public boolean isPlaced()
	{
		return isPlaced;
	}
	
	/**
	 * Set this ship as placed
	 */
	public void setPlaced()
	{
		isPlaced = true;
	}

	/**
	 * Check if the ship floats
	 * @return true if the ship is sunk,
	 *         false if it still floats
	 */
	public boolean isSunk()
	{
		return isSunk;
	}

	/**
	 * Hit this ship with a bomb and
	 * decrease it's health
	 */
	public void hit()
	{
		if (--health == 0) isSunk = true;
	}
}
