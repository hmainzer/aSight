package filter;

import java.awt.Container;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomFilter extends AbstractFilter {

	private int faktor = 1;

	protected BufferedImage action( BufferedImage img ) {
		int oldW = img.getWidth();
		int oldH = img.getHeight();
		int w = oldW / faktor;
		int h = oldH / faktor;
		int x = ( oldW - w ) / 2;
		int y = ( oldH - h ) / 2;
		img = application.Utility.resize( img.getSubimage( x, y, w, h ), oldW, oldH );
		return img;
	}

	public void createGUI( Container parentBox ) {
		// JLabel
		JLabel filterLabel = new JLabel( "~~~ Zoom ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		// JCheckBox
		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				ZoomFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );

		// JLabel
		JLabel faktorLabel = new JLabel( "Factor: " );
		faktorLabel.setBounds( 8, 72, 60, 24 );
		parentBox.add( faktorLabel );

		// JSpinner
		final JSpinner faktorSpinner = new JSpinner();
		faktorSpinner.setValue( faktor );
		faktorSpinner.setBounds( 72, 72, 60, 24 );
		faktorSpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( faktorSpinner.getValue() instanceof Integer ) {
					int value = (Integer) faktorSpinner.getValue();
					if ( value > 10 ) {
						value = 10;
					} else if ( value < 1 ) {
						value = 1;
					}
					faktor = value;
					faktorSpinner.setValue( faktor );
				}
			}
		} );
		parentBox.add( faktorSpinner );
	}

	public int getGUIHeigth() {
		return 3;
	}

}
