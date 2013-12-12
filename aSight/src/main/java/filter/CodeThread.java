package filter;

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
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;

/*
 * The image is here scanned for qr- and barcodes. The results are kept for another go, if no results are found, to be displayed a little bit longer (and leave a little room for error in the recognition). The results are loaded send to a ParaAccesControllList to be picked up by the ParaContent. 
 */
public class CodeThread extends TimerTask {

	boolean readQR = false;
	boolean readBar = false;

	boolean activated;
	Timer timer;

	QRCodeMultiReader qrReader = new QRCodeMultiReader();
	GenericMultipleBarcodeReader code128Reader = new GenericMultipleBarcodeReader(
			new Code128Reader());
	GenericMultipleBarcodeReader code39Reader = new GenericMultipleBarcodeReader(
			new Code39Reader());

	GenericMultipleBarcodeReader ean13Reader = new GenericMultipleBarcodeReader(
			new EAN13Reader());
	GenericMultipleBarcodeReader ean8Reader = new GenericMultipleBarcodeReader(
			new EAN8Reader());
	ArrayList<CodeParaContainer> codes = new ArrayList<CodeParaContainer>();
	CodeParaAccessControllList codeAccessControll = new CodeParaAccessControllList();

	// already declared for better memory management
	Result[] result;
	Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
	CodeParaAccessControllImg imgAccess;
	String text;

	public CodeThread(CodeParaAccessControllImg imgAccess,
			CodeParaAccessControllList code) {
		super();
		this.imgAccess = imgAccess;
		this.codeAccessControll = code;
		decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
	}

	public void setActivate(boolean act) {
		this.activated = act;
		if (act == false) {
			codes.clear();
			codeAccessControll.write(codes);
			lastReadSuccessfull = false;
		}
	}

	public void setReadbar(boolean read) {
		this.readBar = read;
	}

	public void setReadQR(boolean read) {
		this.readQR = read;
	}

	private void readBar(GenericMultipleBarcodeReader reader,
			BinaryBitmap bitmap, FontMetrics fontMetrics, Graphics g,
			BufferedImage img) throws NotFoundException {
		result = reader.decodeMultiple(bitmap, decodeHints);
		for (Result tempResult : result) {
			codes.add(new CodeParaContainer(tempResult.getText(),
					(int) tempResult.getResultPoints()[0].getX(),
					(int) tempResult.getResultPoints()[0].getY(),
					(int) tempResult.getResultPoints()[1].getX(),
					(int) tempResult.getResultPoints()[1].getY(),
					(int) fontMetrics.getStringBounds(tempResult.getText(), g)
							.getWidth(), (int) fontMetrics.getStringBounds(
							tempResult.getText(), g).getHeight(), img
							.getWidth(), img.getHeight(),
					(int) (application.Main.getFont().getLineMetrics(
							tempResult.getText(),
							new FontRenderContext(new AffineTransform(), true,
									true)).getAscent())));
		}
	}

	private void readAll(ArrayList<CodeParaContainer> codes,
			BinaryBitmap bitmap, BufferedImage img, Graphics g,
			FontMetrics fontMetrics) throws CodeParaNoObjectYetException {
		if (readQR) {
			try {
				result = qrReader.decodeMultiple(bitmap, decodeHints);
				for (Result tempResult : result) {
					text = tempResult.getText();
					if (text.length() > 50)
						text = text.substring(0, 49) + "...";
					codes.add(new CodeParaContainer(tempResult.getText(),
							(int) tempResult.getResultPoints()[0].getX(),
							(int) tempResult.getResultPoints()[0].getY(),
							(int) tempResult.getResultPoints()[2].getX(),
							(int) tempResult.getResultPoints()[2].getY(),
							(int) fontMetrics.getStringBounds(
									tempResult.getText(), g).getWidth(),
							(int) fontMetrics.getStringBounds(
									tempResult.getText(), g).getHeight(), img
									.getWidth(), img.getHeight(),
							(int) (application.Main.getFont().getLineMetrics(
									tempResult.getText(),
									new FontRenderContext(
											new AffineTransform(), true, true))
									.getAscent())));
				}
			} catch (NotFoundException e) {
			}
		}

		if (readBar) {
			try {
				readBar(code39Reader, bitmap, fontMetrics, g, img);
			} catch (NotFoundException e) {
			}

			try {
				readBar(code128Reader, bitmap, fontMetrics, g, img);
			} catch (NotFoundException e) {
			}
			try {
				readBar(ean13Reader, bitmap, fontMetrics, g, img);
			} catch (NotFoundException e) {
			}

			try {
				readBar(ean8Reader, bitmap, fontMetrics, g, img);
			} catch (NotFoundException e) {
			}
		}
	}

	private boolean lastReadSuccessfull = false;

	public void run() {
		if (activated) {
			try {
				codes.clear();
				BufferedImage img = imgAccess.read();
				LuminanceSource source = new BufferedImageLuminanceSource(img);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
						source));
				Graphics g = img.getGraphics();
				FontMetrics fontMetrics = img.getGraphics().getFontMetrics(
						application.Main.getFont());
				readAll(codes, bitmap, img, g, fontMetrics);

				if (codes.size() > 0) {
					lastReadSuccessfull = true;
					codeAccessControll.write(codes);
				} else if (lastReadSuccessfull) {
					// keeps the results of the last run for one more cycle
					lastReadSuccessfull = false;
				} else {
					codeAccessControll.write(codes);
				}
			} catch (CodeParaNoObjectYetException e1) {
			}

		}
	}
}