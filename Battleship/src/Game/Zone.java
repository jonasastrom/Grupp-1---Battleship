package Game;

import java.util.ArrayList;


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

	/**
	 * Constructor
	 */
	public Zone(int xPos, int yPos, String id, ZoneListener zoneListener)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = id;
		this.zoneListener = zoneListener;
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
		String side;
		if(id.equals("ai")){side = "right";}
		else {side = "left";}
		
		System.out.println("innan");
		
		if(ship!=null){ship.hit();}
		
		if(hasShip)
		{
			System.out.println("he");
			if(ship.isSunk())
			{
				zoneListener.update(xPos, yPos, side, "sunk");
				System.out.println("forsta");
			}
			else{
				zoneListener.update(xPos, yPos, side, "hit");
				System.out.println("andra");
			}
		}
		
		
		else {
			System.out.println("else");
			zoneListener.update(xPos, yPos, side, "miss");
			System.out.println("tredje");
		}
		
		return true;
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
}