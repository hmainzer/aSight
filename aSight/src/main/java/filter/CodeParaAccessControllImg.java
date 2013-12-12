package filter;

import java.awt.image.BufferedImage;

/*
 * The AccessControll makes it possible for the reader (CodeThread) to request the next possible camera image
 * */
public class CodeParaAccessControllImg {
	BufferedImage img = null;

	private boolean readerWaiting;
	
	/*
	 * Here, the reader waits for an image to be uploaded to the access control. As soon as the image is uploaded, it is returned to the waiting process
	 * 
	 */
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
	
	/*
	 * Here the image is uploaded, if there is a reader waiting for it
	 * 
	 * */
	public synchronized void write(BufferedImage img){
		if (readerWaiting){
			this.img = application.Utility.copy(img);
			readerWaiting = false;	
			notifyAll();
		}
	}
}