package Game;

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
	private String id;
	private ZoneListener zoneListener;
	private String side;

	/**
	 * Constructor
	 */
	public Zone(int xPos, int yPos, String id, ZoneListener zoneListener)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = id;
		this.zoneListener = zoneListener;
		if(id.equals("ai")){side = "right";}
		else {side = "left";}
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
	 * 
	 */
	public boolean setBomb()
	{
		System.out.println("innan");

		if(ship!=null){ship.hit();}

		if(hasShip)
		{
			zoneListener.update(xPos, yPos, side, "hit");
			isBombed = true;
			System.out.println("andra");
			return isBombed;

		}


		else {
			System.out.println("else");
			zoneListener.update(xPos, yPos, side, "miss");
			System.out.println("tredje");
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
		hasShip = true;
	}

	public void setSunk()
	{
		zoneListener.update(xPos, yPos, side, "sunk");
	}
}