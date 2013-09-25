package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.jhlabs.image.PerspectiveFilter;

public class CorrectionFilter extends AbstractFilter {

	public CorrectionFilter() {
		super();
		super.setActive( true );
	}

	protected BufferedImage action( BufferedImage img ) {
		BufferedImage targetImage = null;
		BufferedImage blackImage = new BufferedImage( 800, 600, BufferedImage.TYPE_INT_RGB );
		if ( img.getWidth() != 800 || img.getHeight() != 600 ) {
			img = application.Utility.resize( img, 800, 600 );
		}
		PerspectiveFilter perspectiveFilter = new PerspectiveFilter();
		perspectiveFilter.setCorners( 25, 98, 691, 8, 691, 591, 25, 501 );
		targetImage = perspectiveFilter.filter( img, targetImage );
		blackImage.getGraphics().drawImage( targetImage, 25, 8, null );
		return blackImage;
	}

	public void createGUI( Container parentBox ) {
		JLabel filterLabel = new JLabel( "~~~ Correction Filter ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.setSelected( true );
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				CorrectionFilter.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );
	}

	public int getGUIHeigth() {
		return 2;
	}
}
