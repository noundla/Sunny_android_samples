package com.vl.samples.googleMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class DirectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse("http://maps.google.com/maps?saddr=17.447192,78.371336&daddr=17.463154, 78.424753"));
		startActivity(intent);
	}
}
