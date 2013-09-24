package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureSearchFilter extends AbstractFilter {

	private ContentLayer cl;
	private boolean takeNextPicture = false;
	private int category = 0, precision = 1;

	public PictureSearchFilter( ContentLayer cl ) {
		this.cl = cl;
	}

	protected BufferedImage action( BufferedImage img ) {
		if ( !takeNextPicture ) {
			return img;
		}
		takeNextPicture = false;
		startSearch( application.Utility.copy( img ) );

		return img;
	}

	private void startSearch( BufferedImage img ) {
		PictureSearchThread search = new PictureSearchThread( img, cl, this, category, precision );
		search.start();
	}

	public void createGUI( Container parentBox ) {
		// JLabel
		JLabel filterLabel = new JLabel( "~~~ Picture Search ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		// JCheckBox
		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				PictureSearchFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );

		// Snapshot
		final JButton snapshotButton = new JButton( "Search Picture" );
		snapshotButton.setBounds( 8, 72, 120, 24 );
		snapshotButton.addActionListener( new ActionListener() {

			public void actionPerformed( ActionEvent arg0 ) {
				takeNextPicture = isActive();
			}
		} );
		parentBox.add( snapshotButton );

		// Category Label
		final JLabel categoryLabel = new JLabel( "Category:" );
		categoryLabel.setBounds( 8, 104, 48, 24 );
		parentBox.add( categoryLabel );

		// Category
		final JComboBox<String> categoryComboBox = new JComboBox<String>( getCategories() );
		categoryComboBox.setBounds( 64, 104, 104, 24 );
		categoryComboBox.setSelectedIndex( category );
		categoryComboBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				if ( arg0.getActionCommand().equals( "comboBoxChanged" ) ) {
					category = categoryComboBox.getSelectedIndex();
				}
			}
		} );
		parentBox.add( categoryComboBox );

		// Precision Label
		final JLabel precisionLabel = new JLabel( "Precision:" );
		precisionLabel.setBounds( 8, 136, 48, 24 );
		parentBox.add( precisionLabel );

		// Precision
		final JSpinner precisionSpinner = new JSpinner();
		precisionSpinner.setValue( precision );
		precisionSpinner.setBounds( 64, 136, 104, 24 );
		precisionSpinner.addChangeListener( new ChangeListener() {
			public void stateChanged( ChangeEvent e ) {
				if ( precisionSpinner.getValue() instanceof Integer ) {
					int value = (Integer) precisionSpinner.getValue();
					if ( value > 10 ) {
						value = 10;
					} else if ( value < 1 ) {
						value = 1;
					}
					precision = value;
					precisionSpinner.setValue( precision );
				}
			}
		} );
		parentBox.add( precisionSpinner );
	}

	public int getGUIHeigth() {
		return 5;
	}

	private Vector<String> getCategories() {
		Vector<String> v = new Vector<String>();
		v.add( "0 > US Traffic Signs                              " );
		v.add( "1 > Flags (fotw.net)                              " );
		v.add( "2 > Flags                                         " );
		v.add( "3 > English football jerseys                      " );
		v.add( "4 > US Uniform Insignia                           " );
		v.add( "5 > Symbols (symbols.com)                         " );
		v.add( "6 > Maya Hyerogliphs                              " );
		v.add( "7 > Van Gogh Gallery                              " );
		v.add( "8 > Picasso Gallery                               " );
		v.add( "9 > Michelangelo Gallery                          " );
		v.add( "10 > Monet Gallery                                " );
		v.add( "11 > Munch Gallery                                " );
		v.add( "12 > UK Heraldry                                  " );
		v.add( "13 > Irish Coats of Arms                          " );
		v.add( "14 > American Historical flags                    " );
		v.add( "15 > Logos                                        " );
		v.add( "16 > Post stamps (based on google search)         " );
		v.add( "17 > American flags (based on google search)      " );
		v.add( "18 > Space (based on google search)               " );
		v.add( "19 > Coin (based on google search)                " );
		v.add( "20 > Comics Batman                                " );
		v.add( "21 > Famous people (based on google search)       " );
		v.add( "22 > Nature (based on google search)              " );
		v.add( "23 > Temples (based on google search)             " );
		v.add( "24 > Flowers (based on google search)             " );
		v.add( "25 > Ships (based on google search)               " );
		v.add( "26 > Monuments (based on google search)           " );
		v.add( "27 > Banknotes (based on google search)           " );
		v.add( "28 > Florence monuments (based on google search)  " );
		v.add( "29 > Paris monuments (based on google search)     " );
		v.add( "30 > Berlin monuments (based on google search)    " );
		v.add( "31 > Barcelona monuments (based on google search) " );
		v.add( "32 > Rome monuments (based on google search)      " );
		return v;
	}

}
