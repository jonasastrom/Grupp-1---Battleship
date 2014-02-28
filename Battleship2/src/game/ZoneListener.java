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
	public void update(int x, int y, String side, String state)
	{
		setChanged();
		notifyObservers(new ZoneLink(x,y,side,state));
	}
}
