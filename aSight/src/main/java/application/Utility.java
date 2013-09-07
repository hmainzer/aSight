package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utility {
	public static BufferedImage resize( BufferedImage img, int w, int h ){
		BufferedImage resizedImage = new BufferedImage( w, h, img.getType() );
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage( img, 0, 0, w, h, null );
		g.dispose();
		return resizedImage;
	}
}
