package filter;

import java.awt.image.BufferedImage;
import java.util.Timer;

public class CodeParaFilter extends AbstractFilter {
	CodeParaContent codeContent;
	CodeParaAccessControllImg imgAccess = new CodeParaAccessControllImg();

	CodeThread thread;
	Timer timer;

	private ContentLayer cl;

	public CodeParaFilter( ContentLayer cl ) {
		this.codeContent = new CodeParaContent( -1, null );
		this.cl = cl;
		CodeParaAccessControllList list = new CodeParaAccessControllList();
		codeContent.setCodeAccessControll( list );
		thread = new CodeThread( imgAccess, list );
		timer = new Timer();
		timer.schedule( thread, 500, 250 );
	}

	public CodeParaFilter afterKonstrukt() {
		cl.giveContent( this, codeContent );
		return this;
	}

	protected BufferedImage action( BufferedImage img ) {
		imgAccess.write( img );
		codeContent.paintContent( img );
		return img;
	}

	public void setActive( boolean act ) {
		super.setActive( act );
		thread.setActivate( act );
	}

}