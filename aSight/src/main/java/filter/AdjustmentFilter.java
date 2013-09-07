package filter;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AdjustmentFilter extends AbstractFilter {

	private int x, y, width, height;

	public  AdjustmentFilter( int x, int y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	protected BufferedImage action( BufferedImage img ) {
		img = img.getSubimage( x, y, width, height );
		return img;
	}

	@Override
	public void createGUI( Container parentBox ) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getGUIHeigth() {
		// TODO Auto-generated method stub
		return 0;
	}


}
