package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utility {
	public static int pressed = 1;
	public static int released = 0;
	public static int typed = 2;
	
	
	public static BufferedImage resize( BufferedImage img, int w, int h ) {
		BufferedImage resizedImage = new BufferedImage( w, h, img.getType() );
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage( img, 0, 0, w, h, null );
		g.dispose();
		return resizedImage;
	}

	public static BufferedImage copy( BufferedImage img ) {
		int w = img.getWidth(), h = img.getHeight();
		BufferedImage newImg = new BufferedImage( w, h, img.getType() );
		Graphics2D g = newImg.createGraphics();
		g.drawImage( img, 0, 0, w, h, null );
		g.dispose();
		return newImg;
	}

	public static String encodeURIComponent( String s ) {
		String result = null;

		try {
			result = URLEncoder.encode( s, "UTF-8" ).replaceAll( "\\+", "%20" ).replaceAll( "\\%21", "!" )
					.replaceAll( "\\%27", "'" ).replaceAll( "\\%28", "(" ).replaceAll( "\\%29", ")" )
					.replaceAll( "\\%7E", "~" );
		}

		// This exception should never occur.
		catch ( UnsupportedEncodingException e ) {
			result = s;
		}

		return result;
	}
}
