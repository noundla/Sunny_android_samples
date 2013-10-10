package com.vl.samples.gpsLocation;

import java.util.List;
import java.util.Locale;

import com.vl.samples.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocationActivity extends Activity{
	TextView mFindLoc,mLatLong;
	Activity mActivity;
	private ProgressDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_location);
		mActivity = this;
		
		mFindLoc = (TextView)findViewById(R.id.your_loc_tv);
		mFindLoc.setVisibility(View.VISIBLE);
		
		mLatLong = (TextView)findViewById(R.id.lat_long_tv);
		mLatLong.setVisibility(View.VISIBLE);
		
		mDialog = ProgressDialog.show(this, "", "Loading..."); 
		mDialog.setCancelable(true);
		
		
		if(new GPSLocation().getGPSLocation(this)){
			mFindLoc.setText("finding your location....");
			
			MyAsyncTask myAsnc = new MyAsyncTask();
			myAsnc.execute();
		}else{
			mDialog.dismiss();
			mFindLoc.setText("please try again....");
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if(Constants.IS_FROM_GPSLOCATION){
			
			Constants.IS_FROM_GPSLOCATION = false;
			try {
				//Settings are taking time to update thats why sleep is used here.
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mDialog = ProgressDialog.show(this, "", "Loading..."); 
			mDialog.setCancelable(true);
			if(new GPSLocation().getGPSLocation(this)){
				mFindLoc.setText("finding your location....");
				
				MyAsyncTask myAsnc = new MyAsyncTask();
				myAsnc.execute();
			}else{
				mDialog.dismiss();
				mFindLoc.setText("please try again....");
			}
		}
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
				Toast.makeText(mActivity,"Lat & long"+ Constants.gpslat+" \n "+Constants.gpslng, Toast.LENGTH_LONG).show();
				mLatLong.setText("lat & long: "+Constants.gpslat+" , "+Constants.gpslng);
				
				//The following call uses the network not the GPS
				//finding current location address
				Context context = getApplicationContext(); 
				ReverseGeocodeLookupTask task = new ReverseGeocodeLookupTask();
				task.applicationContext = context;
				task.activityContext = mActivity;
				task.execute();
			}else{
				Toast.makeText(mActivity, "Unable to get your location", Toast.LENGTH_LONG).show();
			}
			
		}
	}
	
	public class ReverseGeocodeLookupTask extends AsyncTask <Void, Void, String>{
		protected Context applicationContext;
		protected Context activityContext;

		@Override
		protected String doInBackground(Void... params) {
			String foundAddress = "";
			double lat = Double.parseDouble(Constants.gpslat);//passed.get(0);
			double lon = Double.parseDouble(Constants.gpslng);//passed.get(1);
			try
			{
				List<Address> addresses = new Geocoder(applicationContext,Locale.getDefault()).getFromLocation(lat,lon, 1);
				if (addresses.size() > 0)
				{
					String addressLine = "";

					for(Integer i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++){
						if(!addressLine.equals("")){
							addressLine+= ", ";
						}
						addressLine+=  addresses.get(0).getAddressLine(i);
					}
					if(addressLine!=""){
						foundAddress+= addressLine;
					}
				}
			}
			catch (Exception e) {
				//here exception may be raised as... "unable to parse response from server"
				Log.d("Exception finding location address", e.getMessage());
				return null;
			}

			return foundAddress;
		}
		@Override
		protected void onPostExecute(String result)
		{
			
			if(result!=null){
				
				if(Constants.LAST_KNOWN_LOCATION){
					Toast.makeText(mActivity, "These detials are about last known location.", Toast.LENGTH_LONG);
				}
				Log.d("latitude & longitude",Constants.gpslat+" & "+Constants.gpslng);
				mFindLoc.setText("Address:"+result);
				
			}else{
				//Its better to show an alert here.
				
				
				mFindLoc.setText("Unable to determine the Current Location(Lat & Longs) based address.");
			}
			
			mDialog.dismiss();
		}
	}
    
    
    
}
