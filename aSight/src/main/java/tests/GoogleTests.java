package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import tests.GoogleResults.Result;

import com.google.gson.Gson;

public class GoogleTests {
	public static void main( String[] args ) throws Exception {
		
		String charset = "UTF-8";		
		String category = "7";
        String image_url = "http://www.ibiblio.org/wm/paint/auth/gogh/gogh.chambre-arles.jpg";
        String precision = "5";

        URL url = new URL( "http://www.revimg.net/jsapi/index_api.php?category="+category+"&image_url="+encodeURIComponent(image_url)+"&precision="+precision+"&callback=?" );
	
		Reader reader = new InputStreamReader( url.openStream(), charset );
		filter.PictureSearchThread.generateResults(reader);
		
		/*
		BufferedReader br = new BufferedReader( reader );
		
		boolean loop = true;
		String line;
		while ( loop ){
			line = br.readLine();			
			if ( line == null ){
				loop = false;
			} else {
				System.out.println(line);
			}
		}*/
		/*
		
		GoogleResults results = new Gson().fromJson( reader, GoogleResults.class );

		// Show title and URL of 1st result.
			
		List<Result> rslt = results.getResponseData().getResults();
		int i = 0;
		for ( Result r : rslt ){
			System.out.println( r.getTitle() );	
			System.out.println( r.getUrl() );
			System.out.println("");
			i++;
			if ( i > 30 ){
				break;
			}
		}*/

	}
	 public static String encodeURIComponent(String s)
	  {
	    String result = null;

	    try
	    {
	      result = URLEncoder.encode(s, "UTF-8")
	                         .replaceAll("\\+", "%20")
	                         .replaceAll("\\%21", "!")
	                         .replaceAll("\\%27", "'")
	                         .replaceAll("\\%28", "(")
	                         .replaceAll("\\%29", ")")
	                         .replaceAll("\\%7E", "~");
	    }

	    // This exception should never occur.
	    catch (UnsupportedEncodingException e)
	    {
	      result = s;
	    }

	    return result;
	  }  
}
