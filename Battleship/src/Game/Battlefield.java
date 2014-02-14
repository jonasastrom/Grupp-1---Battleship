package Game;

/**
 * Handles the Battlefield
 * @author grupp1  
 *
 */
public class Battlefield {

	Zone [][] zones = new Zone[10][10];
	Zone zoneTMP;
	
	/**
	 * Constructor
	 */
	public Battlefield()
	{
		for (int y = 1; y < 5; y++)
		{	
			for (int x = 1; x < 5; x++)
			{
				zones[x][y] = new Zone(x,y);
			}
		}
	}
	

	/**
	 * Returns true if the zone with the specified coordinates has been bombed
	 * False otherwise
	 * @param int x The x coordinate of the zone to check
	 * @param int y The y coordinate of the zone to check
	 */
	public boolean isBombed(int x, int y)
	{
		
		zoneTMP = zones[x][y];
		return zoneTMP.isBombed();

	}

	/**
	 * Sets the zone with the specified coordinates to bombed.
	 * @param int x The x coordinates of the zone to bomb
	 * @param int y The y coordinate of the zone to bomb
	 */
	/*public boolean setBomb(int x, int y)
	{
		zoneTMP = zones[x][y];
		zoneTMP.setBomb();
	}*/

	/**
	 * Returns true if the zone with the specified coordiantes has a ship
	 * False otherwise
	 * @param int x The x coordinates of the zone to check
	 * @param int y The y coordinate of the zone to check
	 */
	public boolean hasShip(int x, int y)
	{
		zoneTMP = zones[x][y];
		return zoneTMP.hasShip();

	}

	/**
	 * Gets the ship from the zone of with the specified coordinates
	 * Returns null is there is no ship
	 * @param int x The x coordinates of the zone to get ship from
	 * @param int y The y coordinate of the zone to get ship from
	 */
	public Ship getShip(int x, int y)
	{
		zoneTMP = zones[x][y];
		return zoneTMP.getShip();
	}

	/**
	 * Sets a ship-object to a zone
	 * @param int x The x coordinates of the zone to set ship
	 * @param int y The y coordinate of the zone to set ship
	 */
	public void setShip(int x, int y, Ship ship)
	{
		zoneTMP = zones[x][y];
		zoneTMP.setShip(ship);
	}
}
