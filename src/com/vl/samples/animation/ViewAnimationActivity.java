package com.vl.samples.animation;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vl.samples.R;


/**
 * http://developer.android.com/guide/topics/graphics/view-animation.html#tween-animation
 * */
public class ViewAnimationActivity extends Activity {
	/*Tweened Animation: "Tween" is actually short for "in-between", 
	 * and refers to the creation of successive frames of animation between key frames. 
	 * In computer animation, the term is most commonly used for Flash's "shape tweening" and
	 *  "motion tweening" processes, 
	 *  where the user can define two key frames and 
	 *  Flash will automatically create the in-between frames,
	 *   either morphing one shape into another over a set period of time or 
	 *   else moving a shape or shapes from point A to point B over a set period of time.
	 * 3D animation programs also have their own method of "tweening".
	 * */
	ImageView spaceshipImage;
	TextView textView_anim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		 spaceshipImage = (ImageView) findViewById(R.id.spaceshipImage);
		 spaceshipImage.setVisibility(View.VISIBLE);
		
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		
		textView_anim = (TextView)findViewById(R.id.textView_anim);
		textView_anim.setVisibility(View.VISIBLE);
		Animation textTitlesAnimation = AnimationUtils.loadAnimation(this, R.anim.titles);
		textView_anim.startAnimation(textTitlesAnimation);
		
		
	}
}
