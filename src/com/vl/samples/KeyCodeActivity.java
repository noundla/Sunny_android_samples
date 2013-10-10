package com.vl.samples;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class KeyCodeActivity extends Activity {
	boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_HOME){
			flag = true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(flag){
			Log.d("fsdf", "dfsafdf");
			Toast.makeText(this, "home button pressed", Toast.LENGTH_LONG);
		}
		return super.onKeyUp(keyCode, event);
	}
	/*@Override
	protected void onPause() {
		if(flag){
			Log.d("fsdf", "dfsafdf");
			Toast.makeText(this, "home button pressed", Toast.LENGTH_LONG);
		}
		super.onPause();
	}*/
}
