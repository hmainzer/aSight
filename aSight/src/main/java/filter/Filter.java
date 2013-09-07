package filter;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface Filter {
	public BufferedImage useFilter( BufferedImage img );
	public void createGUI( Container parentBox );
	public int getGUIHeigth();
	public void setActive(boolean act);
	public boolean isActive();
}
