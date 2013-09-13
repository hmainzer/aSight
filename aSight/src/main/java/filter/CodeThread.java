package filter;

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
	boolean readQR = true;
	boolean readBar39 = true;
	boolean readBar128 = true;
	boolean readEan8 = true;
	boolean readEan13 = true;
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
	}
	
	public void run() {

		if (activated){
		try {
			BufferedImage img = imgAccess.read();
			codes.clear();
			LuminanceSource source = new BufferedImageLuminanceSource(img);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			Graphics g = img.getGraphics();

			if (readQR) {
				try {
					result = qrReader.decodeMultiple(bitmap,decodeHints);
					for (Result tempResult : result) {
						text = tempResult.getText();
						if (text.length()>50)
							text = text.substring(0,49) + "...";
						codes.add(new CodeParaContainer(text, (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[2].getX(), (int)tempResult.getResultPoints()[2].getY(), (int)g.getFontMetrics().getStringBounds(text, g).getWidth(),(int)g.getFontMetrics().getStringBounds(text, g).getHeight(),img.getWidth(), img.getHeight(),(int)(g.getFont().getLineMetrics(text, new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {}
			}
			
			if (readBar39) {
				try {
					result = code39Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getWidth(),(int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(g.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
			}

			if (readEan8) {
				try {
					result = code128Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getWidth(),(int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(g.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
			}
				
			if (readEan13){
				try {
					result = ean13Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getWidth(),(int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(g.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
					}
				} catch (NotFoundException e) {
				}
				
				try {
					result = ean8Reader.decodeMultiple(bitmap, decodeHints);
					for (Result tempResult : result) {
						codes.add(new CodeParaContainer(tempResult.getText(), (int)tempResult.getResultPoints()[0].getX(), (int)tempResult.getResultPoints()[0].getY(), (int)tempResult.getResultPoints()[1].getX(), (int)tempResult.getResultPoints()[1].getY(), (int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getWidth(),(int)g.getFontMetrics().getStringBounds(tempResult.getText(), g).getHeight(),img.getWidth(), img.getHeight(),(int)(g.getFont().getLineMetrics(tempResult.getText(), new FontRenderContext(new AffineTransform(),true,true)).getAscent())));
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
