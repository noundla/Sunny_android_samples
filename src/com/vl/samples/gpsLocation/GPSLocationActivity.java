package com.vl.samples.gpsLocation;

import com.vl.samples.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GPSLocationActivity extends Activity {
    /** Called when the activity is first created. */
    Button mFindGps,mClearLatLong;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_location);
        
        mFindGps = (Button) findViewById(R.id.find_gps_loc_btn);
        mFindGps.setVisibility(View.VISIBLE);
        mFindGps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("lattitue & longitude",Constants.gpslat+" & "+Constants.gpslng);
				Constants.gpslat=null;
				Constants.gpslng=null;
				Log.d("lattitue & longitude cleared..",Constants.gpslat+" & "+Constants.gpslng);
				Intent intent = new Intent(GPSLocationActivity.this,ShowLocationActivity.class);
				startActivity(intent);
				
			}
		});
        
        mClearLatLong = (Button) findViewById(R.id.clear_lat_longs);
        mClearLatLong.setVisibility(View.VISIBLE);
        mClearLatLong.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Constants.gpslat=null;
				Constants.gpslng=null;
				Log.d("lattitue & longitude",Constants.gpslat+" & "+Constants.gpslng);
			}
			
			
		});
    }
}