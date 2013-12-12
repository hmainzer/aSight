package filter;

import java.util.ArrayList;

/*
 * ParaAccessControllList is a fairly standart access controll (as learned in para). Here the CodeParaContainers are send back to the contentLayer, which displays them. If there is no Object to display there yet - it happens sometimes - an error is thrown.
 * */
public class CodeParaAccessControllList {
	ArrayList<CodeParaContainer> codes = null;

	private boolean readerActive;
	private boolean writerActive;
	private boolean writerWaiting;	

	
	public ArrayList<CodeParaContainer> read() throws CodeParaNoObjectYetException{
		beforeRead();
		ArrayList<CodeParaContainer> returnList = codes;
		afterRead();
		return returnList;
	}

	public void write(ArrayList<CodeParaContainer> toWrite){
		beforeWrite();
		codes = new ArrayList<CodeParaContainer>(toWrite);
		afterWrite();
	}
	
	private synchronized void beforeRead() throws CodeParaNoObjectYetException{
		while(writerWaiting || writerActive){
			try {
				wait();
			} catch (InterruptedException e) {
				}
		}
		if (codes==null){
			throw new CodeParaNoObjectYetException();
		}
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