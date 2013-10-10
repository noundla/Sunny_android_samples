package com.vl.samples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vl.samples.fileDownload.download;



public class AlertActivity extends Activity {
	TextView title_tv;
	TextView message_tv;
	Button btn_ok;
	Button btn_cancel;
	private Activity _activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert);
		_activity = this;
		


		String title="title";
		String message = "sfdsdfssfd fds sddf dsdfs dsdfs ";
		
		title_tv = (TextView)findViewById(R.id.alert_title_c2dm);
		message_tv = (TextView)findViewById(R.id.alert_content_c2dm);
		btn_ok = (Button)findViewById(R.id.alert_ok_c2dm);
		btn_cancel = (Button)findViewById(R.id.alert_cancel_c2dm);
		btn_cancel.setVisibility(View.GONE);
		
		
		
		/*Intent intent = getIntent();
		if(intent.hasExtra(Constants.TITLE))
			title=intent.getStringExtra(Constants.TITLE);
		if(intent.hasExtra(Constants.MESSAGE))
			message=intent.getStringExtra(Constants.MESSAGE);*/
		
		title_tv.setText(title);
		message_tv.setText(message);
		
		
		//it works when the user is not in our Application.
		if(true){
			btn_cancel.setVisibility(View.VISIBLE);
			
			btn_cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					_activity.finish();
				}
			});
		}
		
		btn_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(true){
					Intent intent = new Intent(_activity,download.class);
					startActivity(intent);
					// go to castrol when user clicks the button
//					ModuleManager.startActivity(ModuleManager.DISPATCH_MODULE,DispatchModuleHandler.SPLASHSCREEN,_activity);
					Toast.makeText(_activity, "hey im ok.. going", Toast.LENGTH_LONG);
				}else{
					//Constants.C2DM_ACTIVITY_FOCUS_CHECK = true;
					_activity.finish();
				}
			}
		});		
		
	}
	}
