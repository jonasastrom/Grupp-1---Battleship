package Game;

/**
 * Handles the human player
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Human extends Player
{
	//private Battlefield battlefield;
	private Fleet fleet;
	//private String id;
	
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

	/**
	 * Perform an attack by fetching input from
	 * the GUI and returning the coordinates
	 * @return The coordinates to be bombed
	 */
	//@Override
	//public int[] attack()
	//{
	//	return GameEngine.getGui().getInput();
	//}
}
