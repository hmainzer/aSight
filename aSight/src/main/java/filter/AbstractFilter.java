package filter;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class AbstractFilter implements Filter {

	private boolean active = false;

	public BufferedImage useFilter(BufferedImage img ) {
		if (active) {
			return action(img);
		} else {
			return img;
		}
	}

	protected abstract BufferedImage action(BufferedImage img);

	public abstract void createGUI(Container parentBox);
	public abstract int getGUIHeigth();
	
	public void setActive(boolean act) {
		active = act;
	}

	public boolean isActive() {
		return active;
	}
	

}
