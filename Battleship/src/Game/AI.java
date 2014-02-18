
package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;
	private List<int[]> firingSolution;
	private boolean shipsPlaced;

	/**
	 * the constructor of this class
	 */
	public AI(int difficulties) {
		super();
		this.difficulties = difficulties;
		firingSolution = new ArrayList<>();
		// Ships have not been placed yet.
		shipsPlaced = false;
	}

	@Override
	/**
	 * Placing out the ships and 
	 * it will override the Player class method placeShips() 
	 * and call the method setShip()
	 * get an array of all the list 
	 * 
	 */
	public void placeShips(Ship ship) {

		Random random = new Random();
		int xNumber = random.nextInt(10) + 1; // randomize a number from 1-10												// for the x-coordinate
		int yNumber = random.nextInt(10) + 1; // randomize number for the
												// y-coordinate
		int xNewNumber = random.nextInt(10) + 1;
		int yNewNumber = random.nextInt(10) + 1;
		battlefield = null;

		System.out.println(xNumber);
		System.out.println(yNumber);

		if (isLegal(ship))
			battlefield.setShip(xNumber, yNumber, ship);
		else
			System.out.println("Error, please try again!");
	}

	/**
	 * 
	 * @param xNumber
	 * @param yNumber
	 * @param yNewNumber
	 * @param xNewNumber
	 * @return
	 */ 
	private boolean isLegal(Ship ship) {
		Iterator<Fleet> it = Iterator<Fleet>;
		int counter = 0;
		while (it.hasNext()) {
			
		}

		return false;
		/**
		 * if (battlefield.hasShip(xNumber, yNumber)) { if (Math.abs(xNumber -
		 * xNewNumber) == 2 || (Math.abs(yNumber - yNewNumber) == 2)) { return
		 * true; } else if (Math.abs(xNumber - xNewNumber) == 3 ||
		 * (Math.abs(yNumber - yNewNumber) == 3)) { return true; } else if
		 * (Math.abs(xNumber - xNewNumber) == 4 || (Math.abs(yNumber -
		 * yNewNumber) == 4)) { return true; } else if (Math.abs(xNumber -
		 * xNewNumber) == 3 || (Math.abs(yNumber - yNewNumber) == 5)) return
		 * true;
		 * 
		 * } else return false;
		 */
	}

	/**
	 * AIs turn to attack
	 * @param lastHit Wether the last attack was a hit or a miss
	 * @return a two-position int containing X- and Y-coordinates to hit
	 */
	public int[] attack(boolean lastHit) {
		/**
		 * TODO
		 * Attack the player using the prepared list of random zones to hit. 
		 * Give the AI the option of seeking out hit ships.
		 */
		// For 1.0
		if (difficulties == 1)
			
		return null;
	}

	/**
	 * Create the AI-players firing solution based on the set difficulty.
	 */
	private void createFiringSolution() {
		// In the initial, stupid iteration, only randomly attack zones.
		for (int x = 1; x <= 10; x++) {
			for (int y = 1; y <= 10; y++) {
				int pos[] = new int[2]; 
				pos[0] = x;
				pos[1] = y;
				firingSolution.add(pos);
			}
		}
		// Randomize the hitlist
		Collections.shuffle(firingSolution, new Random());
	}
	
	/**
	 * @return Wether the AI has placed its ships or not.
	 */
	public boolean isShipsPlaced() {
		return shipsPlaced;
	}

}
