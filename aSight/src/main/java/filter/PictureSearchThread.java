package filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import application.FtpManager;

public class PictureSearchThread extends Thread {

	private ContentLayer cl;
	private BufferedImage img;
	private PictureSearchFilter f;
	private int precision = 5, category = 0;

	public PictureSearchThread( BufferedImage img, ContentLayer cl, PictureSearchFilter f, int category, int precision ) {
		this.cl = cl;
		this.img = img;
		this.f = f;
		this.precision = precision;
		this.category = category;
	}

	public void run() {

		LoadingBarContent load = new LoadingBarContent( -5, f, 100, 400, "saving", Color.green,
				application.Main.getFont() );
		cl.giveContent( f, load );
		File outputfile = new File( "ImageSearch/Image_" + System.currentTimeMillis() + ".jpg" );
		try {
			ImageIO.write( img, "jpg", outputfile );
			load.setString( "uploading" );
			String image_url = FtpManager.uploadFileToFtp( "ftp.asight.bplaced.net", "asight", "DummyPW123",
					outputfile.getCanonicalPath(), outputfile.getName(), "" );
			load.setString( "searching" );
			String charset = "UTF-8";
			String category = Integer.toString( this.category );
			String precision = Integer.toString( this.precision );

			URL url = new URL( "http://www.revimg.net/jsapi/index_api.php?category=" + category + "&image_url="
					+ application.Utility.encodeURIComponent( image_url ) + "&precision=" + precision + "&callback=?" );
			ArrayList<String> rslt = generateResults( new InputStreamReader( url.openStream(), charset ) );
			cl.setTimeoutForFilter( f, 1 );
			load.setInactiveAndKill();
			for ( int i = 0; i < 3 && i < rslt.size(); i++ ) {
				cl.giveContent( f, new StringContent( 60, f, rslt.get( i ), 100, 400 + i * 24, Color.GREEN,
						application.Main.getFont() ) );
			}
			FtpManager.deleteFileFromFtp( "ftp.asight.bplaced.net", "asight", "DummyPW123", outputfile.getName(), "" );

		} catch ( IOException e ) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> generateResults( Reader results ) {
		BufferedReader bRslt = new BufferedReader( results );
		String resultString = "";
		boolean loop = true;
		String line = null;
		while ( loop ) {
			try {
				line = bRslt.readLine();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			if ( line != null ) {
				resultString = resultString + line;
			} else {
				loop = false;
			}
		}

		ArrayList<String> resultList = new ArrayList<String>();
		StringBuffer rsltBuffer = new StringBuffer( resultString );
		int p1 = 0, p2 = 0, t1 = 0, t2 = 0;
		p1 = rsltBuffer.indexOf( "[" ) + 1;
		loop = true;
		String tmp, tmpPercent;
		while ( loop ) {
			p2 = rsltBuffer.indexOf( "]", p1 );
			if ( p2 >= 0 ) {
				tmp = rsltBuffer.substring( p1 + 1, p2 - 1 );
				t1 = tmp.indexOf( ",", tmp.indexOf( "," ) + 1 );
				t2 = tmp.indexOf( ",", t1 + 1 );
				tmpPercent = tmp.substring( t1 + 1, t2 - 1 ) + "%";
				tmp = tmpPercent + " - " + tmp.substring( t2 + 2 );
				resultList.add( tmp );
				p1 = p2 + 2;
			} else {
				loop = false;
			}
		}
		return resultList;
	}

}
