package application;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment; 
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import filter.*;

public class Main {
	
	private ArrayList<filter.Filter> filterList;
	private filter.Filter adjustmentFilter;
	private filter.ContentLayer contentLayer;
	
	public static void main( String[] args ) {
		ArrayList<filter.Filter> filterList = new ArrayList<filter.Filter>();
		filterList.add( new AdjustmentFilter() );
		filterList.add( new TestFilter() );
		
		filter.ContentLayer cl = new filter.ContentLayer();
		filterList.add( cl );
		
		filterList.get( 1 ).setActive( true ); //%%
		Gui.createGui( new Main(filterList, filterList.get( 0 ), cl) );
	}	
	
	public Main(ArrayList<filter.Filter> filter, filter.Filter adjustmentFilter, filter.ContentLayer contentLayer ){
		this.filterList = filter;
		this.adjustmentFilter = adjustmentFilter;
		this.contentLayer = contentLayer;
	}
	
	public ArrayList<filter.Filter> getFilter(){
		return filterList;
		
	}
	
	public filter.Filter getAdjustmentFilter(){
		return adjustmentFilter;
		
	}
	
	public filter.ContentLayer getContentLayer(){
		return contentLayer;
	}
	
	public Vector<Integer> getCountInputDevices(){
		Vector<Integer> v = new Vector<Integer>();
		v.add( 1 );
		return v;
	}
	
	public Vector<Integer> getCountOutputDevices(){
		Vector<Integer> v = new Vector<Integer>();
		v.add( 1 );
		v.add( 2 );
		return v;
	}
	
	public void applyFilters(BufferedImage in, VideoField out){
		Graphics2D graphic = in.createGraphics();
		for ( filter.Filter filter : filterList ) {
			filter.useFilter( in, graphic );
		}
		out.setImage( in );
	}
}
