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
	private String s;
	private boolean inactive = false;

	public LoadingBarContent( int timeout, ContentLayerCompatible parent, int x, int y, String s, Color c, Font f ) {
		super( timeout, parent );
		this.x = x;
		this.y = y;
		this.c = c;
		this.f = f;
		this.s = s;
		states = new String[12];
		states[0] = "";
		states[1] = ".";
		states[2] = "..";
		states[3] = "...";
		states[4] = " ...";
		states[5] = "  ...";
		states[6] = "   ...";
		states[7] = "    ...";
		states[8] = "     ...";
		states[9] = "      ...";
		states[10] = "      ..";
		states[11] = "       .";
	}

	@Override
	public BufferedImage paintContent( BufferedImage img ) {
		if (inactive ){
			return img;
		}
		Graphics2D graphics = img.createGraphics();
		if ( f != null ) {
			graphics.setFont( f );
		}
		graphics.setColor( c );
		state = state + 1;
		if ( state > 11 ) {
			state = 0;
		}
		graphics.drawString( s + states[state], x, y );
		graphics.dispose();
		return img;
	}

	public void setString( String s ) {
		this.s = s;
	}

	public void setInactiveAndKill(){
		inactive = true;
		this.setTimeout( 1 );
	}
}
