package com.vl.samples.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.vl.samples.R;

public class DrawableAnimationActivity extends Activity {
	AnimationDrawable rocketAnimation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawable_anim);
			ImageView rocketImage = (ImageView) findViewById(R.id.rocket_image);
		  rocketImage.setBackgroundResource(R.drawable.rocket_thrust);
		  rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
	}
	
	/**
	 * If we need to start our animation, we have wait upto user interaction. 
	 * Because we must call the start() method on AnimationDrawable object to start animation.
	 * Ex: rocketAnimation.start();
	 * */
	/*@Override
	public boolean onTouchEvent(MotionEvent event) {
		  if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    rocketAnimation.start();
		    return true;
		  }
		  return super.onTouchEvent(event);
	}*/
	
	/**The below method will be called when the activity get/loss focus. 
	 * So we can start our animation in this by without user interaction.
	 * */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			rocketAnimation.start();
		}
		super.onWindowFocusChanged(hasFocus);
	}
}
