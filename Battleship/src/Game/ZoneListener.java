package Game;

import java.util.Observable;

public class ZoneListener extends Observable
{

	public void update(int x){
		setChanged();
		notifyObservers(new ZoneLink(x));
	}
}
