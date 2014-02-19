package Game;

/**
 * Handles the human player
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Human extends Player
{
	private Fleet fleet;
	
	/**
	 * Construct the human player
	 */
	public Human()
	{
		super("human");
	}
	
	/**
	 * TODO in 2.0
	 */
	@Override
	public void placeShips()
	{
		while (!fleet.isPlaced()) {
			//wait
		}
	}
}
