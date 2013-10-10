package com.vl.samples;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * This code is used to know which applications are presently running on device.
 * */
public class ToKnowForegroundActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			final boolean foregroud = new ForegroundCheckTask().execute(this).get();
			if(foregroud){
				//Do something
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	//working with method 
	private boolean getCurrentForegroundPackage(){
		
		boolean running = false;
		try{
			ActivityManager activityManager = (ActivityManager) this.getSystemService( Context.ACTIVITY_SERVICE );
			List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
			for(RunningAppProcessInfo appProcess : appProcesses){
			    if(appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
			        Log.i("Foreground App", appProcess.processName);
			        if(appProcess.processName.equals("com.vl.c2dm.app2")){
			        	Toast.makeText(this, "suresh vachinduuuuuu", Toast.LENGTH_LONG);
			        	Log.d("found", "suresh vachinduuuuuu");
			        	running = true;
			        }
			    }
			}
		
		}catch (Exception e) {
			Log.d("ValueLabs Exception", e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
		}
		return running;
	}
	
	
	
	
	
	
	
	
	//working with inner class. This runs in the background as a Thread
	class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {

		  @Override
		  protected Boolean doInBackground(Context... params) {
		    final Context context = params[0].getApplicationContext();
		    return isAppOnForeground(context);
		  }

		  private boolean isAppOnForeground(Context context) {
		    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		    if (appProcesses == null) {
		      return false;
		    }
		    final String packageName = context.getPackageName();
		    for (RunningAppProcessInfo appProcess : appProcesses) {
		      if (! (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName))) {
		        return true;
		      }
		    }
		    return false;
		  }
		}
}
