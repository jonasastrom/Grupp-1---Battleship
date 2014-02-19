package Game;

import java.util.ArrayList;
import java.util.Observable;

import GUI.Gui;

/**
 * Handles a zone
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Zone extends Observable
{
	private int xPos;
	private int yPos;
	private boolean isBombed = false;
	private boolean hasShip = false;
	private Ship ship;
	private Gui ZoneGui;
	private String id;

	/**
	 * Constructor
	 */
	public Zone(int xPos, int yPos, String id)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.id = id;
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
		ArrayList<String> stringList = new ArrayList<String>();
		stringList.add("hej");
		setChanged();
		notifyObservers();
		return true;
		
		
		/*String whatBattlefield;
		if(id.equals("ai")){whatBattlefield = "left";}
		else{
			whatBattlefield = "right";}
		ZoneGui = GameEngine.getGui();
		
		isBombed = true;
		if (ship != null) {
			ship.hit();
			if (ship.isSunk()) {
				ZoneGui.updateZone(++xPos,++yPos,ZoneState.SUNK,whatBattlefield);
				return true;
			}
			else {
				ZoneGui.updateZone(++xPos,++yPos,ZoneState.HIT,whatBattlefield);
				return true;
			}
		}
		else {
			ZoneGui.updateZone(++xPos,++yPos,ZoneState.MISS,id);
			return false;
		}*/
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