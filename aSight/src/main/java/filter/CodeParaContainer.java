package filter;

import java.awt.Color;
import java.awt.Graphics;

public class CodeParaContainer {
	private int xText;
	private int yText;
	private String text;
	private static Color bgColor = new Color(1.0f, 1.0f, 1.0f , 0.9f);
	private static Color textColor = Color.black;
	
	int borderX;
	int borderY;
	int borderWidth;
	int borderHeight;
	
	public CodeParaContainer(String text, int x1, int y1, int x2, int y2, int stringWidth, int stringHeight, int imgWidth, int imgHeight, int ascent) {
		
		// gets the center of the Text in the center of the two given points
		xText = ((x1+x2)/2) - (stringWidth/2);
		yText = ((y1+y2)/2) + (ascent/2);
		
		//prevents the text from going over the left or right edges of the graphic. Should be, due to the nature of the code, not be a problem for the top or bottom edge
		if (xText+stringWidth>imgWidth){
			xText=imgWidth-stringWidth;
		}
		if (xText<0){
			xText=0;
		}
		
		borderX=xText-2;
		borderY=yText-ascent-2;
		borderWidth=stringWidth+4;
		borderHeight=stringHeight+4;
		this.text = text;
	}
	
	public void paintContent(Graphics g){
		g.setColor(bgColor);
		
		g.fillRect(borderX, borderY, borderWidth, borderHeight);
		g.setColor(textColor);
		g.drawString(text, xText, yText);
		
		g.setColor(Color.red);
	}
	
}
