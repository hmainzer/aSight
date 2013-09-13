package tests;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import tests.GoogleResults.Result;

import com.google.gson.Gson;

public class GoogleTests {
	public static void main( String[] args ) throws Exception {
		//String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		
	    String google = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
		String search = "bunny";
		String charset = "UTF-8";

		URL url = new URL( google + URLEncoder.encode( search, charset ) );
		Reader reader = new InputStreamReader( url.openStream(), charset );
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
		}

	}
}
