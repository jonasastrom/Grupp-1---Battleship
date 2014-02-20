package Game;

public class ZoneLink 
{
	public static int x;
	public static int y;
	public static String leftOrRight;
	public static String state;
	
	public ZoneLink(int x, int y, String leftOrRight, String state)
	{
		this.x = x;
		this.y = y;
		this.leftOrRight = leftOrRight;
		this.state = state;
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	public String getLeftOrRight()
	{
		return leftOrRight;
	}
	public String getSate()
	{
		return state;
	}
}
