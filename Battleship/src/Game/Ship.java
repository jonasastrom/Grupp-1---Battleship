package Game;

/**
 * Handles the ships
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Ship
{
	private String name;
	private int lenght;
	private int hit;
	private boolean isVertical;
	private boolean isSunk = false;

	/**
	 * Constructor
	 * @param lenght Length of ship
	 * @param name Name of ship
	 */
	public Ship(int lenght, String name)
	{
		this.lenght = lenght;
		this.name = name;
		hit = 0;
	}

	/**
	 * Returns true if the ship is vertical
	 * false otherwise
	 */
	/*public boolean isVertical()
	{
		return true;
	}*/

	/**
	 * Rotates the ship 90 degrees
	 */
	/*public void rotate()
	{

	}*/

	/**
	 * Returns true if the ship has been sun
	 * false otherwise
	 */
	public boolean isSunk()
	{
		return isSunk;
	}

	public void hit()
	{
		if(++hit == lenght) {isSunk = true;}
	}
}
