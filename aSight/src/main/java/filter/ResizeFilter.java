package filter;

import java.awt.Container;
import java.awt.image.BufferedImage;

import application.Main;
import application.Utility;

public class ResizeFilter extends AbstractFilter {

	private int toX, toY, borderX, borderY;
	private boolean blackBorder;
	
	public ResizeFilter() {
		super();
		toX = Main.getFromConfig("");
		toY = Main.getFromConfig("");
		borderX = Main.getFromConfig("");
		borderY = Main.getFromConfig("");
		blackBorder = Main.getFromConfig("") == 1;
	}
	
	@Override
	protected BufferedImage action(BufferedImage img) {
		if ( blackBorder ){			
			BufferedImage blackImage = new BufferedImage( borderX, borderY, BufferedImage.TYPE_INT_RGB );
			BufferedImage img2 = Utility.resize(img, toX, toY);			
			blackImage.getGraphics().drawImage( img2, (borderX - toX) / 2, (borderY - toY) / 2, null );
			return blackImage;
		} else {
			return Utility.resize(img, toX, toY);	
		}
	}
	
	@Override
	public void createGUI(Container parentBox) {
		// TODO Auto-generated method stub
		super.createGUI(parentBox);
	}
	
	@Override
	public int getGUIHeight() {
		return 3;
	}

}
