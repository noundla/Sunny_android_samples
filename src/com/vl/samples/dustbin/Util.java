package com.vl.samples.dustbin;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;



public class Util {
	static boolean isNetworkAvailable; 
	
	private static boolean isAndroidTablet(Context context) {
		// Can use static final constants like HONEYCOMB, declared in later	versions
		// of the OS since they are inlined at compile time. This is guaranteed behavior.
		if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
			// test screen size, use reflection because isLayoutSizeAtLeast is
			// only available since 11
			Configuration con = context.getResources().getConfiguration();
			try {
				Method mIsLayoutSizeAtLeast = con.getClass().getMethod(
						"isLayoutSizeAtLeast", int.class);
				boolean r = (Boolean)
						mIsLayoutSizeAtLeast.invoke(con,0x00000004); //Configuration.SCREENLAYOUT_SIZE_XLARGE
				return r;
			} catch (Exception x) {
				x.printStackTrace();
				return false;
			}
		}
		return false;
	}

	private static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout
				& Configuration.SCREENLAYOUT_SIZE_MASK)
				>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isTabletDevice(Context context) {
		return isAndroidTablet(context) || isTablet(context);
	}

	public static boolean checkInternetConnection(Activity _activity) {
		ConnectivityManager conMgr = (ConnectivityManager) _activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null
				&& conMgr.getActiveNetworkInfo().isAvailable()
				&& conMgr.getActiveNetworkInfo().isConnected()) 
			return true;
		else
			return false;
	}

	public static boolean deleteDirectory(File path) {
		// TODO Auto-generated method stub
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}

	public static boolean isSdPresent() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}//isSdPresent

	public static boolean checkExternalMedia(){
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		boolean stat;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// Can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
			stat = true;
		} 
		else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// Can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
			stat = false;
		} else {
			// Can't read or write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
			stat = false;
		}
		return stat;
	}//checkExternalMedia()

	public static boolean isNetworkAvailable(Context context) {
		isNetworkAvailable = false;
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						isNetworkAvailable = true;
						return true;
					}
				}
			}
		}
		return false;
	}//isNetworkAvailable()

	public static InputStream convertStringToStream(String text){
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(text.getBytes());
		} catch(OutOfMemoryError om){
			om.printStackTrace();
		}
		return is;
	}//convertStringToStream()



	public static String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			is.close();
		} catch(OutOfMemoryError om){
			om.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return sb.toString();
	}//convertStreamToString()

	public static Document XMLfromString(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = (Document) db.parse(is); 
		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}
		return doc;
	}//XMLfromString()
	public static Bitmap getBitmapImage(String url){		
		try{			
			String streamURL; 
			if(url.contains("\\")){
				streamURL = url.replace("\\", "/");
			}
			else{
				streamURL = url;
			}
			URL newurl = new URL(streamURL); 
			Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());			 
			return mIcon_val;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}//getImage

	public static Bitmap getImage(String url,int width,int height){
		Log.d("get image url:",url);
		try{			
			String streamURL; 
			if(url.contains("\\")){
				streamURL = url.replace("\\", "/");
			}
			else{
				streamURL = url;
			}
			URL newurl = new URL(streamURL); 
			//Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
			HttpGet httpRequest = null;
			try {
				httpRequest = new HttpGet(newurl.toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			InputStream instream = bufHttpEntity.getContent();
			Bitmap mIcon_val = BitmapFactory.decodeStream(instream);			
			Matrix matrix = new Matrix();   
			if(width==0){
				width = mIcon_val.getWidth();
				Log.d("width",""+width);
				height = mIcon_val.getHeight();
				Log.d("height",""+height);
			}
			matrix.postScale((float)width/mIcon_val.getWidth(), (float)height/mIcon_val.getHeight()); 
			
			Bitmap resizedBitmap = Bitmap.createBitmap(mIcon_val, 0, 0, mIcon_val.getWidth(),  mIcon_val.getHeight(), matrix, true);
			return resizedBitmap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}//getImage
	public static Drawable downloadDrawable(String imageUrl) 
	{
		Drawable image_drawable = null;
		try {
			URL url;
			if(imageUrl.contains("\\")){
				url = new URL(imageUrl.replace("\\","/"));
			}else{
				url = new URL(imageUrl);
			}	        
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			Log.d("bannerStream::",""+is.available());	        
			image_drawable = Drawable.createFromStream(is, url.toString());	        
			is.close();	       
		} catch (IOException e) {
			Log.d("Exception",""+e);
		}
		return image_drawable;
	}//downloadDrawable()

	public static String FileNameFromUrl(String url){
		if(url.contains("\\")){
			String fileName = url.substring(url.lastIndexOf("\\")).trim();		
			return fileName.replace("\\","");
		}
		else{
			String fileName = url.substring(url.lastIndexOf("/")).trim();		
			return fileName.replace("/","");
		}
	}//FileNameFromUrl

	public static boolean fileExists(String url){		
		File newpath=new File(Environment.getExternalStorageDirectory(),"/video");		
		if(!newpath.mkdir())
			newpath.mkdir();
		File f;
		if(url.contains("\\")){
			f=new File(newpath,url.substring(url.lastIndexOf("\\")).trim());
		}
		else{
			f=new File(newpath,url.substring(url.lastIndexOf("/")).trim());
		}
		if(f.exists()){        	
			return true;
		}
		else{        	
			return false;
		}
	}

	public static String getPWDFromSP(Activity _activity) {
		// TODO Auto-generated method stub
		SharedPreferences preferences = _activity.getSharedPreferences(/*Constants.SHARED_PREFERENCES_NAME*/"", android.content.Context.MODE_PRIVATE);
		return preferences.getString("PASSWORD", "0");
	}//getPWDFromSP()

	public static void savePWDInSP(Activity _activity, String _pwd){
		SharedPreferences preferences = _activity.getSharedPreferences(/*Constants.SHARED_PREFERENCES_NAME*/"", android.content.Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("PASSWORD", _pwd);
		editor.commit();
	}//savePWDInSP()

	public static InputStream openHttpConnection(String  urlStr) {
		// TODO Auto-generated method stub
		InputStream inStream = null;
		Log.d("in http", "connection");
		HttpGet httpGet = new HttpGet(urlStr);
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 30000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		int timeoutSocket = 30000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpResponse response = httpClient.execute(httpGet);
			response.getStatusLine().toString();
			HttpEntity he = response.getEntity();
			inStream = he.getContent();
			Log.d("input sterm available is",""+inStream.available());

		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.d("openHttpConnection", "1"+e);

		} catch (IOException e) {
			e.printStackTrace();
			Log.d("IoException", ""+e);
		}catch(Exception ee){
			return null;
		}
		return inStream;


	}

	public static String convertBitmapToString(Bitmap src) {
		String str =null; 
		if(src!= null){
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			src.compress(android.graphics.Bitmap.CompressFormat.PNG, 100,(OutputStream) os);
			byte[] byteArray = os.toByteArray();
			//Log.d("byte=",""+byteArray.length);//returns 12111 as a length.
			str = Base64.encodeToString(byteArray,Base64.DEFAULT);	 
		}
		return str;			
	}

	public static Bitmap getBitMapFromString(String src)    {
		//		 Log.d("b=",""+src.getBytes().length);//returns 12111 as a length.
		//		 Log.d("b=string",src); 
		Bitmap bitmap = null;
		if(src!= null){
			byte[] decodedString = Base64.decode(src.getBytes(), Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
		}
		return bitmap;
	}
}