package game;

import game.GameEngine.LastShot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ListIterator;

/**
 * class AI
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class AI extends Player {
	int difficulties;
	// Random list of attack-coords
	private List<int[]> firingSolution;
	private ArrayList<int[]> cheat;
	private ArrayList<int[]> neighbours;
	// List of coordinates to attack while searching
	private List<int[]> huntSolution;
	private boolean shipsPlaced;
	
	// Tells us that we are searching for the boat we hit last time
	private boolean search;
	
	private Battlefield opponent;
	
	// The last coordinates this AI attacked
	private int[] lastAttack;

	private boolean DEBUG = true;
	private boolean DEBUG_PLACE = true;
	
	/**
	 * the constructor of this class
	 * 
	 * @param battlefield
	 */
	public AI(int difficulties,Battlefield opponent, ZoneListener listener) {
		super("ai", listener);
		this.difficulties = difficulties;
		// This is new as of 2014-02-20, Vickie needs to give us te players
		// battlefield.
		this.opponent = opponent;
		firingSolution = new ArrayList<>();
		createFiringSolution();

		cheat = new ArrayList<>();
		neighbours = new ArrayList<>();

		// Create the last attacked coordinate and set it to something innocuous
		lastAttack = new int[2];
		lastAttack[0] = -1;
		lastAttack[1] = -1;
		// Ships have not been placed yet.
		shipsPlaced = false;
		search = false;
	}

	//@Override
	/**
	 * Placing out the ships and 
	 * it will override the Player class method placeShips() 
	 * and call the method setShip()
	 * 
	 */
	public void placeShips() {
		if (DEBUG)
			if (DEBUG_PLACE)
				placeShipsProposed(); // remember to change the name for the final version
			else
				placeShipsHardcoded(); //to debug if needed
	}
	
	/**
	 * Used for debugging
	 */
	public void placeShipsHardcoded() {
		Iterator<Ship> iter = getFleet().getShips().iterator();
		int x = 0;
		while (iter.hasNext()) {
			Ship boat = iter.next();
			
				for (int y = 0; y < boat.getLength(); y++) {
					getBattlefield().setShip(x, y, boat);
				}
			// Go to next row
			x++;
		}
	}
	
	/**
	 * Proposed method for randomly placing ships
	 * Work in progress
	 */
	public void placeShipsProposed() {
		Random rng = new Random();
		
		// Create a list of directions that will be randomized later
		ArrayList<String> directions = new ArrayList<>();
		directions.add("down");
		directions.add("up");
		directions.add("left");
		directions.add("right");
		
		// Get a list of all the ships to place
		Iterator<Ship> ships = getFleet().getShips().iterator();
		
		while (ships.hasNext()) {
			Ship boat = ships.next();
			
			// We have not yet placed this ship
			boolean placingShip = true;
			
			// Loop while we are placing the ship, only break loop when we 
			// have found a place to set it down
			while (placingShip) {
				// For looping, assume the head-coordinate for the ship is taken
				boolean headTaken = true;
				// Make sure coordinates are inited before continuing
				int xCoord = 0;
				int yCoord = 0;
				
				// Test to see if starting coordinates are taken
				while (headTaken) {
					xCoord = rng.nextInt(10);
					yCoord = rng.nextInt(10);
					headTaken = getBattlefield().hasShip(xCoord, yCoord);
				}
				
				// Randomize the list of directions and then take out a 
				// new iterator from it
				Collections.shuffle(directions, rng);
				Iterator<String> dirIter = directions.iterator();
				
				while (dirIter.hasNext() && placingShip) {
					// Grab the next direction to test
					String direction = dirIter.next();
					
					switch (direction) {
					case "down":
						// Do stuff to test if the chosen direction is open
						if (southSpace(xCoord, yCoord, boat)) {
							// Then actually place the ship in that direction
							for (int i = 0; i < boat.getLength(); i++) {
								// Place ship at yCoord + loop-control to place
								getBattlefield().setShip(xCoord,
														 yCoord + i, boat);
							}
							// Ship has been placed, set placingShip accordingly
							placingShip = false;
						}
						break;
					case "up":
						if (northSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord, 
														 yCoord - i, boat);
							}
							placingShip = false;
						}
						break;
					case "left":
						if (westSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord - i,
														 yCoord, boat);
							}
							placingShip = false;
						}
						break;
					case "right":
						if (eastSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord + i,
														 yCoord, boat);
							}
							placingShip = false;
						}
						break;
					default:
						System.out.println("PlaceShipsProposed has just "
								+ "failed completely and didn't try to place "
								+ "a ship in any direction.");
					}
				}
			}
		}
	}

	/**
	 * Checks if the zones right to the start-zone are free
	 * 
	 * @param yValue
	 *            x-coordinate of the start-zone
	 * @param xValue
	 *            y-coordinate of the start-zone
	 * @param ship
	 *            The ship to use to get the length
	 * @return true if the zones are not taken
	 */
	private boolean eastSpace(int xValue, int yValue, Ship ship) {
		// Checks if the ship actually will fit inside the field
		if ((ship.getLength() + xValue - 1) > 9)
			return false;
		// Loop-checks if the zones already has a boat, if so return false
		for (int i = 0; i < ship.getLength(); i++) {
			if (getBattlefield().hasShip(xValue + i, yValue))
				return false;
		}
		return true;
	}

	/**
	 * Checks if the zones below to the start-zone are free
	 * 
	 * @param yValue
	 *            x-coordinate of the start-zone
	 * @param xValue
	 *            y-coordinate of the start-zone
	 * @param ship
	 *            The ship to use to get the length
	 * @return true if the zones are not taken
	 */
	private boolean southSpace(int xValue, int yValue, Ship ship) {
		// Checks if the ship actually will fit inside the field
		if ((ship.getLength() + yValue - 1) > 9)
			return false;
		// Checks if the zones already has a boat
		for (int i = 0; i < ship.getLength(); i++) {
			if (getBattlefield().hasShip(xValue, yValue + i))
				return false;
		}
		return true;
	}

	/**
	 * Checks if the zones left to the start-zone are free
	 * 
	 * @param yValue
	 *            x-coordinate of the start-zone
	 * @param xValue
	 *            y-coordinate of the start-zone
	 * @param ship
	 *            The ship to use to get the length
	 * @return true if the zones are not taken
	 */
	private boolean westSpace(int xValue, int yValue, Ship ship) {
		// Checks if the ship actually will fit inside the field
		if ((xValue - ship.getLength() + 1) < 0)
			return false;
		// Checks if the zones already has a boat
		for (int i = 0; i < ship.getLength(); i++) {
			if (getBattlefield().hasShip(xValue - i, yValue))
				return false;
		}
		return true;
	}

	/**
	 * Checks if the zones above to the start-zone are free
	 * 
	 * @param yValue
	 *            x-coordinate of the start-zone
	 * @param xValue
	 *            y-coordinate of the start-zone
	 * @param ship
	 *            The ship to use to get the length
	 * @return true if the zones are not taken
	 */
	private boolean northSpace(int xValue, int yValue, Ship ship) {
		int shipLength = ship.getLength();
		// Checks if the ship actually will fit inside the field
		if ((yValue - shipLength + 1) < 0)
			return false;
		// Checks if the zones already has a boat
		for (int i = 0; i < shipLength; i++) {
			if (getBattlefield().hasShip(xValue, yValue - i))
				return false;
		}
		return true;
	}

	/**
	 * AIs turn to attack
	 * 
	 * @param lastShot An enum telling us what happened on the last shot
	 * @return a two-position int containing X- and Y-coordinates to hit
	 */
	public int[] attack(LastShot lastShot) {
		int[] target = new int[2];
		// Has this changed? Is 5 still insane or is that 4 now?
		if (difficulties < 4) {
			if (difficulties == 1) {
				target = randomAttack();
			} else if (difficulties == 2 || difficulties == 3) {
				// First of all, if this is difficulty 3, we have to decide if 
				// we're doing a cheat attack or following the pattern from 
				// difficulty 2
				// Check to see if last attack resulted in a hit
				// Go to hunt if that is the case, else do random attack
				if (lastShot == LastShot.HIT) {
					// do the stuff for hunting attack
					target = huntingAttack(true);
				} else {
					// New random attack
					target = randomAttack();
				}
			}
		} else if (difficulties == 4) {
			// Will only shoot where a hit is guaranteed. This looks as if it 
			// will require access to opponents battlefield.
			Iterator<int[]> it = cheat.iterator(); //iterator for the created list
			if(it.hasNext()){				//if there is a next coordinate shoot on it.
				target = it.next();
				cheat.remove(it);			//don't forget to remove the used coordinate
			}
		} else {
			// Gameengine should never do anything with these, but we may end 
			// up here if gameengine calls attack during difficulty 0
			target[0] = 0;
			target[1] = 0;
		}
		
		// Retain these attack coordinates for reference next turn
		lastAttack = target;
			
		return target;
	}
	
	/**
	 * Pops a random attack from firingSolution and returns it
	 * @return the next random coordinate to attack
	 */
	private int[] randomAttack() {
		int[] tempCoord = new int[2];
		if (firingSolution.size() > 0) {
			Iterator<int[]> hits = firingSolution.iterator();
			tempCoord = hits.next();
			firingSolution.remove(tempCoord);
		} else {
			// We shouldn't ever end up here, but let's set something up just
			// in case.
			tempCoord[0] = 0;
			tempCoord[1] = 0;
		}
		return tempCoord;
	}
	
	/**
	 * A method to handle attacks when we are searching for a boat we already 
	 * hit once. If we do not have a list of zones to try, this method will
	 * @return
	 */
	private int[] huntingAttack(boolean lastHit) {
		int[] attackCoord = new int[2];

		// If we aren't in the middle of a hunt, check to see if we should
		// create the list of hunting coordinates
		if (!search) {
			if (lastHit)	{
				// Create the list of coordinates to attack around the last 
				// hit we made, set searching to true and return the first 
				// hunting coordinate
				huntSolution = lookForNeighbour(lastAttack[0], lastAttack[1]);
				search = true;
			} else {
				// Return random coordinate to attack and pop that off the 
				// random list
				Iterator<int[]> iter = firingSolution.iterator();
				attackCoord = iter.next();
				firingSolution.remove(attackCoord);
				return attackCoord;
			}
		}

		if (huntSolution.size() > 1) {
			// Pop a hunt coordinate from the list and return it
			Iterator<int[]> iter = huntSolution.iterator();
			attackCoord = iter.next();
			huntSolution.remove(attackCoord);
			return attackCoord;
		} else {
			// set search to false to signal that we are out of coordinates
			// to hunt in
			// return next hunting coordinate
			Iterator<int[]> iter = huntSolution.iterator();
			attackCoord = iter.next();
			huntSolution.remove(attackCoord);
			search = false;
			return attackCoord;
		}
	}

	/**
	 * Create the AI-players firing solution based on the set difficulty.
	 */
	private void createFiringSolution() {
		// In the initial, stupid iteration, only randomly attack zones.
		// Do a loop to create random attacks, manually checking for conflicts?
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
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
	 * Create a list, where the opponent's ship is set.
	 * @return cheat
	 */
	private ArrayList<int[]> cheatList(){
		Zone[][] countZones = opponent.getZones(); // got the zones
		int[] pos = new int[2];
		pos[0] = 0;
		pos[1] = 0;
		for(int i = 0; i < 10; i++){	//look through the zone
			for(int j = 0; j<10; j++){
				if(countZones != null && countZones[i][j].hasShip()==true ){ //if something is there on that zone
					pos[0] = countZones[i][j].getX();	//take out the x coordinate from zone
					pos[1] = countZones[i][j].getY();	//take out the y coordinate from zone
					cheat.add(pos);	//add to cheat list
				}
			}
		}
		Collections.shuffle(cheat, new Random());
		return cheat;
	}
	
	/**
	 * look for the neighbor coordinates
	 * if coordinate already hit or does not exist, do not put it in list 
	 * put it in a list
	 * @param x
	 * @param y
	 * @return neighbors 
	 */
	private ArrayList<int[]> lookForNeighbour(int x, int y){
		int[] pos = new int[2];				//put the neighbors into the list
		if(x < 9){
			pos[0]=x+1;
			pos[1]= y;
			neighbours.add(pos);
		}
		if(x > 0){
			pos[0]=x-1;
			pos[1]= y;
			neighbours.add(pos);
		}
		if(y < 9){
			pos[1]=y+1;
			pos[0]= x;
			neighbours.add(pos);
		}
		if(y > 0){
			pos[0]= x;
			pos[1]=y-1;
			neighbours.add(pos);
		}
		goThroughCheat();
		return neighbours;
	}
	
	private void goThroughCheat(){
		Iterator<int[]> it = neighbours.iterator();	// create iterator here, not to confuse the iterator	
		while(it.hasNext()){						//look for dead neighbors and take them away from the list
			int[] notThere = it.next();	
			if(!firingSolution.contains(notThere) ){
				neighbours.remove(notThere);
			}
		}
	}
	
	/**
	 * @return Whether the AI has placed its ships or not.
	 */
	public boolean isShipsPlaced() {
		return shipsPlaced;
	}
}
