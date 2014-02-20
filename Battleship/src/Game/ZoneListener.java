package Game;

import java.util.Observable;

public class ZoneListener extends Observable
{

	public void update(int x, int y, String leftRight, String state){
		setChanged();
		notifyObservers(new ZoneLink(x,y,leftRight, state));
	}
}
