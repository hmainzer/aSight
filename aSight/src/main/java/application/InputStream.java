package application;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
//import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/*
 * this class is a parent class for other sorts of inputStreams. 
 * It makes a screencapture and displays it in the specified output and applies the filters
 */

public class InputStream extends Thread {

	protected VideoField target1, target2;	
	protected boolean end = false;

	public InputStream( VideoField target1, VideoField target2 ) {
		this.target1 = target1;
		this.target2 = target2;
	}

	public VideoField getTarget1() {
		return target1;
	}

	public void setTarget1( VideoField target1 ) {
		this.target1 = target1;
	}

	public VideoField getTarget2() {
		return target2;
	}

	public void setTarget2( VideoField target2 ) {
		this.target2 = target2;
	}

	public void run() {
		Robot robot;
		try {
			robot = new Robot();			
			final Rectangle screenBounds = new Rectangle(800,600); // 800 x 600 because it is the maximum resolution of the uses AR-Display
			while ( !end ) {
				BufferedImage screen = robot.createScreenCapture( screenBounds );
				target1.setImage( screen );
				Main.applyFilters( screen, target2 );
				try {
					Thread.sleep( 33 );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}
		} catch ( AWTException e1 ) {
			e1.printStackTrace();
		}
		super.run();
	}

	public void end() {
		end = true;
	}
}
