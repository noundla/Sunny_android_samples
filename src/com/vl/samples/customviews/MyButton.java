package com.vl.samples.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MyButton extends Button {

	public MyButton(Context context) {
		super(context);
		getBackground().setDither(true);
		init();
	}
	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		getBackground().setDither(true);
		init();
	}
	public MyButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getBackground().setDither(true);
		init();
		
	}
	
	
	private void init(){
		if(getBackground() instanceof StateListDrawable){
			Log.d("ss", "sssssss-state");
			return;
		}
		Log.d("ss", "sssssss-non-state");
		StateListDrawable listDrawable = new StateListDrawable();
		Drawable drawable = getBackground();
		
		listDrawable.addState(PRESSED_ENABLED_STATE_SET, drawable);
		listDrawable.addState(EMPTY_STATE_SET, getBackground());
		setBackgroundDrawable(drawable);
	}
	
	

}
