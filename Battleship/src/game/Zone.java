package game;

/**
 * Handles a single zone.
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Zone
{
	private int xPos;
	private int yPos;
	private String id;
	private ZoneListener zoneListener;
	private String side;
	private Ship ship;
	private boolean isBombed = false;
	private boolean hasShip = false;

	/**
	 * Construct a single zone.
	 * @param xPos The x-coordinate of the zone.
	 * @param yPos The y-coordinate of the zone.
	 * @param id The id of the owner of the zone-object.
	 * @param zonListener The ZoneListener object that will be
	 *	  used to update the GUI.
	 */
	public Zone(int xPos, int yPos, String id, ZoneListener zoneListener)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = id;
		this.zoneListener = zoneListener;

		if (id.equals("ai")) side = "left";
		else if (id.equals("human")) side = "right";
	}

	/**
	 * Get the x-coordinate of this zone.
	 * @return The x-coordinate
	 */
	public int getX()
	{
		return xPos;
	}

	/**
	 * Get the y-coordinate of this zone.
	 * @return The y-coordinate
	 */
	public int getY()
	{
		return yPos;
	}

	/**
	 * Check if this zone has been bombed.
	 * @return true if this zone has been bombed,
	 *         false otherwise
	 */
	public boolean isBombed()
	{
		return isBombed;
	}

	/**
	 * Bomb this zone.
	 * @return true if a ship was hit,
	 *         false if it was a miss
	 */
	public boolean setBomb()
	{
		if (hasShip) {
			ship.hit();
			zoneListener.update(xPos, yPos, side, "hit");
			isBombed = true;
			return true;
		} else {
			zoneListener.update(xPos, yPos, side, "miss");
			return false;
		}
	}

	/**
	 * Check if this zone is occupied by a ship.
	 * @return true if a ship occupies this zone,
	 *         false if this zone is free
	 */
	public boolean hasShip()
	{
		return hasShip;
	}

	/**
	 * Get the ship occupying this zone.
	 * @return Ship or null if there is no ship
	 */
	public Ship getShip()
	{
		return ship;
	}

	/**
	 * Place a ship in this zone.
	 * @param ship The ship occupying the zone
	 */
	public void setShip(Ship ship)
	{
		this.ship = ship;

		if (id.equals("human"))
			zoneListener.update(xPos,yPos,side,"ship");

		hasShip = true;
	}

	/**
	 * Sink the ship occupying this zone.
	 */
	public void setSunk()
	{
		if (hasShip)
			zoneListener.update(xPos,yPos,side,"sunk");
	}	

	/**
	 * Show the ship occupying this zone.
	 */
	public void showShip()
	{
		if (hasShip && !isBombed)
			zoneListener.update(xPos,yPos,side,"ship");
	}

	/**
	 * Clear this zone in the GUI.
	 */
	public void setClear()
	{
		zoneListener.update(xPos,yPos,side,"sea");
	}
}
