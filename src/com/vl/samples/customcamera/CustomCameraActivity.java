package com.vl.samples.customcamera;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.vl.samples.R;
import com.vl.samples.gpsLocation.GPSLocation;


public class CustomCameraActivity extends Activity implements OnClickListener
{

	private static final int CROP_IMAGE = 1;

	private static boolean mIsCropLaunched = false;
	private Button mSnap = null;
	private Button mRetake = null;
	private ImageView image = null;

	private Camera mCamera = null;
	private CameraPreview mPreview;
	private int deviceHeight;

	private File sdRoot;
	private String dir;
	private String fileName;
	private ExifInterface exif;
	private File pictureFile;

	private Button mUse;
	private GPSLocation mGpsLocation = null;
	private Activity mContext ;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);
		mContext = this;

		//Initialize all UI elements
		initUI();
	}

	/**
	 * Initialize all UI elements
	 */
	private void initUI() 
	{
		// Setting all the path for the image
		sdRoot = Environment.getExternalStorageDirectory();
		dir = "/DCIM/Camera/";

		mSnap = (Button) findViewById(R.id.buttonSnap);
		mRetake = (Button) findViewById(R.id.buttonretake);
		mUse = (Button) findViewById(R.id.buttonuse);
		image = (ImageView) findViewById(R.id.imageViewSnap);

		mSnap . setOnClickListener(this);
		mRetake . setOnClickListener(this);
		mUse . setOnClickListener(this);
	}

	private void createCamera() {



		// Create an instance of Camera
		mCamera = getCameraInstance();

		// Setting the right parameters in the camera
		Camera.Parameters params = mCamera.getParameters();
		//		params.setPictureSize(1600, 1200);
		params.setPictureFormat(PixelFormat.JPEG);
		params.setJpegQuality(100);
		mCamera.setParameters(params);

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

		// Calculating the width of the preview so it is proportional.
		float widthFloat = (float) (deviceHeight) * 4 / 3;
		int width = Math.round(widthFloat);

		// Resizing the LinearLayout so we can make a proportional preview. This
		// approach is not 100% perfect because on devices with a really small
		// screen the the image will still be distorted - there is place for
		// improvment.
		//		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, deviceHeight);
		//		preview.setLayoutParams(layoutParams);

		// Adding the camera preview after the FrameLayout and before the button
		// as a separated element.
		preview.addView(mPreview, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Test if there is a camera on the device and if the SD card is
		// mounted.
		if (!checkCameraHardware(this)) {
			Intent i = new Intent(this, NoCamera.class);
			startActivity(i);
			finish();
		} else if (!checkSDCard()) {
			Intent i = new Intent(this, NoSDCard.class);
			startActivity(i);
			finish();
		}
		if(mCamera==null){
			//Creating the camera
			createCamera();
		}

		// Register this class as a listener for the accelerometer sensor
		//		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// release the camera immediately on pause event
		releaseCamera();

		// removing the inserted view - so when we come back to the app we
		// won't have the views on top of each other.
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.removeViewAt(0);

	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}

	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	private boolean checkSDCard() {
		boolean state = false;

		String sd = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(sd)) {
			state = true;
		}

		return state;
	}

	/**
	 * A safe way to get an instance of the Camera object.
	 */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			// attempt to get a Camera instance
			c = Camera.open();
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
		}

		// returns null if camera is unavailable
		return c;
	}

	private PictureCallback mPicture = new PictureCallback() {

		@SuppressLint("SdCardPath")
		public void onPictureTaken(byte[] data, Camera camera){


			mSnap.setVisibility(View.GONE);
			mRetake.setVisibility(View.VISIBLE);
			image.setVisibility(View.VISIBLE);

			// File name of the image that we just took.
			fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString() + ".jpg";

			// Creating the directory where to save the image. Sadly in older
			// version of Android we can not get the Media catalog name
			File mkDir = new File(sdRoot, dir);
			mkDir.mkdirs();

			// Main file where to save the data that we recive from the camera
			pictureFile = new File(sdRoot, dir + fileName);

			FileOutputStream purge = null;
			try {
				purge = new FileOutputStream(pictureFile);
				purge.write(data);

			} catch (FileNotFoundException e) {
				Log.d("DG_DEBUG", "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d("DG_DEBUG", "Error accessing file: " + e.getMessage());
			} finally{
				if(purge!=null){
					try {
						purge.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}


			// Adding Exif data for the orientation. For some strange reason the
			// ExifInterface class takes a string instead of a file.
			try {
				exif = new ExifInterface("/sdcard/" + dir + fileName);
				exif.setAttribute(ExifInterface.TAG_ORIENTATION, "" + ExifInterface.ORIENTATION_NORMAL);
				exif.saveAttributes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			////////////////////////////////////////////
			try{

				int angle = 90;

				Matrix mat = new Matrix();
				mat.postRotate(angle);


				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				options.inPurgeable = true;
				options.inInputShareable = true;
				//				Bitmap bm = BitmapFactory.decodeFile("/sdcard/" + dir + fileName,options);

				Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(pictureFile), null, null);
				Log.d("CameraActivity", "camera image dim>>w::"+bm.getWidth()+"//h::"+bm.getHeight());
				Bitmap correctBmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true); 

				try {
					FileOutputStream out = new FileOutputStream(pictureFile);
					correctBmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}


				image.setImageBitmap(bm);
				

			}
			catch(OutOfMemoryError oom) {
				Log.w("TAG", "-- OOM Error in setting image");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.buttonSnap:
			mCamera.takePicture(null, null, mPicture);
			updateButtonsVisibility(true);
			break;

		case R.id.buttonretake:
			
			updateButtonsVisibility(false);
			// Deleting the image from the SD card/
			File discardedPhoto = new File(sdRoot, dir + fileName);
			discardedPhoto.delete();

			// Restart the camera preview.
			mCamera.startPreview();

			mSnap.setVisibility(View.VISIBLE);
			break;

		case R.id.buttonuse:

			performCropImage();





			break;


		default:
			break;
		}

	}

	private void updateButtonsVisibility(boolean visible){
		mUse.setVisibility(visible?View.VISIBLE:View.INVISIBLE);
		mRetake.setVisibility(visible?View.VISIBLE:View.INVISIBLE);

		mSnap.setVisibility(visible?View.INVISIBLE:View.VISIBLE);
	}
	private Uri mCropImagedUri;
	private void performCropImage(){
		try{
			
			//call the standard crop action intent (the user device may not support it)
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			//indicate image type and Uri
			cropIntent.setDataAndType(Uri.fromFile(pictureFile), "image/*");
			//set crop properties
			cropIntent.putExtra("crop", "true");
			//indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			//indicate output X and Y
			cropIntent.putExtra("outputX", 500 );
			cropIntent.putExtra("outputY", 500 );
			//retrieve data on return
			cropIntent.putExtra("return-data", false);
			mIsCropLaunched = true;
			String cropfileName = "crop_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()).toString() + ".jpg";
			File f = new File(sdRoot, dir + cropfileName);
		        try {
		            f.createNewFile();
		        } catch (IOException ex) {
		        Log.e("io", ex.getMessage());  
		        }

		        mCropImagedUri = Uri.fromFile(f);

		      cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
			//start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, CROP_IMAGE);

		}catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CROP_IMAGE:
			mIsCropLaunched = false;
			if (resultCode == RESULT_OK) {
				updateButtonsVisibility(false);
				final Bundle extras = data.getExtras();
				if (extras != null) {
//					Bitmap photo = extras.getParcelable("data");
//					image.setImageBitmap(photo);
//
//					//Delete the original file and create new file with the cropped image
//					File f = new File(sdRoot, dir + fileName); 
//					if (f.exists()) {
//						f.delete();
//						try {
//							f.createNewFile();
//							//Convert bitmap to byte array
//							ByteArrayOutputStream bos = new ByteArrayOutputStream();
//							photo.compress(CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
//							byte[] bitmapdata = bos.toByteArray();
//
//							//write the bytes in file
//							FileOutputStream fos=null;
//							try{
//								fos = new FileOutputStream(f);
//								fos.write(bitmapdata);
//							}catch (Exception e) {
//								e.printStackTrace();
//							}finally{
//								if(fos!=null){
//									fos.close();
//								}
//							}
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
					if(mCropImagedUri!=null)
						image.setImageURI(mCropImagedUri);
				}
			}
			break;
		}


	}

}