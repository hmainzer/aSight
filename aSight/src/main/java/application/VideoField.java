package application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class VideoField extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6169263855591062369L;
	private Image myImage;
	private Dimension mySize;

	public VideoField( Dimension size ) {
		mySize = size;
		setSize( mySize );
	}

	public void changeSize( Dimension size ) {
		mySize = size;
		setSize( mySize );
	}

	public synchronized void paint( Graphics g ) {
		if ( myImage != null ) {
			g.drawImage( myImage, 0, 0, this );
		}
	}

	public void setImage( BufferedImage image ) {
		Dimension imgDim = new Dimension( image.getHeight(), image.getWidth() );
		if ( !imgDim.equals( mySize ) ) {
			image = application.Utility.resize( image, mySize.width, mySize.height );
		}
		SwingUtilities.invokeLater( new ImageRunnable( image ) );
	}

	private class ImageRunnable implements Runnable {
		private final Image newImage;

		public ImageRunnable( Image newImage ) {
			super();
			this.newImage = newImage;
		}

		public void run() {
			VideoField.this.myImage = newImage;
			repaint();
		}
	}

}
