package game;

/**
 * Handles a zone
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Zone
{
	private int xPos;
	private int yPos;
	private boolean isBombed = false;
	private boolean hasShip = false;
	private Ship ship;
	private ZoneListener zoneListener;
	private String side;

	/**
	 * Constructor
	 */
	public Zone(int xPos, int yPos, String id, ZoneListener zoneListener)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.zoneListener = zoneListener;
		if(id.equals("ai")){side = "left";}
		else if(id.equals("human")){side = "right";}
	}

	/**
	 * Returns true if the zone has been bombed
	 * False otherwise
	 */
	public boolean isBombed()
	{
		return isBombed;
	}

	/**
	 * Set true if the zone has already been bombed
	 */
	public boolean setBomb()
	{
		if (hasShip) {
			ship.hit();
			zoneListener.update(xPos, yPos, side, "hit");
			isBombed = true;
			return isBombed;
		}
		else {
			zoneListener.update(xPos, yPos, side, "miss");
			return isBombed;
		}
	}

	/**
	 * Returns true if the zone has a ship
	 * False otherwise
	 */
	public boolean hasShip()
	{
		return hasShip;
	}

	/**
	 * Returns the Ship-object that the zone contains
	 * Returns null if the zone contains no ship
	 * @return The ship that is in the zone
	 * 
	 */
	public Ship getShip()
	{
		return ship;
	}

	/**
	 * Set a specified ship to be in the zone
	 * @param The ship that should be in the zone
	 */
	public void setShip(Ship ship)
	{
		this.ship = ship;
		if(side.equals("right")){zoneListener.update(xPos, yPos, side, "ship");}
		hasShip = true;
	}
	
	/**
	 * Sets the ship of the zone to be sinked
	 */
	public void setSunk()
	{
		zoneListener.update(xPos, yPos, side, "sunk");
	}	
	
	/**
	 * Method for showing enemy ships after game over
	 */
	public void showShip()
	{
		if(hasShip && !isBombed)
		{
			zoneListener.update(xPos, yPos, side, "ship");
		}
	}
	
	/**
	 * Clears this zone
	 */
	public void setClear()
	{
		zoneListener.update(xPos, yPos, side, "sea");
	}
}
