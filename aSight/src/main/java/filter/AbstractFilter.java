package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;

public abstract class AbstractFilter implements ContentLayerCompatible, Filter {

	private boolean active = false;
	protected int defaultKey = KeyEvent.VK_F12;

	public BufferedImage useFilter( BufferedImage img ) {
		if ( active ) {
			return action( img );
		} else {
			return img;
		}
	}

	protected abstract BufferedImage action( BufferedImage img );

	public void createGUI( Container parentBox ) {
		// JLabel
		JLabel filterLabel = new JLabel( "~~~ " + this.getClass().getCanonicalName() + " ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		// JCheckBox
		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				AbstractFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );
	}

	public int getGUIHeigth() {
		return 2;
	}

	public void setActive( boolean act ) {
		active = act;
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean needsRealPicture(){
		return false;
	}
	
	public boolean keyEvent(int key, int event, HotkeyMessage msg){
		if (key == defaultKey){
			return true;
		}
		return false;
	}
	
}
