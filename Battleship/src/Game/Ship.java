package Game;

public class Ship {
	String name;
	int lenght;
	boolean isVertical;
	boolean isSunk = false;
	
	/**
	 * Constructor
	 * @param name Name of ship
	 * @param lenght Lenght of ship
	 */
	Ship(String name, int lenght)
	{
		this.name = name;
		this.lenght = lenght;
	}
	
	/**
	 * Returns true if the ship is vertical
	 * false otherwise
	 */
	public boolean isVertical()
	{
		return true;
	}
	
	/**
	 * Rotates the ship 90 degrees
	 */
	public void rotate()
	{

	}

	/**
	 * Returns true if the ship has been sun
	 * false otherwise
	 */
	public boolean isSunk()
	{
		return isSunk;
	}
	
}
