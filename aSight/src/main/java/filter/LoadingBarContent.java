package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LoadingBarContent extends InformationContent {

	private int x, y;
	private int state;
	private String[] states;
	private Color c;
	private Font f;

	public LoadingBarContent( int timeout, Filter parent, int x, int y, Color c, Font f ) {
		super( timeout, parent );
		this.x = x;
		this.y = y;
		this.c = c;
		this.f = f;
		states = new String[12];
		states[0] = "Searching";
		states[1] = "Searching.";
		states[2] = "Searching..";
		states[3] = "Searching...";
		states[4] = "Searching ...";
		states[5] = "Searching  ...";
		states[6] = "Searching   ...";
		states[7] = "Searching  ...";
		states[8] = "Searching ...";
		states[9] = "Searching...";
		states[10] = "Searching..";
		states[11] = "Searching.";
	}

	@Override
	public BufferedImage paintContent( BufferedImage img ) {
		Graphics2D graphics = img.createGraphics();
		if ( f != null ) {
			graphics.setFont( f );
		}
		graphics.setColor( c );
		state = state + 1;
		if ( state > 11 ) {
			state = 0;
		}
		graphics.drawString( states[state], x, y );
		graphics.dispose();
		return img;
	}

}
