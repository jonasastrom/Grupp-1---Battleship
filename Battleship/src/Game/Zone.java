package Game;
import GUI.Gui;

/**
 * Handles the zones of the Battlefield
 * @author grupp1
 *
 */
public class Zone {

	int xPos;
	int yPos;
	int status;
	boolean isBombed = false;
	boolean hasShip = false;
	Ship ship;
	public static enum ZoneState
	{
		MISS,HIT,SUNK,SHIP;
	}

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
	//Kompilatorn vill att updateZone skall vara static
	/*public boolean setBomb()
	{
		isBombed = true;
		if(ship!=null)
		{
			ship.hit();
			if(ship.isSunk())
			{
				Gui.updateZone(++xPos,++yPos,ZoneState.SUNK);
			}
			
			else {GUI.Gui.updateZone(++xPos,++yPos,ZoneState.HIT);}
		}
		else{GUI.Gui.updateZone(++xPos,++yPos,ZoneState.MISS);}
	}*/

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
		if(ship!= null)
		{
			return ship;
		}
		
		else return null;
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