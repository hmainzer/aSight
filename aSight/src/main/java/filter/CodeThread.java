package filter;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;

public class CodeThread extends TimerTask{
	boolean readQR = false;
/*	boolean readBar39 = true;
	boolean readBar128 = true;
	boolean readEan8 = true;
	boolean readEan13 = true;*/
	boolean readBar = false;
	
	boolean fisheye = false;
	double factorFisheye = 1.66;
	
	boolean activated;
	Timer timer;
	
	QRCodeMultiReader qrReader = new QRCodeMultiReader();
	GenericMultipleBarcodeReader code128Reader = new GenericMultipleBarcodeReader(
			new Code128Reader());
	GenericMultipleBarcodeReader code39Reader = new GenericMultipleBarcodeReader(
			new Code39Reader());

	DataMatrixReader dataMatrixReader = new DataMatrixReader();
	GenericMultipleBarcodeReader ean13Reader = new GenericMultipleBarcodeReader(new EAN13Reader());
	GenericMultipleBarcodeReader ean8Reader = new GenericMultipleBarcodeReader(new EAN8Reader());
	ArrayList<CodeParaContainer> codes = new ArrayList<CodeParaContainer>();
	CodeParaAccessControllList codeAccessControll = new CodeParaAccessControllList();
	
	// already declared for better memory management
	Result[] result;
	Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
	CodeParaAccessControllImg imgAccess;
	String text;
	
	public CodeThread(CodeParaAccessControllImg imgAccess, CodeParaAccessControllList code) {
		super();
		this.imgAccess = imgAccess;
		this.codeAccessControll = code;
		decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
	}
	
	public void setActivate(boolean act){
		this.activated = act;
		if (act==false){
			codes.clear();
			codeAccessControll.write(codes);
		}
	}
	
	public void setReadbar(boolean read){
		this.readBar = read;
	}
	
	public void setReadQR(boolean read){
		this.readQR = read;
	}
	
	static class Coordinates{
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

	
	
	//fisheye-Stuff follows
	
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
	
	//end of fisheye-stuff
	
	public void run() {
		if (activated){
		try {
			BufferedImage img = imgAccess.read();
			codes.clear();
			if (fisheye)
				img = fisheye(img, factorFisheye);
			LuminanceSource source = new BufferedImageLuminanceSource(img);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Graphics g = img.getGraphics();
			FontMetrics fontMetrics = img.getGraphics().getFontMetrics(application.Main.getFont());

			if (readQR) {
				try {
					result = qrReader.decodeMultiple(bitmap,decodeHints);
					for (Result tempResult : result) {
						text = tempResult.getText();
						if (text.length()>50)
							text = text.substring(0,49) + "...";
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[2].getX(), (int)tempResult.getResultPoints()[2].getY(), (int)fontMetrics.getStringBounds(tempResult.getText(), g).getWidth(),(int)fontMetrics.getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(application.Main.getFont().getLineMetrics(tempResult.getText(),new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {}
			}
			
			if (readBar) {
				try {
					result = code39Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)fontMetrics.getStringBounds(tempResult.getText(), g).getWidth(),(int)fontMetrics.getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(application.Main.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}

				try {
					result = code128Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)fontMetrics.getStringBounds(tempResult.getText(), g).getWidth(),(int)fontMetrics.getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(application.Main.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
				try {
					result = ean13Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)fontMetrics.getStringBounds(tempResult.getText(), g).getWidth(),(int)fontMetrics.getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(application.Main.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
				
				try {
					result = ean8Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)fontMetrics.getStringBounds(tempResult.getText(), g).getWidth(),(int)fontMetrics.getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(application.Main.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
			}
			codeAccessControll.write(codes);
		} catch (CodeParaNoObjectYetException e1) {
		}
		
		}
	}
	
}
