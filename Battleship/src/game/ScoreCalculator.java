package game;

/**
 * 
 * @author Vivi
 * 
 */
public class ScoreCalculator {
	private static long combo;
	private static boolean preHit;
	private static boolean sunkShip;
	private static long points;

	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FIVE = 5;
	private static final int FIVEK = 5000;
	private static final int SIXTY_ONE_K = 61000;

	/**
	 * Construktor for the calculator, sets the init for the instances. Created
	 * by gameEngine maybe?
	 * 
	 * @param score
	 */
	public ScoreCalculator() {
		combo = 1;
		sunkShip = false;
		preHit = false;
	}

	/**
	 * Called by gameEngine after each attack from the player. 
	 * Calculates the score point for the current attack.
	 * 
	 * @param hit
	 *            gives us the information if the attack succeeded or not
	 */
	public static long updateScore(GameEngine.LastShot hit) {
		comboCounter();
		switch (hit) {
		case MISS: // if miss just add 2 points to the current score, no combo
			preHit = false;
			sunkShip = false;
			return TWO;
		case HIT:
			preHit = true;
			sunkShip = false;
			return FIVEK * combo;
		case SUNK:
			preHit = true;
			sunkShip = true;
			return SIXTY_ONE_K * combo;
		default:
			System.out.println("Invalid Action");
			return 1;

		}
	}

	/**
	 * Sets the combo, which will be used when adding the score
	 */
	private static void comboCounter() {
		if (preHit && !sunkShip) // if the previous attack was a hit and not
									// sunk
			combo *= THREE;
		else if (preHit && sunkShip) // if the previous attack sunk the ship
			combo *= FIVE;
		else
			combo = ONE; // if the current attack was a SUNK but the previous
							// attack was a MISS
							// or the current attack was a hit but the previous
							// was a MISS
	}

	public static void testCalculator() {
		points += updateScore(GameEngine.LastShot.MISS); // 2 points
		points += updateScore(GameEngine.LastShot.HIT); // 5000 points
		points += updateScore(GameEngine.LastShot.HIT); // 5000 + 5000*3 points
		points += updateScore(GameEngine.LastShot.HIT); // 20 000 + 5000*3
		points += updateScore(GameEngine.LastShot.SUNK); // omg math.... -____-'
		points += updateScore(GameEngine.LastShot.SUNK);
		points += updateScore(GameEngine.LastShot.MISS);
		points += updateScore(GameEngine.LastShot.SUNK);
		System.out.println("Congratz you got: " + points);

	}
}
