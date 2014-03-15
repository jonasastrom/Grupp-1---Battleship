package game;

import java.util.Observable;

/**
 * Used to convey information between
 * a zone and the GUI.
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class ZoneListener extends Observable
{
	/**
	 * Tells the GUI that a zone on the battlefield has been updated.
	 * 
	 * @param x X-coordinate of the zone
	 * @param y Y-coordinate of the zone
	 * @param leftOrRight Which gameboard the zone belongs to, 'left' for the
	 * 			AI player, 'right' for the human player.
	 * @param state The state of the zone, 'hit', 'miss', 'sea', 'ship' 
	 * 			or 'sunk'
	 */
	public void update(int x, int y, String side, String state)
	{
		setChanged();
		notifyObservers(new ZoneLink(x,y,side,state));
	}
}
