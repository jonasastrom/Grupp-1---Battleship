package game;

/**
 * class Player
 * 
 * @author Grupp 1 - DAT055 2014
 * @version 1.0
 */
public class Player
{
	private Battlefield battlefield;

	/**
	 * constructor
	 */
	public Player()
	{
		battlefield = new Battlefield();
	}

	/**
	 * method
	 */
	public void placeShips()
	{
		;		
	}

	/**
	 * method
	 */
	public void attack()
	{
		;
	}

	/**
	 * method
	 */
	public boolean hasShips()
	{
		return true;
	}

	/**
	 * method
	 */
	public Battlefield getBattlefield()
	{
		return battlefield;
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
