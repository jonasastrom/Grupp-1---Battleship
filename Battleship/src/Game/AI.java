package Game;

import java.lang.*;
import java.awt.*;
import java.util.Random;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;

	/**
	 * the constructor of this class
	 */
	public AI(int difficulties) {
		this.difficulties = difficulties;
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
}
