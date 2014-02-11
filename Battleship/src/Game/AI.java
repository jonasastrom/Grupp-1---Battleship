package Game;

import java.lang.*;
import java.awt.*;

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
	public AI(int difficulties){
		this.difficulties = difficulties;
	}
	
	@Override
	/**
	 * Placing out the ships and 
	 * it will override the Player class method placeShips() 
	 * steff does this
	 */
	public void placeShips(int ship){
		
		//super.placeShips();
	}
	
	private boolean ifShips(int ship){
		
			return false;
		
	}
	
}
