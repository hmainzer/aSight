package filter;

import java.awt.image.BufferedImage;

public class CodeParaAccessControllImg {
	BufferedImage img = null;

	private boolean readerActive;
	private boolean writerActive;
	private boolean writerWaiting;
	
	public BufferedImage read() throws CodeParaNoObjectYetException{
		beforeRead();
		BufferedImage returnImg = img;
		afterRead();
		return returnImg;
	}

	public void write(BufferedImage img){
		beforeWrite();
		this.img = application.Utility.copy(img);
		afterWrite();
	}
	
	private synchronized void beforeRead() throws CodeParaNoObjectYetException{
		while(writerWaiting || writerActive){
			try {
				wait();
			} catch (InterruptedException e) {
				}
		}
		if (img==null)
			throw new CodeParaNoObjectYetException();
		readerActive=true;
	}
	
	private synchronized void afterRead(){
		readerActive=false;
		notifyAll();
	}
	
	private synchronized void beforeWrite(){
		writerWaiting=true;
		while (readerActive || writerActive){
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		writerWaiting=false;
		writerActive=true;
	}
	private synchronized void afterWrite(){
		writerActive=false;
		notifyAll();
	}
}