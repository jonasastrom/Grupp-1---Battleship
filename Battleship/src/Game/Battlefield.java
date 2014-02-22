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

	/**
	 * Construct the battlefield with 100 zones
	 */
	public Battlefield(String id, ZoneListener zoneListener)
	{
		zones = new Zone[10][10];

		for (int y = 0; y < 10; y++) {	
			for (int x = 0; x < 10; x++) {
				zones[x][y] = new Zone(x,y,id,zoneListener);
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
	 * and sink the ship if it was sunk
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return true if a ship was hit,
	 *         false otherwise
	 */
	public boolean setBomb(int x, int y)
	{	
		if (zones[x][y].setBomb()) {
			Ship ship = zones[x][y].getShip();
			if (ship.isSunk()) sinkShip(ship);
			return true;
		}
		return false;
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
		// Debug
		System.out.println("Battlefield.hasShip: "+x+","+y+" (x,y)");

		return zones[x][y].hasShip();
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
		// Debug
		System.out.println("Battlefield.setShip: "+x+","+y+" (x,y)");

		zones[x][y].setShip(ship);
	}

	/**
	 * Sink a ship on the battlefield
	 * @param ship The ship that shall be sunk
	 */
	public void sinkShip(Ship ship)
	{
		for (Zone[] array : zones) {
			for (Zone zone : array) {
				if (zone.getShip() == ship)
					zone.setSunk();
			}
		}
	}
}
