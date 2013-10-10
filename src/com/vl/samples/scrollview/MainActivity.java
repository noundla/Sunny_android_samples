package com.vl.samples.scrollview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vl.samples.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void fillContent(View view){
		Intent intent = new Intent(this, FillContentScrollViewActivity.class);
		startActivity(intent);
	}
	
	public void listviewToScroll(View view){
		Intent intent = new Intent(this, ListViewInScrollviewActivity.class);
		startActivity(intent);
	}
	
	public void simpleScroll(View view){
		Intent intent = new Intent(this, SimpleScrollActivity.class);
		startActivity(intent);
	}
	public void horizontalScroll(View view){
		Intent intent = new Intent(this, HorizontalScrollActivity.class);
		startActivity(intent);
	}
	public void scrollWeight(View view){
		Intent intent = new Intent(this, ScrollWithWeightActivity.class);
		startActivity(intent);
	}
	
	
	

}
