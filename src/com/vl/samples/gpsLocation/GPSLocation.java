package com.vl.samples.gpsLocation;

import java.util.Timer;
import java.util.TimerTask;

import com.vl.samples.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;


public class GPSLocation {
	
	private Timer mTimer1;
    private LocationManager mLocationManager;
    private boolean isGpsEnabled=false;
    private boolean isNetworkEnabled=false;
    private AlertDialog.Builder mDialog;

	 
	public boolean getGPSLocation(final Context context){
		if(mLocationManager==null)
			mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    	/* Returns the current enabled/disabled status of the given provider. 
		 * If the user has enabled this provider in the Settings menu, true is returned 
		 * otherwise false is returned. */
		try{
			 isGpsEnabled=mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    }catch(Exception ex){
	      	//Exception: No suitable permission is present for the provider.
	       	Constants.PROVIDER_EXCEPTION = ex.getMessage();
	       	return false;
	    }
		try{
			isNetworkEnabled=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){
        	Constants.PROVIDER_EXCEPTION = ex.getMessage();
        	return false;
        }
		
		
        if(Constants.LOG)Log.d("Is provider enabled?", "GPS_PROVIDER : "+isGpsEnabled+" & network_provider : "+isNetworkEnabled);

        //don't start listeners if no provider is enabled
        if(!isGpsEnabled && !isNetworkEnabled){
        	mDialog = new AlertDialog.Builder(context);
        	mDialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
        	mDialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
                    context.startActivity(myIntent);
                    
                    Constants.IS_FROM_GPSLOCATION=true;
                    //checkForProviders(mLocationManager);
                    //to check once more for providers
                    //getGPSLocation(context);
				}
			});
        	mDialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					//what you wanna show if user has clicked cancel button?
					//It is based on project requirement.
				}
			});
        	mDialog.show();
        	return false;
        }
         if(isGpsEnabled)
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
         
        if(isNetworkEnabled)
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        mTimer1=new Timer();
        //Here the GetLastLocation class will be executed after 20 seconds, if no update location found using above.
        mTimer1.schedule(new GetLastLocation(), 20000);
        return true;
    }
	
	

    LocationListener locationListenerGps = new LocationListener() {
        @Override
		public void onLocationChanged(Location location) {
        	if(mTimer1!=null)
        		mTimer1.cancel();/*stopping the timer, because we got the new location details.*/
            gotLocation(location);
            if(mLocationManager!=null){
	            mLocationManager.removeUpdates(this);
	            mLocationManager.removeUpdates(locationListenerNetwork);
            }
        }
        @Override
		public void onProviderDisabled(String provider) {}
        @Override
		public void onProviderEnabled(String provider) {}
        @Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    

    LocationListener locationListenerNetwork = new LocationListener() {
        @Override
		public void onLocationChanged(Location location) {
        	if(mTimer1!=null)
        		mTimer1.cancel();/*stopping the timer, because we got the new location details.*/
            gotLocation(location);
            if(mLocationManager!=null){

            mLocationManager.removeUpdates(this);
            mLocationManager.removeUpdates(locationListenerGps);
            }
        }
        @Override
		public void onProviderDisabled(String provider) {}
        @Override
		public void onProviderEnabled(String provider) {}
        @Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    
    /*This is used to store the latest location details.*/
    public void gotLocation(Location location){
    	if(location!=null){
	    	Constants.gpslat = location.getLatitude()+"";
	        Constants.gpslng = location.getLongitude()+"";
	        Log.d("location changed: lattitue & longitude",Constants.gpslat+" & "+Constants.gpslng);
    	}
    }
	 
    class GetLastLocation extends TimerTask {
    	 private boolean isGpsEnabled=false;
    	 private boolean isNetworkEnabled=false;
        @Override
        public void run() {
        	/* Removes any current registration for location updates of the current activity
        	 * with the given LocationListener. 
        	 * Following this call, updates will no longer occur for this listener.*/
             mLocationManager.removeUpdates(locationListenerGps);
             mLocationManager.removeUpdates(locationListenerNetwork);
             
             try{
            	 isGpsEnabled=mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
             }catch(Exception ex){
             	//Exception: No suitable permission is present for the provider.
             	Constants.PROVIDER_EXCEPTION = ex.getMessage();
             }
             try{
            	 isNetworkEnabled=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
             }catch(Exception ex){
             	Constants.PROVIDER_EXCEPTION = ex.getMessage();
             }

             Location net_loc=null, gps_loc=null;

             if(isGpsEnabled){
            	 /*Returns a Location indicating the data from the last known location fix obtained 
            	  * from the given provider. This can be done without starting the provider. 
            	  * Note that this location could be out-of-date, 
            	  * for example if the device was turned off and moved to another location.*/
                 gps_loc=mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                 Constants.LAST_KNOWN_LOCATION = true;
             }
             if(isNetworkEnabled){
                 net_loc=mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                 Constants.LAST_KNOWN_LOCATION = true;
             }
             
             //if there are both values then use the latest one.
             if(gps_loc!=null && net_loc!=null){
                 if(gps_loc.getTime()>net_loc.getTime())
                     gotLocation(gps_loc);
                 else
                     gotLocation(net_loc);
                 return;
             }

             if(gps_loc!=null){
                 gotLocation(gps_loc);
                 return;
             }
             
             if(net_loc!=null){
                 gotLocation(net_loc);
                 return;
             }
             gotLocation(null);
        }
    }
	 
	 
    
		 
		 
		 
		 
		 
		 
		 

    
    
    /*Note: In the above we are storing the location details in the Constants static variables. 
     * So here it is unnecessary to use the  GetLastLocation class. 
     * But this class will be helpful when the static data has been removed like clearing data in device. 
     * */
}
