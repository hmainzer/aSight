package filter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class ContentLayer extends AbstractFilter {

	private Map<Filter, Map<Integer, InformationContent>> contentByFilter;
	private Integer ids;
	private ArrayList<Filter> filter = null;

	public ContentLayer() {
		super();
		contentByFilter = new HashMap<Filter, Map<Integer, InformationContent>>();
		ids = 0;
		super.setActive( true );
	}

	public void setFilterList( ArrayList<Filter> filter ) {
		this.filter = filter;
	}

	// action() - calls all active InformationContents to draw and reduces their
	// timeout
	protected BufferedImage action( BufferedImage img ) {
		boolean realPic = false;
		for ( Filter f : filter ) {
			realPic = realPic || ( f.needsRealPicture() && f.isActive() );
		}
		if ( !realPic ) {
			img = new BufferedImage( img.getWidth(), img.getHeight(), img.getType() );
			Graphics g = img.getGraphics();
			g.setColor( Color.black );
			g.fillRect( 0, 0, img.getWidth(), img.getHeight() );
			g.dispose();
		}

		Set<Filter> keys = contentByFilter.keySet();
		Set<Integer> keys2;
		Map<Integer, InformationContent> map;
		InformationContent c;
		for ( Filter f : keys ) {
			map = contentByFilter.get( f );
			keys2 = map.keySet();
			for ( Integer i : keys2 ) {
				c = map.get( i );
				img = c.paintContent( img );
				c.setTimeout( c.getTimeout() - 1 );
				if ( c.getTimeout() == 0 ) {
					map.remove( i );
				}
			}
		}
		return img;
	}

	public void createGUI( Container parentBox ) {
		// JLabel
		JLabel filterLabel = new JLabel( "~~~ Content Layer ~~~" );
		filterLabel.setBounds( 8, 8, 164, 24 );
		parentBox.add( filterLabel );

		// JCheckBox
		final JCheckBox isActiveBox = new JCheckBox( "Activate" );
		isActiveBox.setBounds( 8, 40, 120, 24 );
		isActiveBox.setSelected( true );
		isActiveBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				ContentLayer.this.setActive( isActiveBox.isSelected() );
			}
		} );
		parentBox.add( isActiveBox );
	}

	// giveContent() - gives the filter an InformationContent object to call for
	// drawing
	public synchronized int giveContent( Filter f, InformationContent c ) {
		Map<Integer, InformationContent> map = getOrCreateFilterEntry( f );
		int id = ids++;
		map.put( id, c );
		c.setId( id );
		return id;
	}

	// getOrCreateFilterEntry() - gives back an map object for the content of f
	private Map<Integer, InformationContent> getOrCreateFilterEntry( Filter f ) {
		Map<Integer, InformationContent> map;
		map = contentByFilter.get( f );
		if ( map == null ) {
			contentByFilter.put( f, new HashMap<Integer, InformationContent>() );
			map = contentByFilter.get( f );
		}
		return map;
	}

	// getIdActive() - returns if a id still exists within the contents of f
	public boolean getIdActive( Filter f, int id ) {
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ) {
			return false;
		}
		InformationContent c = map.get( id );
		return c != null;
	}

	// getInformationContent() - returns the Information content with id of f
	public InformationContent getInformationContent( Filter f, int id ) {
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ) {
			return null;
		}
		InformationContent c = map.get( id );
		return c;
	}

	// getAnyActiveOfFilter() - returns true if the filter has any
	// InformationContent Object
	public boolean getAnyActiveOfFilter( Filter f ) {
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map != null && map.size() > 0 ) {
			return true;
		}
		return false;
	}

	// getIDsForFilter() - returns a Set of all IDs used for f
	public Set<Integer> getIDsForFilter( Filter f ) {
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ) {
			return null;
		}
		return map.keySet();
	}

	// deleteContentOfFilter() - deletes a filter from the map and all his
	// content with him
	public void deleteContentOfFilter( Filter f ) {
		contentByFilter.remove( f );
	}

	// setTimeoutForFilter() - sets the timeout for all InformationContents of f
	// to t
	public void setTimeoutForFilter( Filter f, int t ) {
		Map<Integer, InformationContent> map;
		map = contentByFilter.get( f );
		if ( map != null ) {
			Set<Integer> keys = map.keySet();
			for ( Integer i : keys ) {
				map.get( i ).setTimeout( t );
			}
		}
	}

}
