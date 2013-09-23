package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class PictureSearchFilter extends AbstractFilter {

	private ContentLayer cl;
	private boolean takeNextPicture = false;

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
		PictureSearchThread search = new PictureSearchThread( img, cl, this );
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

	}

	public int getGUIHeigth() {
		return 3;
	}

}
