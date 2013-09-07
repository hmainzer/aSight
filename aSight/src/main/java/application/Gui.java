package application;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import filter.*;
import java.util.ArrayList;

public class Gui {

	private JFrame frame;
	private VideoField in, out;
	private Main application;

	/**
	 * Launch the application.
	 */

	public static void createGui( final Main application ) {
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				try {
					Gui window = new Gui( application );
					window.frame.setVisible( true );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		} );
	}

	/**
	 * Create the application.
	 */
	public Gui( Main application ) {
		this.application = application;
		initialize();
	}

	public VideoField getIn() {
		return in;
	}

	public VideoField getOut() {
		return out;
	}

	public Main getApplication() {
		return application;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle( "aSight" );
		frame.setResizable( false );
		frame.setBounds( 100, 100, 1000, 700 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( null );

		// absolute positioning: all x, y, w and h are multiples of 8!
		// default line height: 24

		JPanel inPanel = new JPanel();
		inPanel.setBounds( 0, 0, 320, 672 );
		frame.getContentPane().add( inPanel );
		inPanel.setLayout( null );

		VideoField in = new VideoField( new Dimension( 320, 240 ) );
		in.setBounds( 0, 0, 320, 240 );
		inPanel.add( in );

		JComboBox<Integer> inComboBox = new JComboBox<Integer>( application.getCountInputDevices() );
		inComboBox.setBounds( 74, 248, 120, 24 );
		inPanel.add( inComboBox );

		JLabel inLabel = new JLabel( "In Channel: " );
		inLabel.setBounds( 8, 248, 64, 24 );
		inPanel.add( inLabel );

		JPanel filterPanel = new JPanel();
		filterPanel.setBounds( 320, 0, 360, 672 );
		frame.getContentPane().add( filterPanel );
		filterPanel.setLayout( null );

		{// filterPanel
			ArrayList<Filter> filter = application.getFilter();
			int sumHeightPx = 0;
			int height = 0;
			int y = 0;
			// max height = 672
			for ( Filter f : filter ) {
				if ( ( height = f.getGUIHeigth() ) > 0 ) {
					if ( ( sumHeightPx + height * 24 + ( height + 1 ) * 8 ) > 672 ) {
						if ( y == 180 ) {
							System.err.println( "to many filters!!!!" );
						} else {
							y = 180;
							sumHeightPx = 0;
						}
					}
					JPanel container = new JPanel();
					container.setBounds( y, sumHeightPx, 180, height * 24 + ( height + 1 ) * 8 );
					container.setLayout( null );
					sumHeightPx = sumHeightPx + height * 24 + ( height + 1 ) * 8;
					f.createGUI( container );
					filterPanel.add( container );
				}
			}

		}

		JPanel outPanel = new JPanel();
		outPanel.setBounds( 680, 0, 320, 672 );
		frame.getContentPane().add( outPanel );
		outPanel.setLayout( null );

		VideoField out = new VideoField( new Dimension( 320, 240 ) );
		out.setBounds( 0, 0, 320, 240 );
		outPanel.add( out );

		JComboBox<Integer> outComboBox = new JComboBox<Integer>( application.getCountOutputDevices() );
		outComboBox.setBounds( 80, 248, 120, 24 );
		outPanel.add( outComboBox );

		JLabel outLabel = new JLabel( "Out Channel: " );
		outLabel.setBounds( 8, 248, 72, 24 );
		outPanel.add( outLabel );

		GraphicsDevice[] monitorArray = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		JFrame fullFrame = new JFrame( "Fullscreen Display" );
		fullFrame.setLocation( monitorArray[1].getDefaultConfiguration().getBounds().getLocation() );
		fullFrame.setUndecorated( true );
		fullFrame.setExtendedState( Frame.MAXIMIZED_BOTH );
		VideoField outFull = new VideoField( monitorArray[1].getDefaultConfiguration().getBounds().getSize() );
		fullFrame.getContentPane().add( outFull );
		fullFrame.setVisible( true );

		InputStream i = new InputStream( in, application, outFull );
		i.start();

	}
}
