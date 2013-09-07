package application;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class InputStream extends Thread {

	private VideoField target1, target2;
	private Main application;

	public InputStream( VideoField target1, Main application, VideoField target2 ) {
		this.target1 = target1;
		this.target2 = target2;
		this.application = application;
	}

	@Override
	public void run() {
		Robot robot;
		try {
			robot = new Robot();

			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			final Rectangle screenBounds = new Rectangle( toolkit.getScreenSize() );
			while ( true ){
				BufferedImage screen = robot.createScreenCapture( screenBounds );
				target1.setImage( screen );
				application.applyFilters( screen, target2 );
				try {
					Thread.sleep( 33 );
				} catch ( InterruptedException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch ( AWTException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		super.run();
	}

}
