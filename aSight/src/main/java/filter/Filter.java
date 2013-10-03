package filter;

import java.awt.Container;
import java.awt.image.BufferedImage;

public interface Filter extends ContentLayerCompatible {
	public BufferedImage useFilter( BufferedImage img );

	public void createGUI( Container parentBox );

	public int getGUIHeigth();

	public void setActive( boolean act );

	public boolean isActive();

	public boolean needsRealPicture();

	public boolean keyEvent( int key, int event, HotkeyMessage msg );
}
