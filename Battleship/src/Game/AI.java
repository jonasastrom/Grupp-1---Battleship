package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ListIterator;

/**
 * 
 * @author Grupp 1
 * 
 */
public class AI extends Player {
	int difficulties;
	private List<int[]> firingSolution;
	private boolean shipsPlaced;
	private boolean search;
	private Battlefield opponent;

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
		// Ships have not been placed yet.
		shipsPlaced = false;
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
				placeShipsProposed();
			else
				placeShipsHardcoded();
		else {
			ArrayList<Ship> array = getFleet().getShips();
			Iterator<Ship> it = array.iterator();
			Random random = new Random();
			int xValue = 0;
			int yValue = 0;

			while (it.hasNext()) { // as long as there is another ship
				Ship tempShip = (Ship) it.next();
				boolean lookForShip = true;
				boolean takenSpot = true;

				while (lookForShip) {
					while (takenSpot) {
						xValue = random.nextInt(10);
						yValue = random.nextInt(10);
						System.out.println(xValue + ", " + yValue);
						takenSpot = getBattlefield().hasShip(xValue, yValue);
					}

					if (((tempShip.getLength() + xValue -1) <= 9) 
							&& eastSpace(xValue + 1, yValue, tempShip)) {
						for (int i = 0; i < tempShip.getLength(); i++){
							getBattlefield().setShip(xValue + i, yValue, tempShip);
							lookForShip = false;
						}
					} else if (((tempShip.getLength() - xValue -1) >= 0)
							&& westSpace(xValue - 1, yValue, tempShip)) {
						for (int i = 0; i < tempShip.getLength(); i++){
							getBattlefield().setShip(xValue - 1, yValue, tempShip);
							lookForShip = false;
						}
					} else if (((tempShip.getLength() + yValue -1) <= 9)
							&& southSpace(xValue, yValue + 1, tempShip)) {
						tempShip.rotate();
						for (int i = 0; i < tempShip.getLength(); i++){
							getBattlefield().setShip(xValue, yValue + 1, tempShip);
							lookForShip = false;
						}
					} else if ((tempShip.getLength() - yValue -1) >= 0
							&& northSpace(xValue, yValue - 1, tempShip)) {
						tempShip.rotate();
						for (int i = 0; i < tempShip.getLength(); i++){
							getBattlefield().setShip(xValue, yValue - 1, tempShip);
							lookForShip = false;
						}
					}
				}
			}
		}
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
	 * if the boat fits to the chosen coordinates
	 * 
	 * @param yValue
	 * @param xValue
	 * @return true else
	 * @return false
	 */
	private boolean eastSpace(int xValue, int yValue, Ship ship) {
		int shipLength = ship.getLength();

		if ((shipLength + xValue - 1) > 9)
			return false;

		for (int i = 0; i < shipLength; i++) {
			if (getBattlefield().hasShip(xValue + i, yValue))
				return false;
		}
		return true;
		
		/*int counter = 1;
		// ships length
		while (!takenNeighbour && counter < ship.getLength()) {
			System.out.println("Placing " + ship.getName());
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			xValue++;
			counter++;
		}*/
	}

	private boolean southSpace(int xValue, int yValue, Ship ship) {
		int shipLength = ship.getLength();

		if ((shipLength + yValue - 1) > 9)
			return false;
		for (int i = 0; i < shipLength; i++) {
			if (getBattlefield().hasShip(xValue, yValue + i))
				return false;
		}
		return true;
		/*
		boolean takenNeighbour = false;
		int counter = 1;
		while (!takenNeighbour && counter < ship.getLength()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			yValue++;
			counter++;
		}
		return !takenNeighbour;*/
	}

	private boolean westSpace(int xValue, int yValue, Ship ship) {
		int shipLength = ship.getLength();

		if ((xValue-shipLength + 1) < 0)
			return false;
		for (int i=0; i<shipLength; i++){
			if(getBattlefield().hasShip(xValue-i, yValue))
				return false;
		}
		return true;
		/*boolean takenNeighbour = false;
		int counter = 1;
		while (!takenNeighbour && counter < ship.getLength()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			xValue--;
			counter++;
		}
		return !takenNeighbour;*/
	}

	private boolean northSpace(int xValue, int yValue, Ship ship) {
		int shipLength = ship.getLength();

		if ((yValue-shipLength + 1) < 0)
			return false;
		for (int i=0; i<shipLength; i++){
			if(getBattlefield().hasShip(xValue, yValue-i))
				return false;
		}
		return true;
		/*boolean takenNeighbour = false;
		int counter = 1;
		while (!takenNeighbour && counter < ship.getLength()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			yValue--;
			counter++;
		}
		return !takenNeighbour;*/
	}

	/**
	 * AIs turn to attack
	 * 
	 * @param lastHit
	 * Whether the last attack was a hit or a miss
	 * @return a two-position int containing X- and Y-coordinates to hit
	 */
	public int[] attack(boolean lastHit) {
		/**
		 * 
		 * Attack the player using the prepared list of random zones to hit.
		 * Give the AI the option of seeking out hit ships.
		 */

		search = false;
		int[] target = new int[2];
		// For 1.0
		ListIterator<int[]> hits = firingSolution.listIterator();
		if (difficulties < 5) {
			if (difficulties == 1) {
				target = hits.next();
				// Remove the last square to be hit from the list permanently
				hits.remove();
			}
		} else if (difficulties == 5) {
			// Will only shoot where a hit is guaranteed. This looks as if it 
			// will require access to opponents battlefield.
		} else {
			// Gameengine should never do anything with these
			target[0] = 0;
			target[1] = 0;
		}
			
		return target;
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
	 * @return Wether the AI has placed its ships or not.
	 */
	public boolean isShipsPlaced() {
		return shipsPlaced;
	}
}
