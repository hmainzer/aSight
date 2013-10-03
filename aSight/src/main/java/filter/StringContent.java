package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StringContent extends InformationContent {

	private String content;
	private int x, y;
	private Color c;
	private Font f;

	public StringContent( int timeout, ContentLayerCompatible parent, String content, int x, int y ) {
		this( timeout, parent, content, x, y, Color.black, null );
	}

	public StringContent( int timeout, ContentLayerCompatible parent, String content, int x, int y, Color c ) {
		this( timeout, parent, content, x, y, c, null );
	}

	public StringContent( int timeout, ContentLayerCompatible parent, String content, int x, int y, Color c, Font f ) {
		super( timeout, parent );
		this.content = content;
		this.x = x;
		this.y = y;
		this.c = c;
		this.f = f;
	}

	public BufferedImage paintContent( BufferedImage img ) {
		Graphics2D graphics = img.createGraphics();
		if ( f != null ) {
			graphics.setFont( f );
		}
		graphics.setColor( c );
		graphics.drawString( content, x, y );
		graphics.dispose();
		return img;
	}

}
