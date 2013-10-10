package com.vl.samples.googleMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.vl.samples.R;
import com.vl.samples.gpsLocation.Constants;
import com.vl.samples.gpsLocation.GPSLocation;


//url: http://mobiforge.com/developing/story/using-google-maps-android

public class GoogleMapActivity extends MapActivity{
	MapView mMapView;
	Activity mActivity;
	MapController mMapcontroller;
	ProgressDialog mDialog;
	GeoPoint p;
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.google_map);
		mActivity = this;
		
		mMapView = (MapView)findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);/*shows the built-in zoom controls on UI*/
		mMapcontroller = mMapView.getController();
		
	}

	
	

	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.menu, menu);
		 return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(mMapView.isSatellite()) 
    		mMapView.setSatellite(false);
    	if(mMapView.isStreetView())
    		mMapView.setStreetView(false);
    	if(mMapView.isTraffic())
    		mMapView.setTraffic(false);
		
		
	    switch (item.getItemId()) {
	        case R.id.satilite:
	        	if(!mMapView.isSatellite()) 
	        		mMapView.setSatellite(true);
	        	break;
	        	
	        case R.id.street:
//	        	if(!mMapView.isStreetView()) 
//	        		mMapView.setStreetView(true);
	        	mMapcontroller.zoomToSpan((int)Math.abs(76.899381 - (22.116853)), (int)Math.abs(80.859375 - (110.03906)));
	        	mMapcontroller.animateTo(new GeoPoint( 
	        			(int)(76.899381 + (-22.116853))/2, 
	        			(int)(80.859375 + (-110.03906))/2 )); 
	        	
	        	break;

	        case R.id.traffic:
	        	if(mMapView.isSatellite()) 
	        		mMapView.setSatellite(true);
	        	
	        	if(!mMapView.isTraffic())
	        		mMapView.setTraffic(true);
	        	break;
	        case R.id.current_location:
	        	if(new GPSLocation().getGPSLocation(this)){
	        		mDialog = ProgressDialog.show(mActivity, "", "finding your location....");
	        		MyAsyncTask myAsnc = new MyAsyncTask();
	    			myAsnc.execute();
	    		}else{
	    			if(mDialog!=null)
	    				mDialog.dismiss();
	    			
	    		}
	        	break;
	    }
	    return true;
	}
	
	
	public class MyAsyncTask extends AsyncTask <Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params) {
			int i=0;
			while(Constants.gpslat==null){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
				if(i>=30){
					break;
				}
			}
			
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
//			mDialog.dismiss();
			if(Constants.gpslat!=null && Constants.gpslng!=null){
				//here we get the latitude and Longitude
				Log.d("latitude & longitude",Constants.gpslat+" & "+Constants.gpslng);
				//Toast.makeText(mActivity,"Lat & long"+ Constants.gpslat+" \n "+Constants.gpslng, Toast.LENGTH_LONG).show();
				 p = new GeoPoint(
				            (int) (Double.parseDouble(Constants.gpslat) * 1E6), 
				            (int) (Double.parseDouble(Constants.gpslng) * 1E6));
				 
				 mMapcontroller.animateTo(p);
				 //mMapcontroller.setZoom(17); 
				 mMapView.invalidate();
				
			}else{
				Toast.makeText(mActivity, "Unable to get your location", Toast.LENGTH_LONG).show();
			}
			
		}
	}
}
