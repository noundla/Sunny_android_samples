package com.vl.samples.camaraImgStore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vl.samples.R;
import com.vl.samples.projConfig.Constants;

public class CamaraTestActivity extends Activity implements OnClickListener{

	private static final String TAG = "CamaraTestActivity";

	private final int CAMERA_RESULT = 2;
	private final int SELECT_PICTURE = 3;
	private final int CROP_IMAGE = 4;

	private Activity mActivity;
	private Button mTakePhoto,mPickPhoto,mPickFromFolder;
	private ImageView mDisplayImage;

	/**This points to captured or selected image uri. 
	 *<br><br>Captured image uri will be created while launching camera. 
	 *<br>Selected image uri will be generated after selecting the image.*/
	private static Uri selectedImageUri;

	private LinearLayout mImagesLayout;
	/**Target folder of the device to store the captured images. This targets to external sdcard.*/
	private final String TARGET_FOLDER = Environment.getExternalStorageDirectory()+"/sandeep/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initGUI();

	}
	/**initiates the GUI*/
	private void initGUI() {
		setContentView(R.layout.camara);
		mActivity = this;

		mTakePhoto = (Button)findViewById(R.id.take_photo);
		mPickPhoto = (Button)findViewById(R.id.pick_photo);
		mPickFromFolder = (Button)findViewById(R.id.Pick_from_folder);
		mDisplayImage = (ImageView)findViewById(R.id.display_image);
		mImagesLayout = (LinearLayout)findViewById(R.id.images_layout);

		mTakePhoto.setOnClickListener(this);
		mPickPhoto.setOnClickListener(this);
		mPickFromFolder.setOnClickListener(this);	

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.take_photo:
			startCamera();
			break;
		case R.id.pick_photo:
			pickPhoto();
			break;
		case R.id.Pick_from_folder:
			if(mImagesLayout.getChildCount()>0)
				mImagesLayout.removeViews(1, mImagesLayout.getChildCount()-1);	
			loadImages();
			break;
		default:
			break;
		}

	}


	/**This method is used to launch Camera and saves the captured image in specified folder
	 * */
	private void startCamera() {

		selectedImageUri = Uri.fromFile(createNewFile());
		Toast.makeText(mActivity, "Image stroing location: "+selectedImageUri, Toast.LENGTH_LONG).show();
		if(Constants.LOG)Log.i(TAG,"photoUri : "+selectedImageUri);


		//starting camera with specific file path
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra("crop", "true");
		i.putExtra("aspectX", 1);
		i.putExtra("aspectY", 1);
		/*i.putExtra("outputX", 500);
		 i.putExtra("outputY", 500);*/

		i.putExtra( MediaStore.EXTRA_OUTPUT, selectedImageUri);

		try {

			i.putExtra("return-data", false);
			startActivityForResult(i, CAMERA_RESULT);

		} catch (ActivityNotFoundException e) {
			//launch the camera without having crop feature
			Intent i1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			i1.putExtra( MediaStore.EXTRA_OUTPUT, selectedImageUri);
			startActivityForResult(i, CAMERA_RESULT);
		}



	}

	private File createNewFile(){
		Calendar cal = Calendar.getInstance();
		File newDirectory = new File(TARGET_FOLDER);
		if(!newDirectory.exists()){
			if(newDirectory.mkdir()){
				if(Constants.LOG)Log.i(TAG,newDirectory.getAbsolutePath()+" directory created");
			}
		}
		File file = new File(newDirectory,(cal.getTimeInMillis()+".jpg"));
		if(!file.exists()){
			try {
				if(file.createNewFile())
					if(Constants.LOG)Log.i(TAG, file.getAbsolutePath()+" file created");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			file.delete();
			try {
				if(file.createNewFile())
					if(Constants.LOG)Log.i(TAG, file.getAbsolutePath()+" file created");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * Picks an image from sdcard or internal memory.
	 * 
	 * */
	private void pickPhoto() {
		selectedImageUri = Uri.fromFile(createNewFile());
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		/*i.putExtra("outputX", 500);
		 i.putExtra("outputY", 500);*/
		intent.putExtra( MediaStore.EXTRA_OUTPUT, selectedImageUri);

		try {

			intent.putExtra("return-data", false);
			startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

		} catch (ActivityNotFoundException e) {
			//launch the camera without having crop feature
			Intent intent1 = new Intent();
			intent1.setType("image/*");
			intent1.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent1,"Select Picture"), SELECT_PICTURE);
		}

	}
	/**Loads all the images from specified folder which are having JPG and JPEG as extension*/
	public void loadImages(){
		String[] imageExtensions = new String[]{"jpg","jpeg"};
		String state = Environment.getExternalStorageState();
		if(state.contentEquals(Environment.MEDIA_MOUNTED) || state.contentEquals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			String homeDir = Environment.getExternalStorageDirectory().toString()+"/sandeep/";
			Log.d(TAG,"home directory : "+ homeDir);
			File file = new File(homeDir);
			Toast.makeText(mActivity, "Images picking from "+homeDir, Toast.LENGTH_LONG).show();
			((TextView)findViewById(R.id.title)).setText("Images from "+homeDir+" floder:");
			if(file.exists()){
				File[] listOfFiles = file.listFiles();
				//For each file in the directory... 
				for (File aFile : listOfFiles){                            
					//Check if the extension is one of the supported filetypes                           
					//imageExtensions is a String[] containing image filetypes (e.g. "png")
					for (String ext : imageExtensions){ 	
						if (aFile.getName().endsWith("." + ext)) {
							Log.d("list of files", aFile.getAbsolutePath());
							InputStream in=null;
							try {
								//converting File to InputStream
								in = new BufferedInputStream(new FileInputStream(aFile));
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							if(in!=null){
								//converting BitMap from stream
								BitmapFactory.Options options = new BitmapFactory.Options();
								options.inSampleSize=20;
								Bitmap thumb = BitmapFactory.decodeStream(in,null,options);
								Log.d("thumb", thumb+"");
								//creating imageView and adding that to layout
								ImageView image = new ImageView(mActivity);

								image.setImageBitmap(thumb);

								mImagesLayout.addView(image);
							}
						}
					} 
				}
			} else{
				Toast.makeText(mActivity, file.getAbsolutePath()+" not existed", Toast.LENGTH_LONG).show();
			}
		}
		else {
			Log.e(TAG,"Error : External Storage Unaccessible - " + state);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case CAMERA_RESULT:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), selectedImageUri);

					Matrix mat = new Matrix();
					mat.postRotate(-90);
					// Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
					mDisplayImage.setImageBitmap(bitmap);
					mDisplayImage.setVisibility(View.VISIBLE);
//								performCropImage();
				}catch (FileNotFoundException e) {
					e.printStackTrace();

				}catch (IOException e) {
					e.printStackTrace();

				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;

		case SELECT_PICTURE:
			if (resultCode == RESULT_OK) {

				//				selectedImageUri = Uri.parse(data.getDataString());//Uri.parse(imageReturnedIntent.getDataString());        
				Toast.makeText(mActivity, "Image picking location: "+data.getDataString(), Toast.LENGTH_LONG).show();
				ContentResolver cr = getContentResolver();
				InputStream in=null;
				try {
					in = cr.openInputStream(selectedImageUri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				if(in!=null){
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize=20;
					Bitmap thumb = BitmapFactory.decodeStream(in,null,options);
					mDisplayImage.setImageBitmap(thumb);
					mDisplayImage.setVisibility(View.VISIBLE);
				}
//				performCropImage();
			}
			break;

		case CROP_IMAGE:
			if (resultCode == RESULT_OK) {
				final Bundle extras = data.getExtras();
				if (extras != null) {
					if(mCropImagedUri!=null){
						mDisplayImage.setImageURI(mCropImagedUri);
						mDisplayImage.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		}
	}


	private void performCropImage1(){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");
		List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
		int size = list.size();
		if (size == 0) {            
			Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();

			return;
		} else {
			intent.setData(selectedImageUri);        
			intent.putExtra("outputX", 500);
			intent.putExtra("outputY", 500);
			intent.putExtra("aspectX", 500);
			intent.putExtra("aspectY", 500);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			if (size == 1) {
				Intent i        = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

				startActivityForResult(i, CROP_IMAGE);
			} else {

			}

		}
	}
	private Uri mCropImagedUri;
	/**Crop the image
	 * @return returns <tt>true</tt> if crop supports by the device,otherwise false*/
	private boolean performCropImage(){
		try {
			if(selectedImageUri!=null){
				//call the standard crop action intent (the user device may not support it)
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				//indicate image type and Uri
				cropIntent.setDataAndType(selectedImageUri, "image/*");
				//set crop properties
				cropIntent.putExtra("crop", "true");
				//indicate aspect of desired crop
				cropIntent.putExtra("aspectX", 1);
				cropIntent.putExtra("aspectY", 1);
				cropIntent.putExtra("scale", true);
				//indicate output X and Y
				cropIntent.putExtra("outputX", 500);
				cropIntent.putExtra("outputY", 500);
				//retrieve data on return
				cropIntent.putExtra("return-data", false);

				File f = createNewFile("CROP_");
				try {
					f.createNewFile();
				} catch (IOException ex) {
					Log.e("io", ex.getMessage());  
				}

				mCropImagedUri = Uri.fromFile(f);
				cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImagedUri);
				//start the activity - we handle returning in onActivityResult
				startActivityForResult(cropIntent, CROP_IMAGE);
				return true;
			}
		}
		catch(ActivityNotFoundException anfe){
			//display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}
		return false;
	}
	private File createNewFile(String prefix){
		if(prefix==null || "".equalsIgnoreCase(prefix)){
			prefix="IMG_";
		}
		File newDirectory = new File(TARGET_FOLDER);
		if(!newDirectory.exists()){
			if(newDirectory.mkdir()){
				Log.d("this", newDirectory.getAbsolutePath()+" directory created");
			}
		}
		File file = new File(newDirectory,(prefix+System.currentTimeMillis()+".jpg"));
		if(file.exists()){
			//this wont be executed
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}
}
