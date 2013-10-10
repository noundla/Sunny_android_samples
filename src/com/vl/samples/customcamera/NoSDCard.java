package com.vl.samples.customcamera;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * This screen will display when there is no sdcard when we click on camera in the application .
 * 
 *
 */
public class NoSDCard extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("no camera");
		setContentView(tv);
	}
}
