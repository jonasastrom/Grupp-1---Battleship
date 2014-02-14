package GUI;

import java.awt.Color;

import javax.swing.JButton;
/**
 * This is the class for representing the squares
 * @author Grupp 1
 *
 */
public class Zone extends JButton{

	public final int x, y;
	public final String name;

	/**
	 * constructor
	 */
	public Zone(int x, int y, String name){
		this.x = x;
		this.y = y;
		this.name = name;

		/**
		 * If x or y is 0 (x*y will be 0), its the buttons outside the 10x10 frame, 
		 * and the buttons should have a text, 1-10 or A-J. Otherwise set background blue.
		 */
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
	 *  This method change the background color of the zone.
	 */
	public void changeColor(int number){
		if(number == 0){
			setBackground(Color.GRAY);
		}else if(number == 1){
			setBackground(Color.GREEN);
		}else if(number == 2){
			setBackground(new Color(222, 49, 99));
		}else if(number == 3){
			setBackground(Color.BLACK);
		}
	}
}
