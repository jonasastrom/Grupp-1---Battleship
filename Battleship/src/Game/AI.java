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
	 * 
	 */
	public void placeShips() {
		Battlefield battlefield = getBattlefield();
		ArrayList<Ship> array = getFleet().getShips();
		Random random = new Random();
		int counter = 0;
		int xValue;
		int yValue;

		while (counter <= 5) {
			while (true) {
				xValue = random.nextInt(10) + 1;
				yValue = random.nextInt(10) + 1;
				battlefield.hasShip(xValue, yValue); // if that

			}

			if (enoughSpace(xValue, yValue) == true) {
				battlefield.setShip(xValue, yValue, ship.getNext());
				counter++;
			}
		}
	}

	/**
	 * if the boat fits to the chosen coordinates
	 * 
	 * @param yValue
	 * @param xValue
	 * @return true else
	 * @return false
	 */
	private boolean enoughSpace(int xValue, int yValue) {

		int countSquare;

		for(countSquare = 0; countSquare < 6; countSquare++){
			if(battlefield.hasShip(xValue, yValue) == true){
				xValue++;
			}
			else if(battlefield.hasShip(xValue, yValue) == true){
				xValue--;
			}
			else if(battlefield.hasShip(xValue, yValue) == true){
				yValue++;
			}
			else if(battlefield.hasShip(xValue, yValue) == true){
				yValue--;
			}
			else
				break;
			
			return true;
		}
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
