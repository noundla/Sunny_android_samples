package com.vl.samples.activityAnimation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout llayout = new LinearLayout(this);
		llayout.setBackgroundColor(Color.WHITE);
		TextView tv = new TextView(this);
		tv.setText("this activity's animation defined through xml (style,theme)");
		
		Button btn = new Button(this);
		btn.setText("close");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		llayout.addView(tv);
		llayout.addView(btn);
		setContentView(llayout);
	}

}
