package Game;

/**
 * class Player
 * 
 * @author Grupp 1 - DAT055 2014
 * @version 1.0
 */
public class Player
{
	private Battlefield battlefield;
	private Fleet fleet;

	/**
	 * constructor
	 */
	public Player()
	{
		battlefield = new Battlefield();
		fleet = new Fleet();
	}

	/**
	 * method
	 */
	public boolean hasShips()
	{
		return fleet.hasShips();
	}

	/**
	 * method for 2.0
	 */
	/*public void placeShips()
	{
		while (!fleet.isPlaced()) {
			//wait
		}
	}*/

	/**
	 * method
	 */
	public void attack()
	{
		;
	}

	/**
	 * Return this player's battlefield
	 */
	public Battlefield getBattlefield()
	{
		return battlefield;
	}
	
	/**
	 * Return this player's fleet
	 */
	public Fleet getFleet()
	{
		return fleet;
	}

	/**
	 * Bomb this player's battlefield
	 * @return true if a ship was hit
	 *         false otherwise
	 */
	public boolean bomb(int x, int y)
	{
		if (!battlefield.isBombed(x, y)) {
			if (battlefield.setBomb(x, y)) {
				return true;
			}
		}
		else return false;
	}
}
