package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomFilter extends AbstractFilter {

	private int faktor = 100;

	protected BufferedImage action( BufferedImage img ) {		
		float f = faktor / 100f;
		int oldW = img.getWidth();		
		int oldH = img.getHeight();
		int w = (int) ( oldW / f );
		int h = (int) ( oldH / f );
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
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				ZoomFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );

		// JLabel
		JLabel faktorLabel = new JLabel( "Factor (%): " );
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
					if ( value > 1000 ) {
						value = 1000;
					} else if ( value < 100 ) {
						value = 100;
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

	public boolean needsRealPicture() {
		return true;
	}
}
