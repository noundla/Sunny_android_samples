package com.vl.samples.sms;

import android.telephony.SmsManager;

public class SendSMS {
	//call this method to send SMS
	/**@param tel receiver number
	 * @param s message*/
	public static void sendMsg(String tel, String s) {
	    SmsManager smsManager = SmsManager.getDefault();
	    smsManager.sendTextMessage(tel, null, s, null, null);
	  }
}
