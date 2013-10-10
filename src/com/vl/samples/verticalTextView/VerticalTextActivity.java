package com.vl.samples.verticalTextView;


import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.vl.samples.R;

public class VerticalTextActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vertical_text);
		
		RotateAnimation ranim = (RotateAnimation)AnimationUtils.loadAnimation(this, R.anim.rotateanim);
		ranim.setFillAfter(true); //For the textview to remain at the same place after the rotation
		((TextView)findViewById(R.id.textView1)).setAnimation(ranim);
	}
}
