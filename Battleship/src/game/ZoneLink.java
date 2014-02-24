package game;

/**
 * class ZoneLink
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class ZoneLink 
{
	public static int x;
	public static int y;
	public static String leftOrRight;
	public static String state;
	
	public ZoneLink(int x, int y, String leftOrRight, String state)
	{
		ZoneLink.x = x;
		ZoneLink.y = y;
		ZoneLink.leftOrRight = leftOrRight;
		ZoneLink.state = state;
	}
}
