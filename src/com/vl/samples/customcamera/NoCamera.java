package com.vl.samples.customcamera;



import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.vl.samples.R;
/**
 * This screen will display when there is no camera when we click on camera in the application .
 * 
 *
 */
public class NoCamera extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("no camera");
		setContentView(tv);
	}
}
