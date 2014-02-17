
package Game;

import java.util.*;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;
	private List<int[]> firingSolution;
	private Battlefield battlefield;

	/**
	 * the constructor of this class
	 */
	public AI(int difficulties) {
		this.difficulties = difficulties;
		firingSolution = new ArrayList<>();
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
		int xNumber = random.nextInt(10) + 1;
		int yNumber = random.nextInt(10) + 1;
		int xNewNumber = random.nextInt(10) + 1;
		int yNewNumber = random.nextInt(10) + 1;
		battlefield = null;

		System.out.println(xNumber);
		System.out.println(yNumber);

		if (isLegal(xNumber, yNumber, yNewNumber, xNewNumber))
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
	private boolean isLegal(int xNumber, int yNumber, int yNewNumber,
			int xNewNumber) {

		if (battlefield.hasShip(xNumber, yNumber)) {
			if (Math.abs(xNumber - xNewNumber) == 2 || (Math.abs(yNumber - yNewNumber) == 2)) {
				return true;
			} else if (Math.abs(xNumber - xNewNumber) == 3 || (Math.abs(yNumber - yNewNumber) == 3)) {
				return true;
			} else if (Math.abs(xNumber - xNewNumber) == 4 || (Math.abs(yNumber - yNewNumber) == 4)) {
				return true;
			} else if (Math.abs(xNumber - xNewNumber) == 3 || (Math.abs(yNumber - yNewNumber) == 5))
				return true;

		} else
			return false;

		return false;
	}

	/**
	 * AIs turn to attack
	 */
	public void attack() {

	}

	/**
	 * Create the AI-players firing solution based on the set difficulty.
	 */
	private void createFiringSolution() {
		// In the initial, stupid iteration, only randomly attack zones.
		// Do a loop to create random attacks, manually checking for conflicts?
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

}
