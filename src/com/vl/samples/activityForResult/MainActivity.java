package com.vl.samples.activityForResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vl.samples.R;

public class MainActivity extends Activity{
	Button m_startBtn;
	Activity m_activity;
	private final int RESULT=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		m_activity = this;
		
		m_startBtn = (Button)findViewById(R.id.start_activity);
		m_startBtn.setVisibility(View.VISIBLE);
		m_startBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(m_activity,ResultActivity.class);
				startActivityForResult(intent, RESULT);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==RESULT && resultCode == RESULT_OK){
			((TextView)findViewById(R.id.result_data)).setVisibility(View.VISIBLE);
			((TextView)findViewById(R.id.result_data)).setText(data.getStringExtra("returnData"));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
