package com.vl.samples;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vl.samples.activityAnimation.FirstActivity;
import com.vl.samples.animation.DrawableAnimationActivity;
import com.vl.samples.animation.ViewAnimationActivity;
import com.vl.samples.camaraImgStore.CamaraTestActivity;
import com.vl.samples.charts.ChartsActivity;
import com.vl.samples.customDialog.CustomDialog;
import com.vl.samples.customImageView.MyImageView;
import com.vl.samples.customcamera.CustomCameraActivity;
import com.vl.samples.customcamera.PreviewDemo;
import com.vl.samples.ellipsizetextview.EllipsizeTextViewActivity;
import com.vl.samples.expandablelistview.ExpandableListViewActivity;
import com.vl.samples.googleCharts.GoogleChartsActivity;
import com.vl.samples.googleMap.DirectionActivity;
import com.vl.samples.googleMap.GoogleMapActivity;
import com.vl.samples.gpsLocation.GPSLocationActivity;
import com.vl.samples.listview.SingleItemSelectionListActivity;
import com.vl.samples.ninePatch.NinePatchActivity;
import com.vl.samples.sms.SMSActivity;
import com.vl.samples.text2speech.TextToSpeechActivity;
import com.vl.samples.textJustify.JustifyTextActivity;
import com.vl.samples.verticalTextView.VerticalTextActivity;

public class MainActivity extends Activity implements View.OnClickListener{
	Button mDrawableAnim,mViewAnimation,mActivityAnimation, 
			mCamaraImageStore, mGoogleMap,mCustomDialog,mImageZoomControls,
			mGpsLocation,mText2Speech,mTextJustify,mNinePatch,mGoogleCharts,
			mCustomCharts,mVerticalText,mEllipsizeTextView,mLaunchScrollApp,mExpandableList;
	MainActivity mAcitivy;
	CustomDialog mMessageDialog;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_launch);
		mAcitivy = this;
		
		mDrawableAnim = (Button) findViewById(R.id.drawable_anim);
		mViewAnimation = (Button) findViewById(R.id.view_anim);
		mActivityAnimation = (Button) findViewById(R.id.activity_anim);
		mCamaraImageStore = (Button) findViewById(R.id.camara_img_store);
		mCamaraImageStore = (Button) findViewById(R.id.camara_img_store);
		mGoogleMap  = (Button) findViewById(R.id.google_map);
		mCustomDialog  = (Button) findViewById(R.id.custom_dialog);
		mImageZoomControls  = (Button) findViewById(R.id.custom_image);
		mGpsLocation  = (Button) findViewById(R.id.gps);
		mText2Speech  = (Button) findViewById(R.id.text_2_speech);
		mTextJustify  = (Button) findViewById(R.id.text_justify);
		mNinePatch  = (Button) findViewById(R.id.nine_patch);
		mGoogleCharts  = (Button) findViewById(R.id.google_charts_btn);
		mCustomCharts  = (Button) findViewById(R.id.custom_charts_btn);
		mVerticalText = (Button) findViewById(R.id.vertical_text_btn);
		mEllipsizeTextView = (Button) findViewById(R.id.ellipsizetextview_btn);
		mLaunchScrollApp = (Button) findViewById(R.id.launch_scrollapp_btn);
		mExpandableList = (Button) findViewById(R.id.expandable_list_btn);
		
		mDrawableAnim.setOnClickListener(this);
		mViewAnimation.setOnClickListener(this);
		mActivityAnimation.setOnClickListener(this);
		mCamaraImageStore.setOnClickListener(this);
		mGoogleMap.setOnClickListener(this);
		mCustomDialog.setOnClickListener(this);
		mImageZoomControls.setOnClickListener(this);
		mGpsLocation.setOnClickListener(this);
		mText2Speech.setOnClickListener(this);
		mTextJustify.setOnClickListener(this);
		mNinePatch.setOnClickListener(this);
		mGoogleCharts.setOnClickListener(this);
		mCustomCharts.setOnClickListener(this);
		mVerticalText.setOnClickListener(this);
		mEllipsizeTextView.setOnClickListener(this);
		mLaunchScrollApp.setOnClickListener(this);
		mExpandableList.setOnClickListener(this);
		findViewById(R.id.sms_btn).setOnClickListener(this);
		findViewById(R.id.customCameraBtn).setOnClickListener(this);
		
		findViewById(R.id.direction_map).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.drawable_anim:
			intent = new Intent(mAcitivy , DrawableAnimationActivity.class);
			startActivity(intent);
			break;
		
		case R.id.view_anim:
			intent = new Intent(mAcitivy , ViewAnimationActivity.class);
			startActivity(intent);
			break;
			
		case R.id.activity_anim:
			intent = new Intent(mAcitivy , FirstActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		
		case R.id.camara_img_store:
			intent = new Intent(mAcitivy , CamaraTestActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.google_map:
			intent = new Intent(mAcitivy , GoogleMapActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
			
		case R.id.custom_dialog:
			showChoiceDialog("Quit", "Are you sure\nyou want to exit?");
		break;
		
		case R.id.custom_image:
			intent = new Intent(mAcitivy , MyImageView.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.gps:
			intent = new Intent(mAcitivy , GPSLocationActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.text_2_speech:
			intent = new Intent(mAcitivy , TextToSpeechActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		
		case R.id.text_justify:
			intent = new Intent(mAcitivy , JustifyTextActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.nine_patch:
			intent = new Intent(mAcitivy , NinePatchActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.google_charts_btn:
			intent = new Intent(mAcitivy , GoogleChartsActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.custom_charts_btn:
			intent = new Intent(mAcitivy , ChartsActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.vertical_text_btn:
			intent = new Intent(mAcitivy , VerticalTextActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.ellipsizetextview_btn:
			intent = new Intent(mAcitivy , EllipsizeTextViewActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;	
		case R.id.launch_scrollapp_btn:
			try{
//			intent = new Intent(mAcitivy,com.vl.samples.scrollview.MainActivity.class);
			intent = new Intent();	
			intent.setComponent(new ComponentName("com.vl.scrollviewsample","com.vl.scrollviewsample.MainActivity"));
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //XXX:use this to launch the application in new task
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			}catch (ActivityNotFoundException e) {
				//ScrollViewSample application is not installed in this device.
				Toast.makeText(mAcitivy, "Yaak! It seems that you are trying to access missing application/activity", Toast.LENGTH_LONG ).show();
			}
			break;
			
		case R.id.expandable_list_btn:
			intent = new Intent(mAcitivy , ExpandableListViewActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.direction_map:
			intent = new Intent(mAcitivy , DirectionActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
		case R.id.sms_btn:
			intent = new Intent(mAcitivy , SMSActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
			
		case R.id.customCameraBtn:
			intent = new Intent(mAcitivy , CustomCameraActivity.class);
			startActivity(intent);
			//this is used to launch the activity with animation
			overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
			break;
			
		default:
			break;
		}
		
	}
	
	
	public void showChoiceDialog(CharSequence title, CharSequence message) {
		
		Toast.makeText(mAcitivy, "implementation is in pending", Toast.LENGTH_LONG).show();
	}
	
	public void singleSelectionList(View view){
		Intent intent = new Intent(mAcitivy , SingleItemSelectionListActivity.class);
		startActivity(intent);
		//this is used to launch the activity with animation
		overridePendingTransition(R.anim.fade, R.anim.my_activity_exit_anim);
	}
	

}
