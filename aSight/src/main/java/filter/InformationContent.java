package filter;

import java.awt.image.BufferedImage;

/*
 * InformationContent is an abstract class for all informations handled by the ContentLayer
 * paintContent is the relevant function to apply any sort of information in the ContentLayer
 */
public abstract class InformationContent {

	
	private int timeout;	
	private final ContentLayerCompatible parent;
	private int id;

	public InformationContent( int timeout, ContentLayerCompatible parent ) {				
		this.timeout = timeout;
		this.parent = parent;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public int getTimeout() {
		return timeout;
	}

	public ContentLayerCompatible getParent() {
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
