package filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class AbstractFilter implements Filter {

	private boolean active = false;

	public BufferedImage useFilter(BufferedImage img, Graphics2D graphic) {
		if (active) {
			return action(img, graphic);
		} else {
			return img;
		}
	}

	protected abstract BufferedImage action(BufferedImage img,
			Graphics2D graphic);

	public abstract void createGUI(Object parentBox);
	public abstract int getGUIHeigth();
	
	public void setActive(boolean act) {
		active = act;
	}

	public boolean isActive() {
		return active;
	}

}
