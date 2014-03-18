package gui;

import java.awt.Color;

import javax.swing.JButton;

/**
 * This is the class for representing the squares
 * 
 * @author Group 1 - DAT055 2014
 * @version 1.0
 */
public class Zone extends JButton{

	private static final long serialVersionUID = -4145708944665750892L;
	public final int x, y;
	public final String name;

	/**
	 * Constructor for creating a Zone-object that have x and y coordinates and a name.
	 * These are the squares in the battlefield.  
	 * @param x - The x-coordinate
	 * @param y - The y-coordinate
	 * @param name - Name of the Zone
	 */
	public Zone(int x, int y, String name){
		this.x = x;
		this.y = y;
		this.name = name;

		// If x or y is 0 (x*y will be 0), its the buttons outside the 10x10 frame, 
		// and the buttons should have a text, 1-10 or A-J. Otherwise set background blue.		 
		if((x * y) == 0){
			setBackground(null);
			setBorder(null);
			setEnabled(false);
			if(x == 0 && y == 0){
				// do nothing, it is the button up in the right corner, and it should not be shown.
			}else if(x == 0){
				setText("" + y);
			}else if(y == 0){
				char letterPlace = name.charAt(0);
				setText("" + letterPlace);
			}
		}else if((x * y) != 0){
			setBackground(Color.BLUE);
		}
	}

	/**
	 * This method change the background color of the zone.
	 * @param color
	 */
	public void changeColor(Color color){
		setBackground(color);
	}
}
