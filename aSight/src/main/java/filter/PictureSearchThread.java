package filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.imageio.ImageIO;

public class PictureSearchThread extends Thread {

	private ContentLayer cl;
	private BufferedImage img;
	private PictureSearchFilter f;

	public PictureSearchThread( BufferedImage img, ContentLayer cl, PictureSearchFilter f ) {
		this.cl = cl;
		this.img = img;
		this.f = f;
	}

	public void run() {

		File outputfile = new File( "Image_" + System.currentTimeMillis() + ".jpg" );
		try {
			ImageIO.write( img, "jpg", outputfile );
		} catch ( IOException e ) {
			e.printStackTrace();
		}

		cl.giveContent( f, new StringContent( 60, f, "Picture Search Result Test", 400, 300, Color.GREEN,
				application.Main.getFont() ) );
	}

	public static void main( String[] args ) {
		File save_path = new File( "searchImage.jpg" );
		// ImageIO.write(img, "JPG", save_path);

		try {
			upload( save_path );
		} catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void upload( File file ) throws Exception {

		MultipartEntity entity = new MultipartEntity();
		entity.addPart( "encoded_image", new InputStreamBody( new FileInputStream( file ), file.getName() ) );

		HttpPost post = new HttpPost( "https://www.google.com/searchbyimage/upload" );
		post.setEntity( entity );

		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute( post );
		org.apache.http.Header[] h = response.getAllHeaders();
		for ( Header he : h ) {
			System.out.println( he.getName() + " ~~~ " );
			System.out.println( he.getValue() );
		}
		// String site = response.getFirstHeader( "location" ).getValue();
		// Runtime.getRuntime().exec( "cmd /c start " + site );
		// temp.dispose();
		// lock=false;
	}

}
