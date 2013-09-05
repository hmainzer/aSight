package filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface Filter {
	public BufferedImage useFilter( BufferedImage img, Graphics2D graphic );
	public void createGUI( Object parentBox );
	public int getGUIHeigth();
	public void setActive(boolean act);
	public boolean isActive();
}
