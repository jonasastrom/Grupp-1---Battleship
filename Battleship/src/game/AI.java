package game;

import game.GameEngine.LastShot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class handles all the actions the AI player can make. 
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public class AI extends Player {
	// How difficult the AI will be
	int difficulties;
	
	// Random list of attack-coords
	private List<int[]> firingSolution;
	
	// List of all zones where a ship exists
	private List<int[]> cheat;
	
	// List of coordinates to attack while searching
	private List<int[]> neighbours;
	
	// Tells us whether the AI has placed its ships or not
	private boolean shipsPlaced;

	// Reference to the opponents battlefield
	private Battlefield opponent;

	// The last coordinates this AI attacked
	private int[] lastAttack;

	/**
	 * the constructor of this class
	 * 
	 * @param difficulties The difficulty of this AI, 
	 * from 0 (easiest) to 4 (hardest)
	 * @param battlefield A reference to our opponents battlefield
	 * @param listener A listener handling changes to our own battlefield
	 */
	public AI(int difficulties, Battlefield opponent, ZoneListener listener) {
		super("ai", listener);
		this.difficulties = difficulties;
		this.opponent = opponent;
		
		// Create and fill the list of random coordinates to attack
		firingSolution = new ArrayList<>();
		createFiringSolution();

		// These lists will not be filled until later
		cheat = new ArrayList<>();
		neighbours = new ArrayList<>();

		// Create the last attacked coordinate and set it to something innocuous
		lastAttack = new int[2];
		lastAttack[0] = -1;
		lastAttack[1] = -1;
		
		// Ships have not been placed yet.
		shipsPlaced = false;
	}

	/**
	 * Placing out the ships and it will override the Player class method
	 * placeShips() and call the method setShip()
	 * 
	 * @override
	 */
	public void placeShips() {
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
								getBattlefield().setShip(xCoord, yCoord + i,
										boat);
							}
							// Ship has been placed, set placingShip accordingly
							placingShip = false;
						}
						break;
					case "up":
						if (northSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord, yCoord - i,
										boat);
							}
							placingShip = false;
						}
						break;
					case "left":
						if (westSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord - i, yCoord,
										boat);
							}
							placingShip = false;
						}
						break;
					case "right":
						if (eastSpace(xCoord, yCoord, boat)) {
							for (int i = 0; i < boat.getLength(); i++) {
								getBattlefield().setShip(xCoord + i, yCoord,
										boat);
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
	 * Checks if the zones right of the start-zone are free
	 * 
	 * @param yValue x-coordinate of the start-zone
	 * @param xValue y-coordinate of the start-zone
	 * @param ship The ship to use to get the length
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
	 * @param yValue x-coordinate of the start-zone
	 * @param xValue y-coordinate of the start-zone
	 * @param ship The ship to use to get the length
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
	 * @param yValue x-coordinate of the start-zone
	 * @param xValue y-coordinate of the start-zone
	 * @param ship The ship to use to get the length
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
	 * @param yValue x-coordinate of the start-zone
	 * @param xValue y-coordinate of the start-zone
	 * @param ship The ship to use to get the length
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
//		boolean doNotCheat = true;
		int[] target = new int[2];
		// If the list of cheating coordinates is empty, populate it.
		if(cheat.isEmpty()){
			cheatList();
		}
		
		if (difficulties < 4) {
			// If we are diff 3, random doNotCheat
			if (difficulties == 1) {
				target = randomAttack();
			} else if (difficulties == 2) {
				target = normalAttack(lastShot);
			} else if (difficulties == 3) {
				if (((new Random()).nextInt(2) > 0) && lastShot != LastShot.HIT) {
					// Do cheating attack
					target = insaneAttack();
				} else
					target = normalAttack(lastShot);
			}
		} else if (difficulties == 4) {
			// Will only shoot where a hit is guaranteed. This looks as if it
			// will require access to opponents battlefield.
			target = insaneAttack();
		} else {
			// Gameengine should never do anything with these, but we may end
			// up here if gameengine calls attack during difficulty 0
			target[0] = 0;
			target[1] = 0;
		}

		// Retain these attack coordinates for reference next turn
		lastAttack = target;
		// Remove target from both lists of coordinates
		removeFiringSolution(target);
		return target;
	}
	
	/**
	 * Give the next cheating coordinate to attack
	 * @return Coordinates to attack
	 */
	private int[] insaneAttack() {
		int[] target;
		Iterator<int[]> it = cheat.iterator();
		if (it.hasNext()) {
			// Take the next coordinate from the list
			target = it.next();
		} else {
			target = new int[2];
		}
		return target;
	}
	
	/**
	 * Handle attacking on normal difficulty
	 * 
	 * @param lastShot status of our last attack
	 * @return coordinates to attack
	 */
	private int[] normalAttack(LastShot lastShot) {
		int[] target;
		if (lastShot == LastShot.HIT) {
			lookForNeighbour(lastAttack[0], lastAttack[1]);
			Iterator<int[]> neighIt = neighbours.iterator();
			target = neighIt.next(); //better check if the iter.hasNext before going to next one in the list
			neighIt.remove();			//error appeared
		} else if (lastShot == LastShot.MISS && neighbours.size() > 0) {
			Iterator<int[]> iter = neighbours.iterator();
			target = iter.next();
			iter.remove();
		} else 
			target = randomAttack();
		return target;
	}

	/**
	 * Takes a random attack from firingSolution and returns it
	 * 
	 * @return the next random coordinate to attack
	 */
	private int[] randomAttack() {
		int[] tempCoord = new int[2];
		if (firingSolution.size() > 0) {
			Iterator<int[]> hits = firingSolution.iterator();
			tempCoord = hits.next();
		} else {
			// We shouldn't ever end up here, but let's set something up just
			// in case.
			tempCoord[0] = 0;
			tempCoord[1] = 0;
		}
		return tempCoord;
	}

	/**
	 * Create the AI-players firing solution based on the set difficulty.
	 */
	private void createFiringSolution() {
		// Create a list of all possible attacks and then randomise that list
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				int pos[] = new int[2];
				pos[0] = x;
				pos[1] = y;
				firingSolution.add(pos);
			}
		}
		// Randomise the hitlist
		Collections.shuffle(firingSolution, new Random());
	}

	/**
	 * Create a list with all of the opponents ships
	 * 
	 */
	private void cheatList() {
		// get the zones
		Zone[][] countZones = opponent.getZones(); 
		int[] pos;
		// look through the zone
		for (int i = 0; i < 10; i++) { 
			for (int j = 0; j < 10; j++) {
				// If something is in that zone
				if (countZones != null && countZones[i][j].hasShip() == true) {
					pos = new int[2];
					// take out the x coordinate from zone
					pos[0] = countZones[i][j].getX(); 
					// take out the y coordinate from zone
					pos[1] = countZones[i][j].getY(); 
					cheat.add(pos); // add to cheat list
				}
			}
		}
		Collections.shuffle(cheat, new Random());
	}

	/**
	 * look for the neighbour coordinates if coordinate already hit or does not
	 * exist, do not put it in a list
	 * 
	 * @param x
	 * @param y
	 */
	private void lookForNeighbour(int x, int y) {
		int[] pos;

		neighbours.clear();

		if (x >= 0 && x < 9) {
			pos = new int[2];
			pos[0] = x + 1;
			pos[1] = y;
			neighbours.add(pos);
		}
		if (x > 0 && x < 10) {
			pos = new int[2];
			pos[0] = x - 1;
			pos[1] = y;
			neighbours.add(pos);
		}
		if (y >= 0 && y < 9) {
			pos = new int[2];
			pos[1] = y + 1;
			pos[0] = x;
			neighbours.add(pos);
		}
		if (y > 0 && y < 10) {
			pos = new int[2];
			pos[0] = x;
			pos[1] = y - 1;
			neighbours.add(pos);
		}

		goThroughNeighbors();
		Collections.shuffle(neighbours, new Random());
	}

	/**
	 * Remove given coordinate from both the random list of coordinates and the
	 * list of cheating coordinates.
	 * 
	 * @param pos coordinates to remove from lists
	 */
	private void removeFiringSolution(int[] pos) {
		for (int i = 0; i < firingSolution.size(); i++) {
			int[] test = firingSolution.get(i);
			if (test[0] == pos[0] && test[1] == pos[1]) {
				firingSolution.remove(i);
				break;
			}
		}

		for (int i = 0; i < cheat.size(); i++) {
			int[] test = cheat.get(i);
			if (test[0] == pos[0] && test[1] == pos[1]) {
				cheat.remove(i);
				break;
			}
		}
	}

	/**
	 * remove the coordinates the AI already shot at by comparing the 
	 * coordinates between firingSolution() and neighbours()
	 * 
	 */
	private void goThroughNeighbors() {
		Iterator<int[]> test = neighbours.iterator();

		while (test.hasNext()) {
			int[] neigh = test.next();
			if (opponent.isBombed(neigh[0], neigh[1]))
				test.remove();
		}
	}

	/**
	 * @return Whether the AI has placed its ships or not.
	 */
	public boolean isShipsPlaced() {
		return shipsPlaced;
	}
}
