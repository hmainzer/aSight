package filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TestFilter extends AbstractFilter {

	@Override
	protected BufferedImage action(BufferedImage img, Graphics2D graphic) {
		graphic.setColor(Color.BLUE);
		graphic.fillOval(0, 0, img.getWidth(), img.getHeight());
		return img;
	}

	@Override
	public void createGUI(Object parentBox) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getGUIHeigth() {
		// TODO Auto-generated method stub
		return 0;
	}

}
