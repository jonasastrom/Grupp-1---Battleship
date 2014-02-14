package Game;

import java.util.*;
import javax.swing.*;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;
	private ArrayList<int[]> firingSolution;

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
	 * it will also call the method setShip(),hasShips()
	 * steff does this
	 */
	public void placeShips(Ship ship) {
		
		Random random = new Random();
		int xNumber = random.nextInt(10) + 1;
		int yNumber = random.nextInt(10) + 1;
		Battlefield battlefield = null;
		
		System.out.println(xNumber);
		System.out.println(yNumber);

		if(battlefield.hasShip(xNumber, yNumber) && Math.abs(xNumber) == 2){
			battlefield.setShip(xNumber, yNumber, ship);
		}
		else if(battlefield.hasShip(xNumber, yNumber) && Math.abs(xNumber) == 3){
			battlefield.setShip(xNumber, yNumber, ship);
		}
		else if(battlefield.hasShip(xNumber, yNumber) && Math.abs(xNumber) == 4){
			battlefield.setShip(xNumber, yNumber, ship);
		}
		else if(battlefield.hasShip(xNumber, yNumber) && Math.abs(xNumber) == 5){
			battlefield.setShip(xNumber, yNumber, ship);
		}
		else
			System.out.println("Error, please try again!");
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
		// firingSolution = pullListofZones;
	}


}