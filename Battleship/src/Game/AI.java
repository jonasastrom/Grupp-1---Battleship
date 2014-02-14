package Game;

import java.util.HashSet;
import java.util.Random;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;
	private HashSet firingSolution;

	/**
	 * the constructor of this class
	 */
	public AI(int difficulties) {
		this.difficulties = difficulties;
		firingSolution = new HashSet<>();
	}

	@Override
	/**
	 * Placing out the ships and 
	 * it will override the Player class method placeShips() 
	 * it will also call the method 
	 * steff does this
	 */
	public void placeShips(int ship) {
		Random random = new Random();
		int xNumber  = random.nextInt(10) + 1;
		int yNumber  = random.nextInt(10) + 1;
		
		System.out.println(xNumber);
		System.out.println(yNumber);
		//battlefield.hasShip(xNumber, yNumber);
		//battlefield.setShip(xNumber, yNumber);
		
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
