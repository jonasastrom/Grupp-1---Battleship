package game;

import game.GameEngine.LastShot;

/**
 * The base class for all players.
 * 
 * @author Group 1 - DAT055 2014
 * @version 2.0
 */
public abstract class Player
{
	private Battlefield battlefield;
	private Fleet fleet;

	/**
	 * Construct a battlefield and a fleet.
	 */
	public Player(String id, ZoneListener zoneListener)
	{
		battlefield = new Battlefield(id, zoneListener);
		fleet = new Fleet();
	}

	/**
	 * Get this player's battlefield.
	 * @return The battlefield
	 */
	public Battlefield getBattlefield()
	{
		return battlefield;
	}

	/**
	 * Get this player's fleet.
	 * @return The fleet
	 */
	public Fleet getFleet()
	{
		return fleet;
	}

	/**
	 * Check if this player has any ships left.
	 * @return true if any ships still floats,
	 *         false if all are sunk
	 */
	public boolean hasShips()
	{
		return !fleet.isSunk();
	}

	/**
	 * Called in the beginning of the game when
	 * the player should place it's ships.
	 */
	public abstract void placeShips();

	/**
	 * Bomb a zone on this player's battlefield.
	 * @param x The x-coordinate of the zone
	 * @param y The y-coordinate of the zone
	 * @return MISS, HIT or SUNK
	 */
	public LastShot bomb(int x, int y)
	{
		if (battlefield.isBombed(x, y))
			throw new IllegalArgumentException(
					"The zone ("+x+","+y+") has already been bombed");

		return battlefield.setBomb(x, y);
	}
}
