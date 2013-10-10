package com.vl.samples.webviewintent;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.vl.samples.R;

public class MyWebViewImplicitIntent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.webviewintent);
		WebView webView = (WebView) findViewById(R.id.my_webView);   

		Intent intent = getIntent();

		Uri data = intent.getData();
		Log.d("sssssssss", "sssssssssssssss:"+data.getPath());
		URL url = null;
		if(data!=null){
			try {
				url = new URL(data.getScheme(), data.getHost(),	data.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(url!=null){
				Log.d("sssssssss", "sssssssssssssss:"+url.toString());
				webView.loadUrl(url.toString());
			}
		}
		
		


	}
}
