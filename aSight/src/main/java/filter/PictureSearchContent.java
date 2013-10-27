package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import application.Main;

public class PictureSearchContent extends InformationContent {

	private String c1, c2, c3, loading;
	private int x, y;
	private Color c;
	private Font f;
	private boolean loadingBarState;
	private int resultTimeout;
	private int state;
	private String[] states;

	public PictureSearchContent(int timeout, ContentLayerCompatible parent,
			int x, int y) {
		super(timeout, parent);
		this.x = x;
		this.y = y;
		f = Main.getFont();
		c = Color.green;
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

	public void displayResults( String r1, String r2, String r3 ){
		resultTimeout = 60;
		c1 = r1;
		c2 = r2;
		c3 = r3;
		System.out.println(resultTimeout);
		System.out.println(c1+c2+c3);
	}
	
	public void setLoadingBarState(boolean state){
		loadingBarState = state;
	}
	
	public void setLoadingBar(String s){
		loading = s;
	}
	
	public BufferedImage paintContent(BufferedImage img) {
		Graphics2D graphics = img.createGraphics();
		if ( f != null ) {
			graphics.setFont( f );
		}
		graphics.setColor( c );
		if ( loadingBarState){
			state = state + 1;
			if ( state > 11 ) {
				state = 0;
			}
			graphics.drawString( loading + states[state], x, y );
		} else {
			if ( resultTimeout > 0 ){
				resultTimeout--;
				graphics.drawString(c1, x, y + (f.getSize() + 6));
				graphics.drawString(c2, x, y + 2*(f.getSize() + 6));
				graphics.drawString(c3, x, y + 3*(f.getSize() + 6));
			}
		}
		graphics.dispose();
		return img;
	}

}
