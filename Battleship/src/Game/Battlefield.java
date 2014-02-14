package Game;

/**
 * Handles the battefield
 * @author grupp1
 *
 */
public class Battlefield {

	int [10] [10] zones;
	/**
	 * Constructor
	 */
	public Battlefield()
	{
		for (int y = 1; y < 5; y++)
		{	
			for (int x = 1; x < 5; x++)
			{
				zones [x] [y] = new zone(x,y);
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
		zones[x][y] = Zone zone;
		return zone.isBombed();
	}

	/**
	 * Sets the zone with the specified coordinates to bombed.
	 * @param int x The x coordinates of the zone to bomb
	 * @param int y The y coordinate of the zone to bomb
	 */
	public boolean setBomb(int x, int y)
	{
		zones[x][y] = Zone zone;
		zone.setBomb();
	}

	/**
	 * Returns true if the zone with the specified coordiantes has a ship
	 * False otherwise
	 * @param int x The x coordinates of the zone to check
	 * @param int y The y coordinate of the zone to check
	 */
	public boolean hasShip(int x, int y)
	{
		zones[x][y] = Zone zone;
		return zone.hasShip();
	}

	/**
	 * Gets the ship from the zone of with the specified coordinates
	 * @param int x The x coordinates of the zone to get ship from
	 * @param int y The y coordinate of the zone to get ship from
	 */
	public Ship getShip(int x, int y)
	{
		zones[x][y] = Zone zone;
		zone.getShip();

	}

	/**
	 * Sets a ship-object to a zone
	 * @param int x The x coordinates of the zone to set ship
	 * @param int y The y coordinate of the zone to set ship
	 */
	Public void  setShip(int x, int y, Ship ship)
	{
		zones[x][y] = Zone zone;
		zone.setShip(ship);
	}



}
