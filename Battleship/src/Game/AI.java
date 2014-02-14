package Game;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	
	private Battlefield battlefield;
	private int difficulties;
	
	private ArrayList<int[]> firingSolution;
	

	/**
	 * the constructor of this class
	 */
	public AI(int difficulties, Battlefield givenField) {
		this.difficulties = difficulties;
		this.battlefield = givenField;
		firingSolution = new ArrayList<>();
	}

	@Override
	/**
	 * Placing out the ships and 
	 * it will override the Player class method placeShips() 
	 * it will also call the method 
	 * steff does this
	 */
	public void placeShips(int ship) {
		int tempNumber = (int) (Math.random());
		char tempAlfapet; //big letters
		
		//battlefield.hasShip();
		//battlefield.setShip();
		
	}
	
	/**
	 * AIs turn to attack
	 */
	public void attack(){
		
	}
	
	/**
	 * Create the AI-players firing solution based on the set difficulty.
	 */
	private void createFiringSolution() {
		// In the initial, stupid iteration, only randomly attack zones.
//		firingSolution = pullListofZones;
	}
	
}
