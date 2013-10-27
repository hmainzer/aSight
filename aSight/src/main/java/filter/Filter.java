package filter;

import java.awt.Container;
import java.awt.image.BufferedImage;

public interface Filter extends ContentLayerCompatible {
	
	public BufferedImage useFilter( BufferedImage img );

	// createGUI - creator for the gui components of a filter. parentBox has 180px width and height depends on the value returned by getGuiHeigth() 
	public void createGUI( Container parentBox );
	
	public int getGUIHeight();
	
	// setActive - sets if the filter is applied or not
	public void setActive( boolean act );

	public boolean isActive();

	// needsRealPicture - messages the content layer, that this filter is dependent on displaying the input picture instead of only text on black ground
	public boolean needsRealPicture();

	// keyEvent - manages the hotkeys of the filter
	public boolean keyEvent( int key, int event, HotkeyMessage msg );
}
