package filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TestStringContent extends InformationContent {

	private String content;
	private int x, y;
	private Color c;
	
	public TestStringContent( int timeout, Filter parent, String content, int x, int y ) {
		this(timeout, parent, content, x, y, Color.black);
	}
	
	public TestStringContent( int timeout, Filter parent, String content, int x, int y, Color c ) {
		super( timeout, parent );		
		this.content = content;
		this.x = x;
		this.y = y;
		this.c = c;	
	}

	public BufferedImage paintContent( BufferedImage img ) {
		Graphics2D graphics = img.createGraphics();
		graphics.setColor( c );		
		graphics.drawString( content, x, y );		
		graphics.dispose();
		return null;
	}

}
