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
		fleet = getFleet();
	}

	/**
	 * TODO in 2.0
	 * Skall fa in info fran gui. Den skall ha en string för att saga vilket skepp det galler.
	 * Sedan skall den ocksa fa koordinater till de zoner som skeppet skall ligga i
	 * anropar sedan dessan zoner och sager att de skall inehalla ett skepp
	 */
	/*@Override
	public void placeShips()
	{
		while (!fleet.isPlaced()) {
			//wait
		}
	}*/
}
