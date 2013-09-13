package application;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;
import filter.*;

public class Main {

	private ArrayList<filter.Filter> filterList;
	private filter.Filter adjustmentFilter;
	private filter.ContentLayer contentLayer;

	public static void main( String[] args ) {
		filter.ContentLayer cl = new filter.ContentLayer();
		ArrayList<filter.Filter> filterList = new ArrayList<filter.Filter>();
		filterList.add( new AdjustmentFilter( 100, 100, 1280, 1024 ) );
		filterList.add( new ZoomFilter() );
		filterList.add( new TestFilter( cl ) );
		filterList.add( new CodeParaFilter( cl ).afterKonstrukt());

		filterList.add( cl );

		Gui.createGui( new Main( filterList, filterList.get( 0 ), cl ) );
	}

	public Main( ArrayList<filter.Filter> filter, filter.Filter adjustmentFilter, filter.ContentLayer contentLayer ) {
		this.filterList = filter;
		this.adjustmentFilter = adjustmentFilter;
		this.contentLayer = contentLayer;
	}

	public ArrayList<filter.Filter> getFilter() {
		return filterList;
	}

	public filter.Filter getAdjustmentFilter() {
		return adjustmentFilter;
	}

	public filter.ContentLayer getContentLayer() {
		return contentLayer;
	}

	public Vector<Integer> getCountInputDevices() {
		Vector<Integer> v = new Vector<Integer>();
		v.add( 0 );
		v.add( 1 );
		v.add( 2 );
		return v;
	}

	public Vector<Integer> getCountOutputDevices() {
		Vector<Integer> v = new Vector<Integer>();
		GraphicsDevice[] monitorArray = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		v.add( 0 );
		for ( int i = 1; i <= monitorArray.length; i++ ) {
			v.add( i );
		}
		return v;
	}

	public void applyFilters( BufferedImage in, VideoField out ) {
		for ( Filter filter : filterList ) {
			in = filter.useFilter( in );
		}
		out.setImage( in );
	}
}
