package com.vl.samples;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class SendEmailActivity extends Activity{
	public void sendEmail(){
	//this opens only mail client like mail & gmail
	Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
	intent.setType("text/plain");
	intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
	intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
	intent.setData(Uri.parse("mailto:default@recipient.com")); // or just "mailto:" for blank
	startActivity(intent);
	}
	public void sendEmail1(){
		Intent send = new Intent(Intent.ACTION_SENDTO);
		String uriText;

		uriText = "mailto:email@gmail.com" + 
		          "?subject=the subject" + 
		          "&body=the body of the message";
		uriText = uriText.replace(" ", "%20");
		Uri uri = Uri.parse(uriText);

		send.setData(uri);
		startActivity(Intent.createChooser(send, "Send mail..."));
	}
	public void SendMail(){
		//this opens all apps those are having the setType pattern
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		i.putExtra(Intent.EXTRA_TEXT   , "body of email");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(SendEmailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	// email validation
	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9+._%-+]{1,256}" +
	          "@" +
	          "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
	          "(" +
	          "." +
	          "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
	          ")+"
	      );
	 private boolean checkEmail(String email) {
	        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	    }
	
}
