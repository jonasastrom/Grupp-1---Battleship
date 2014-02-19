package Game;

/**
 * Handles the battlefield for a player
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Battlefield
{
	private Zone[][] zones;
	private String id;

	/**
	 * Construct the battlefield with 100 zones
	 */
	public Battlefield(String id)
	{
		this.id = id;
		zones = new Zone[10][10];

		for (int y = 0; y < 10; y++) {	
			for (int x = 0; x < 10; x++) {
				zones[x][y] = new Zone(x,y,id);
			}
		}
	}

	/**
	 * Check if the zone with the specified
	 * coordinates has been bombed
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return true if the zone has been bombed,
	 *         false otherwise
	 */
	public boolean isBombed(int x, int y)
	{
		return zones[x][y].isBombed();
	}

	/**
	 * Bomb the zone with the specified coordinates
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return true if a ship was hit,
	 *         false otherwise
	 */
	public boolean setBomb(int x, int y)
	{
		return zones[x][y].setBomb();
	}

	/**
	 * Check if the zone with the specified
	 * coordinates is occupied by a ship
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return true if a ship occupies the zone
	 *         false if the zone is free
	 */
	public boolean hasShip(int x, int y)
	{
		Zone zoneTMP = zones[x][y];
		return zoneTMP.hasShip();
	}

	/**
	 * Return the ship at the specified coordinates
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return Ship or null if there is no ship
	 */
	public Ship getShip(int x, int y)
	{
		return zones[x][y].getShip();
	}

	/**
	 * Place a ship at the specified coordinates
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @param ship The ship occupying the zone
	 */
	public void setShip(int x, int y, Ship ship)
	{
		zones[x][y].setShip(ship);
	}
}
