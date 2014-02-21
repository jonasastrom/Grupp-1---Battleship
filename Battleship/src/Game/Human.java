package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Handles the human player
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Human extends Player
{
	private Fleet fleet;

	/**
	 * Construct the human player
	 */
	public Human(ZoneListener zoneListener)
	{
		super("human", zoneListener);
		fleet = getFleet();
	}

	/**
	 * TODO in 2.0
	 */
	public void placeShips()
	{
		while (!fleet.isPlaced()) {
			//wait
		}
	}
	
	/**
	 * 
	 * @param shipInfo
	 * @throws Exception
	 */
	public void placeShips(ArrayList<String> shipInfo) throws Exception
	{
		String shipName = shipInfo.get(0);
		Iterator<Ship> it = fleet.getShips().iterator();
		ArrayList<String> ourList = new ArrayList<>();
		Ship shipTMP = null;
		
		while(it.hasNext()){
			shipTMP = it.next();
			if(shipTMP.getName().equals(shipName)){	
				break;
			}
		}
		if(shipTMP == null){throw new Exception(); }
		
		int lenght = shipTMP.getLenght();
		
		for(int x = 1; x <= lenght; x++){
			ourList.add(shipInfo.get(x));
			Collections.sort(ourList);
			
		}
	}
}

