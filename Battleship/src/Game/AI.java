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

	/**
	 * the constructor of this class
	 * 
	 * @param battlefield
	 */
	public AI(int difficulties, ZoneListener listener) {
		super("ai", listener);
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
	 * 
	 */
	public void placeShips() {
		ArrayList<Ship> array = getFleet().getShips();
		Iterator<Ship> it = array.iterator();
		Random random = new Random();
		boolean takenSpot = true;
		boolean lookForShip = true;
		int xValue = 0;
		int yValue = 0;

		while (it.hasNext()) { // as long as there is another ship
			Ship tempShip = (Ship) it.next();
			lookForShip = true;
			takenSpot = true;
			
			while (lookForShip) {
				while (takenSpot) {
					xValue = random.nextInt(10);
					yValue = random.nextInt(10);
					System.out.println(xValue + ", " + yValue);
					takenSpot = getBattlefield().hasShip(xValue, yValue);
				}

				if (((tempShip.getLenght() + xValue -1) <= 9) && eastSpace(xValue + 1, yValue, tempShip)) {
					for (int i = 0; i < tempShip.getLenght(); i++){
						getBattlefield().setShip(xValue + i, yValue, tempShip);
						lookForShip = false;
					}
				} else if (((tempShip.getLenght() - xValue -1) >= 0)
						&& westSpace(xValue - 1, yValue, tempShip)) {
					for (int i = 0; i < tempShip.getLenght(); i++){
						getBattlefield().setShip(xValue - 1, yValue, tempShip);
						lookForShip = false;
					}
				} else if (((tempShip.getLenght() + yValue -1) <= 9)
						&& southSpace(xValue, yValue + 1, tempShip)) {
					tempShip.rotate();
					for (int i = 0; i < tempShip.getLenght(); i++){
						getBattlefield().setShip(xValue, yValue + 1, tempShip);
						lookForShip = false;
					}
				} else if ((tempShip.getLenght() - yValue -1) >= 0
						&& northSpace(xValue, yValue - 1, tempShip)) {
					tempShip.rotate();
					for (int i = 0; i < tempShip.getLenght(); i++){
						getBattlefield().setShip(xValue, yValue - 1, tempShip);
						lookForShip = false;
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
		boolean takenNeighbour = false;
		int counter = 1;
		// ships length
		while (!takenNeighbour && counter < ship.getLenght()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			xValue++;
			counter++;
		}
		return !takenNeighbour;

	}

	private boolean southSpace(int xValue, int yValue, Ship ship) {
		boolean takenNeighbour = false;
		int counter = 0;
		while (!takenNeighbour && counter < ship.getLenght()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			yValue++;
			counter++;
		}
		return !takenNeighbour;
	}

	private boolean westSpace(int xValue, int yValue, Ship ship) {
		boolean takenNeighbour = false;
		int counter = 0;
		while (!takenNeighbour && counter < ship.getLenght()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			xValue--;
			counter++;
		}
		return !takenNeighbour;
	}

	private boolean northSpace(int xValue, int yValue, Ship ship) {
		boolean takenNeighbour = false;
		int counter = 0;
		while (!takenNeighbour && counter < ship.getLenght()) {
			takenNeighbour = getBattlefield().hasShip(xValue, yValue);
			yValue--;
			counter++;
		}
		return !takenNeighbour;
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
		if (difficulties < 4) {
			if (difficulties == 1)
				target = hits.next();
			// Remove the last square to be hit from the list permanently
			hits.remove();
		}
		return target;
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

	/**
	 * @return Wether the AI has placed its ships or not.
	 */
	public boolean isShipsPlaced() {
		return shipsPlaced;
	}
}
