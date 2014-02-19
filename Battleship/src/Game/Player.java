package Game;

/**
 * The base class for all players
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Player
{
	private Battlefield battlefield;
	private Fleet fleet;

	/**
	 * Construct a battlefield and a fleet
	 */
	public Player(String id)
	{
		battlefield = new Battlefield(id);
		fleet = new Fleet();
	}

	/**
	 * TODO in 2.0
	 */
	public void placeShips()
	{
		//while (!fleet.isPlaced()) {
			//wait
		//}
	}

	/**
	 * Bomb this player's battlefield
	 * @return true if a ship was hit,
	 *         false otherwise
	 */
	public boolean bomb(int x, int y)
	{
		if (!battlefield.isBombed(x, y)) {
			if (battlefield.setBomb(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if this player has any ships left
	 * @return true if any ships still floats,
	 *         false if all are sunk
	 */
	public boolean hasShips()
	{
		return !fleet.isSunk();
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
}
