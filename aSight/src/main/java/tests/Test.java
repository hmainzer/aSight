package tests;
import java.util.List;

import javax.script.*;

public class Test {
	public static void main( String[] args ) {
		// create a script engine manager
		ScriptEngineManager factory = new ScriptEngineManager();
		// create a JavaScript engine
		ScriptEngine engine = factory.getEngineByName( "JavaScript" );
		
		// evaluate JavaScript code from String
		try {
			engine.eval( "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/><title></title><script src=\"http://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load(\"jquery\", \"1.7\");</script><script type=\"text/javascript\" src=\"jsapi.1.0.js\"></script><script type=\"text/javascript\">var options = {category: 7,image_url: 'http://www.ibiblio.org/wm/paint/auth/gogh/gogh.chambre-arles.jpg',precision: 5} NewRevIMGObj = new RevIMG(options, function() {$('#results').append('image URL: <a target=\"_blank\" href=\"' + NewRevIMGObj.data[0][0] + '\">' + NewRevIMGObj.data[0][0] + '</a>');$('#results').append('<br/>Page URL: ' + NewRevIMGObj.data[0][1]);$('#results').append('<br/>Match %: ' + NewRevIMGObj.data[0][2]);$('#results').append('<br/>Description: ' + NewRevIMGObj.data[0][3]);});</script></head><body><div id=\"results\"></div></body></html>" );
		} catch ( ScriptException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
