package com.vl.samples.log;


public class Log{
	public static final int VERBOSE = 1;
	public static final int DEBUG = VERBOSE+1;
	public static final int INFO = DEBUG+1;
	public static final int WARN = INFO+1;
	public static final int ERROR = WARN+1;
	public static final int ASSERT = ERROR+1;
	
	public static int LOG_LEVEL = -1; 
	
	public static void v(String tag,String msg){
		if(LOG_LEVEL<=VERBOSE){
			android.util.Log.v(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if(LOG_LEVEL<=DEBUG){
			android.util.Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg){
		if(LOG_LEVEL<=INFO){
			android.util.Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg){
		if(LOG_LEVEL<=WARN){
			android.util.Log.w(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if(LOG_LEVEL<=ERROR){
			android.util.Log.e(tag, msg);
		}
	}
	
	public static int println(int priority,String tag,String msg){
		if(LOG_LEVEL<=ASSERT){
			return android.util.Log.println(priority, tag, msg);
		}
		return -1;
	}
}
