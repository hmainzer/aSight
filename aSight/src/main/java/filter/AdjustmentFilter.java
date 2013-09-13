package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AdjustmentFilter extends AbstractFilter {

	private int x, y, width, height;
	private JSpinner xSpinner, ySpinner, wSpinner, hSpinner;

	public AdjustmentFilter( int x, int y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	protected BufferedImage action( BufferedImage img ) {
		if ( width > img.getWidth() ) {
			width = img.getWidth();
		}
		if ( height > img.getHeight() ) {
			height = img.getHeight();
		}
		if ( y + height > img.getHeight() ) {
			y = img.getHeight() - height;
			ySpinner.setValue( y );
		}
		if ( x + width > img.getWidth() ) {
			x = img.getWidth() - width;
			xSpinner.setValue( x );
		}
		img = img.getSubimage( x, y, width, height );
		return img;
	}

	public void createGUI( Container parentBox ) {
		// JLabel
		JLabel filterLabel = new JLabel( "~~~ Adjustment ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		// JCheckBox
		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				AdjustmentFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );

		// JLabel
		JLabel xLabel = new JLabel( "x: " );
		xLabel.setBounds( 8, 72, 16, 24 );
		parentBox.add( xLabel );

		// JSpinner
		xSpinner = new JSpinner();
		xSpinner.setValue( x );
		xSpinner.setBounds( 24, 72, 64, 24 );
		xSpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( xSpinner.getValue() instanceof Integer ) {
					int value = (Integer) xSpinner.getValue();
					if ( value < 0 ) {
						value = 0;
					}
					x = value;
					xSpinner.setValue( x );
				}
			}
		} );
		parentBox.add( xSpinner );

		// JLabel
		JLabel yLabel = new JLabel( "y: " );
		yLabel.setBounds( 96, 72, 16, 24 );
		parentBox.add( yLabel );

		// JSpinner
		ySpinner = new JSpinner();
		ySpinner.setValue( y );
		ySpinner.setBounds( 112, 72, 64, 24 );
		ySpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( ySpinner.getValue() instanceof Integer ) {
					int value = (Integer) ySpinner.getValue();
					if ( value < 0 ) {
						value = 0;
					}
					y = value;
					ySpinner.setValue( y );
				}
			}
		} );
		parentBox.add( ySpinner );

		// JLabel
		JLabel wLabel = new JLabel( "w: " );
		wLabel.setBounds( 8, 104, 16, 24 );
		parentBox.add( wLabel );

		// JSpinner
		wSpinner = new JSpinner();
		wSpinner.setValue( width );
		wSpinner.setBounds( 24, 104, 64, 24 );
		wSpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( wSpinner.getValue() instanceof Integer ) {
					int value = (Integer) wSpinner.getValue();
					if ( value < 0 ) {
						value = 0;
					}
					width = value;
					wSpinner.setValue( width );
				}
			}
		} );
		parentBox.add( wSpinner );

		// JLabel
		JLabel hLabel = new JLabel( "h: " );
		hLabel.setBounds( 96, 104, 16, 24 );
		parentBox.add( hLabel );

		// JSpinner
		hSpinner = new JSpinner();
		hSpinner.setValue( height );
		hSpinner.setBounds( 112, 104, 64, 24 );
		hSpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( hSpinner.getValue() instanceof Integer ) {
					int value = (Integer) hSpinner.getValue();
					if ( value < 0 ) {
						value = 0;
					}
					height = value;
					hSpinner.setValue( height );
				}
			}
		} );
		parentBox.add( hSpinner );
	}

	public int getGUIHeigth() {
		return 4;
	}

}
