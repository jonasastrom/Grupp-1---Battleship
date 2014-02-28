package highscore;

import java.io.Serializable;

/**
 * This class handles a single score.
 * 
 * @author Group 1 - DAT055 2014
 * @version 3.0
 */
public class Score implements Serializable
{
	private static final long serialVersionUID = 4943630263515062187L;

	private int points;
	private String name;

	/**
	 * Construct a score.
	 * @param points The points
	 * @param name The name
	 */
	public Score(int points, String name)
	{
		this.points = points;
		this.name = name;
	}

	/**
	 * Get the points of this score.
	 * @return The points
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * Get the name of this score.
	 * @return The name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the points of this score.
	 * @param points The points to set
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	/**
	 * Set the name of this score.
	 * @param name The name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Get a string representation of this score. 
	 * @return The string
	 */
	@Override
	public String toString()
	{
		return "points=" + points + " name=" + name;
	}
}
