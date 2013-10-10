package com.vl.samples.activityAnimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.vl.samples.R;

public class FirstActivity extends Activity {
	Button mTemp;
	private Button flipAnim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		mTemp = (Button) findViewById(R.id.temp);
		mTemp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FirstActivity.this,SecondActivity.class);
				startActivity(intent);

			}
		});
		flipAnim = (Button) findViewById(R.id.flip_anim);
		flipAnim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FirstActivity.this,FlipAnimActivity.class);
				startActivity(intent);

			}
		});

	}
}
