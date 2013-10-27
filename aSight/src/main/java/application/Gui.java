package application;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import filter.*;
import java.util.ArrayList;

public class Gui implements KeyListener, ContentLayerCompatible {

	private JFrame frame, videoFrame;
	private VideoField in, out, outFull, outUsed;	
	private InputStream i;
	private int mainKey = 65;
	private boolean keyPushed = false;
	private HotkeyMessage msg;
	private ArrayList<Filter> filter;

	// createGui() - create and start Gui
	public static void createGui() {
		EventQueue.invokeLater( new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible( true );
					window.videoFrame.addKeyListener( window );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		} );
	}

	// Gui() - Gui constructor
	public Gui() {
		initialize();
	}

	public VideoField getIn() {
		return in;
	}

	public VideoField getOut() {
		return out;
	}

	public void keyPressed( KeyEvent e ) {
		if ( keyPushed ) {
			keyMsg( e.getKeyCode(), Utility.pressed );
		} else {
			if ( e.getKeyCode() == mainKey ) {
				keyPushed = true;
			}
		}
	}

	public void keyReleased( KeyEvent e ) {
		if ( e.getKeyCode() == mainKey ) {
			keyPushed = false;
		} else {
			keyMsg( e.getKeyCode(), Utility.released );
		}
	}

	public void keyTyped( KeyEvent e ) {
	}

	// keyMsg() - processes KeyEvents for filters
	private void keyMsg( int value, int event ) {
		boolean processed = false;
		for ( int i = 0; i < filter.size() && !processed; i++ ) {
			processed = filter.get( i ).keyEvent( value, event, msg );
		}
	}

	// initialize() - initialize contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.setTitle( "aSight" );
		frame.setResizable( false );
		frame.setBounds( 0, 0, 800, 580 );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.getContentPane().setLayout( null );

		// create videoFrame and set as output
		final GraphicsDevice[] monitorArray = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		videoFrame = new JFrame( "Fullscreen Display" );
		videoFrame.setLocation( monitorArray[0].getDefaultConfiguration().getBounds().getLocation() );
		videoFrame.setUndecorated( true );
		videoFrame.setExtendedState( Frame.MAXIMIZED_BOTH );
		outFull = new VideoField( monitorArray[0].getDefaultConfiguration().getBounds().getSize() );
		videoFrame.getContentPane().add( outFull );
		videoFrame.setVisible( false );

		// create HotkeyMessage to display activated hotkeys in the AR-Display
		msg = new HotkeyMessage(-1, this);
		Main.getContentLayer().giveContent( this,msg );
		
		// create content in absolute layout
		// absolute positioning: all x, y, w and h are multiples of 8!
		// default line height: 24
		JPanel videoPanel = new JPanel();
		videoPanel.setBounds( 0, 0, 310, 552 );
		frame.getContentPane().add( videoPanel );
		videoPanel.setLayout( null );

		final VideoField in = new VideoField( new Dimension( 310, 240 ) );
		in.setBounds( 0, 0, 310, 240 );
		videoPanel.add( in );

		JButton changeInput = new JButton( "Change Input" );
		changeInput.setBounds( 8, 248, 120, 24 );
		changeInput.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				i.end();
				i = new InputStreamCamera( in, outUsed );
				i.start();
			}
		} );
		videoPanel.add( changeInput );

		final VideoField out = new VideoField( new Dimension( 310, 240 ) );
		out.setBounds( 0, 280, 310, 240 );
		videoPanel.add( out );

		JLabel outLabel = new JLabel( "Out Channel: " );
		outLabel.setBounds( 168, 248, 80, 24 );
		videoPanel.add( outLabel );

		// link used output and start screencapture-InputStream
		outUsed = out;
		i = new InputStream( in, outUsed );

		final JComboBox<Integer> outComboBox = new JComboBox<Integer>( Main.getCountOutputDevices() );
		outComboBox.setBounds( 256, 248, 40, 24 );
		outComboBox.addItemListener( new ItemListener() {

			public void itemStateChanged( ItemEvent e ) {
				int itemId = outComboBox.getSelectedIndex();
				if ( itemId >= 0 ) {
					if ( itemId == 0 ) {
						outUsed = out;
						videoFrame.setVisible( false );
					} else {
						outUsed = outFull;
						videoFrame.setLocation( monitorArray[itemId - 1].getDefaultConfiguration().getBounds()
								.getLocation() );
						videoFrame.setExtendedState( Frame.MAXIMIZED_BOTH );
						outFull.changeSize( ( monitorArray[itemId - 1].getDefaultConfiguration().getBounds().getSize() ) );
						videoFrame.setVisible( true );
					}
					i.setTarget2( outUsed );
				}
			}
		} );
		videoPanel.add( outComboBox );

		JPanel filterPanel = new JPanel();
		filterPanel.setBounds( 310, 0, 490, 552 );
		frame.getContentPane().add( filterPanel );
		filterPanel.setLayout( null );

		// filterPanel - call filters to create their GUI
		{
			filter = Main.getFilter();
			int sumHeightPx = 0;
			int height = 0;
			int y = 0;
			// max height = 672
			for ( Filter f : filter ) {
				if ( ( height = f.getGUIHeight() ) > 0 ) {
					if ( ( sumHeightPx + height * 24 + ( height + 1 ) * 8 ) > 552 ) {
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

		// start inputStream
		i.start();
	}
}
