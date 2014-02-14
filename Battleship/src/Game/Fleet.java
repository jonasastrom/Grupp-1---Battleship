package Game;

/**
 * class Fleet
 * 
 * @author Grupp 1 - DAT055 2014
 * @version 1.0
 */
public class Fleet
{
	private ArrayList<Ship> ships;

	/**
	 * constructor
	 */
	public Fleet()
	{
		ships = new ArrayList<>();
		ships.add(new Ship(5, "Carrier"));
		ships.add(new Ship(4, "Battleship"));
		ships.add(new Ship(3, "Submarine"));
		ships.add(new Ship(3, "Cruiser"));
		ships.add(new Ship(2, "Destroyer"));
	}

	/**
	 * method
	 */
	public boolean hasShips()
	{
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * method
	 */
	public ArrayList<Ship> getShips()
	{
		return ships;
	}
}
