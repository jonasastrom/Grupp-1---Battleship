package Game;

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
	 * @return True is boat placement is valid
	 */
	public boolean placeShips(String name, int[] x, int[] y) throws Exception
	{
		Iterator<Ship> it = fleet.getShips().iterator();
		Ship shipTMP = null;

		while(it.hasNext()){
			shipTMP = it.next();
			if(shipTMP.getName().equals(name)){	
				break;
			}
		}
		if(shipTMP == null){throw new Exception(); }

		int length = shipTMP.getLength();

		if(x.length != y.length || x.length != length) throw new Exception();

		for(int i = 1; i <= length; i++){
			if(x[0] == x[i]){
				for(int j = 0; j <= length; j++){
					if(y[j]+1 == y[j+1]){
						for (int k = 0; k < 10; k++) {	
							for (int l = 0; l < 10; l++) {
								getBattlefield().setShip(k,l,shipTMP);
								return true;
							}

						}
					}
				}
			}
			if(y[0] == y[i]){
				for(int k = 1; k <= length; k++){
					if(x[k] == x[k+1]+1){
						for (int r = 0; r < 10; r++) {	
							for (int p = 0; p < 10; p++) {
								getBattlefield().setShip(r,p,shipTMP);
								return true;
							}

						}

					}
				}
			}
		}
		return false;
	}
}

