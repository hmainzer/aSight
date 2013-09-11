package filter;

import java.awt.image.BufferedImage;

public abstract class InformationContent {

	
	private int timeout;	
	private final Filter parent;
	private int id;

	public InformationContent( int timeout, Filter parent ) {				
		this.timeout = timeout;
		this.parent = parent;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public int getTimeout() {
		return timeout;
	}

	public Filter getParent() {
		return parent;
	}

	public int getId() {
		return id;
	}	

	public void setTimeout( int timeout ) {
		this.timeout = timeout;
	}
	
	public abstract BufferedImage paintContent( BufferedImage img );

}
