package Game;

import GUI.Gui;
import Game.GameEngine.ZoneState;

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
	private int status;
	private boolean isBombed = false;
	private boolean hasShip = false;
	private Ship ship;

	/**
	 * Constructor
	 */
	public Zone(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
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
		isBombed = true;
		if (ship != null) {
			ship.hit();
			if (ship.isSunk()) {
				Gui.updateZone(++xPos,++yPos,);
				return true;
			}
			else {
				GUI.Gui.updateZone(++xPos,++yPos,ZoneState.HIT);
				return true;
			}
		}
		else {
			GUI.Gui.updateZone(++xPos,++yPos,ZoneState.MISS);
			return false;
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
	}
}