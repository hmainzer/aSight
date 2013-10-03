package filter;

import java.awt.image.BufferedImage;

public class CodeParaAccessControllImg {
	BufferedImage img = null;

	private boolean readerWaiting;
	
	public synchronized BufferedImage read() throws CodeParaNoObjectYetException{
		readerWaiting=true;
		while (readerWaiting){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public synchronized BufferedImage write(BufferedImage img){
		if (readerWaiting){
			this.img = application.Utility.copy(img);
			readerWaiting = false;	
			notifyAll();
		}
		return img;
	}
}