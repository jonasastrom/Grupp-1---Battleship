package game;

import java.util.Observable;

/**
 * class ZoneListener
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class ZoneListener extends Observable
{

	public void update(int x, int y, String leftRight, String state){
		setChanged();
		notifyObservers(new ZoneLink(x,y,leftRight, state));
	}
}
