package filter;

import java.awt.image.BufferedImage;
import java.util.*;

public class ContentLayer extends AbstractFilter {

	private Map<Filter, Map<Integer, InformationContent>> contentByFilter;
	private Integer ids;

	public ContentLayer() {
		super();
		contentByFilter = new HashMap<Filter, Map<Integer, InformationContent>>();
		ids = 0;
		super.setActive( true );
	}

	protected BufferedImage action( BufferedImage img ) {
		Set<Filter> keys = contentByFilter.keySet();
		Set<Integer> keys2;
		Map<Integer, InformationContent> map;
		InformationContent c;
		for( Filter f : keys ){
			map = contentByFilter.get( f );
			keys2 = map.keySet();
			for( Integer i : keys2 ){
				c = map.get( i );
				c.paintContent( img );
				c.setTimeout( c.getTimeout()-1 );
				if ( c.getTimeout() == 0 ){
					map.remove( i );
				}
			}
		}
		return img;
	}

	public int giveContent( Filter f, InformationContent c ) {
		Map<Integer, InformationContent> map = getOrCreateFilterEntry( f );
		int id = ids++;
		map.put( id, c );
		c.setId( id );
		return id;
	}

	private Map<Integer, InformationContent> getOrCreateFilterEntry( Filter f ) {
		Map<Integer, InformationContent> map;
		map = contentByFilter.get( f );
		if ( map == null ) {
			contentByFilter.put( f, new HashMap<Integer, InformationContent>() );
			map = contentByFilter.get( f );
		}
		return map;
	}

	public boolean getIdActive( Filter f, int id){
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ){
			return false;
		}
		InformationContent c = map.get( id );
		return c != null;
	}
	
	public InformationContent getInformationContent( Filter f, int id ){
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ){
			return null;
		}
		InformationContent c = map.get( id );
		return c;
	}
	
	public boolean getAnyActiveOfFilter( Filter f ){
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map != null && map.size() > 0 ){
			return true;
		}
		return false;
	}
	
	public Set<Integer> getIDsForFilter( Filter f ){
		Map<Integer, InformationContent> map = contentByFilter.get( f );
		if ( map == null ){
			return null;
		}
		return map.keySet();
	}
	
}
