package filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FilterFisheye extends AbstractFilter{

	class Coordinates{
		int x;
		int y;
		public Coordinates(int x, int y) {
			this.x=x;
			this.y=y;
		}
		
		int getX(){
			return x;
		}
		
		int getY(){
			return y;
		}
	}
	
	double dist(int x, int y){
		return Math.sqrt((x*x) + (y*y));
	}
	
	Coordinates defisheye(int srcWidth, int srcHeight, int targetWidth, int targetHeight, int x, int y, double factor){
		//dx, dy zu relativen koordinaten
		int rx = x - (targetWidth/2);
		int ry = y - (targetHeight/2);
		//berechne theta
		double r = dist(rx,ry)/(dist(srcWidth,srcHeight)/factor);
		
		double theta;
		
		if (r == 0)
			theta = 1.0;
		else
			theta = Math.atan(r)/r;
		double sx = (srcWidth/2) + theta*rx;
		double sy = (srcHeight/2) + theta*ry;
		return new Coordinates((int)Math.round(sx), (int)Math.round(sy));
	}
	
	BufferedImage fisheye(BufferedImage image, double factor){
		BufferedImage returnImage = new BufferedImage((int)(image.getWidth()), (int)(image.getHeight()), image.getType());
		for (int y=0; y<returnImage.getHeight(); y++){
			for (int x=0; x<returnImage.getWidth(); x++){
				Coordinates newPoint = defisheye(image.getWidth(), image.getHeight(), returnImage.getWidth(), returnImage.getHeight(), x, y, factor);
				try {
					returnImage.setRGB(x, y, image.getRGB(newPoint.getX(), newPoint.getY()));
				} catch (ArrayIndexOutOfBoundsException ex) {
					returnImage.setRGB(x, y, Color.red.getRGB());
				}
			}
		}
		return returnImage;
	}
	
	
	protected BufferedImage action(BufferedImage img) {
		img = fisheye(img, 1.66);
		return img;
	}

}
