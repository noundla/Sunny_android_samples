package com.vl.samples.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.vl.samples.R;
import com.vl.samples.utils.Util;

public class SMSActivity extends Activity implements OnClickListener {

	private EditText mPhoneET;
	private EditText mMessageET;
	private Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.sms_screen);
		mActivity = this;
		mPhoneET = (EditText)findViewById(R.id.phoneET);
		mMessageET = (EditText)findViewById(R.id.messageET);
		findViewById(R.id.sendBTN).setOnClickListener(this);
		findViewById(R.id.resetBTN).setOnClickListener(this);

	}
	private void sendSMS(String phoneNumber, String message) { 

		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		BroadcastReceiver mSmsReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()){
				case Activity.RESULT_OK:
					Log.d("mSmsReceiver", "mSmsReceiver::RESULT_OK");
					Toast.makeText(mActivity, "Sms sent", Toast.LENGTH_LONG).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Util.showAlert(mActivity, getString(R.string.alert_error),  "SMS sending faild, please try again later!", getString(R.string.ok_text), true);
					Log.d("mSmsReceiver", "mSmsReceiver::RESULT_ERROR_GENERIC_FAILURE");
					break;
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Util.showAlert(mActivity, getString(R.string.alert_error),  "SMS sending failed because of service is currently unavailable, please try again later!", getString(R.string.ok_text), true);
					Log.d("mSmsReceiver", "mSmsReceiver::RESULT_ERROR_NO_SERVICE");
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Util.showAlert(mActivity, getString(R.string.alert_error),  "SMS sending failed because no pdu provided, please try again later!", getString(R.string.ok_text), true);
					Log.d("mSmsReceiver", "mSmsReceiver::RESULT_ERROR_NULL_PDU");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Util.showAlert(mActivity, getString(R.string.alert_error),  "SMS sending failed because radio was explicitly turned off, please try again later!", getString(R.string.ok_text), true);
					Log.d("mSmsReceiver", "mSmsReceiver::RESULT_ERROR_RADIO_OFF");
					break;
				}
				try{
					unregisterReceiver(this);
				}catch(Exception e){
					Log.e("Error", "onDestroy::"+e.getMessage());
				}
			}
		};

		//---when the SMS has been sent---
		registerReceiver(mSmsReceiver, new IntentFilter(SENT));
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI,deliveredPI);        
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sendBTN:
			sendSMS(mPhoneET.getText().toString(), mMessageET.getText().toString());
			break;
		case R.id.resetBTN:
			mMessageET.setText("");
			mPhoneET.setText("");
			mPhoneET.requestFocus();
			break;

		default:
			break;
		}
		
	}
}
