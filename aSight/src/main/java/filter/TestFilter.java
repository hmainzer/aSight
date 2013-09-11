package filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TestFilter extends AbstractFilter {

	private ContentLayer cLayer;
	private boolean actOnce = true;
	private int lastID;

	public TestFilter( ContentLayer cLayer ) {
		super();
		this.cLayer = cLayer;
	}

	@Override
	protected BufferedImage action( BufferedImage img ) {
		/*
		 * Graphics2D graphic = img.createGraphics(); graphic.setColor(
		 * Color.BLUE ); graphic.fillOval( 0, 0, img.getWidth(), img.getHeight()
		 * ); graphic.dispose();
		 */
		if ( actOnce ) {
			InformationContent c = new TestStringContent( 60, this, "Hallo Welt", (int) ( Math.random() * 500 ),
					(int) ( Math.random() * 500 ), Color.green );
			int i = cLayer.giveContent( this, c );
			actOnce = false;
			lastID = i;
		}
		if ( lastID >= 0 ) {
			actOnce = !cLayer.getIdActive( this, lastID );
		}
		return img;
	}
}
