package com.vl.samples.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.vl.samples.R;

public class Util {
	/**
	 * This method convets dp unit to equivalent device specific value in pixels. 
	 * 
	 * @param dp A value in dp(Device independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent Pixels equivalent to dp according to device
	 */
	public static float convertDpToPixel(float dp,Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi/160f);
	    return px;
	}
	/**
	 * This method converts device specific pixels to device independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent db equivalent to px value
	 */
	public static float convertPixelsToDp(float px,Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;

	}
	public static void uniqueData() {
		String[] items = new String[] { "us", "eu", "us", "fr", "in", "fr" };
		int dataLength = 0;
		String[] data = new String[items.length];
		data[dataLength] = items[0];
		for (int i = dataLength + 1; i < items.length; i++) {
			boolean isNew = true;
			for (int j = dataLength; j >= 0; j--) {
				if (items[i].equalsIgnoreCase(data[j])) {
					isNew = false;
					break;
				}
			}
			if (isNew) {
				++dataLength;
				data[dataLength] = items[i];
			}
		}
		for (int k = 0; k <= dataLength; k++) {
			System.out.println(data[k]);
		}
	}
	
	
	/**Creates a file in SdCard for the given string*/
	public static void stringToFileCreation(){
		try {
			String str = "this is test";
			File root = Environment.getExternalStorageDirectory();
			File file = new File(root, "tomato50.txt");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(filewriter);
			out.write(str);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**Verifies the Internet connection availability and calls the callback methods in {@link NetworkStatusListener} class object based on the network status.
	 * Also, displays an alert(popup) saying that there is no network connection.
	 * <br><code>&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;</code>
	 * @param activity {@link Activity} object reference only.
	 * @param listener Reference object of {@link NetworkStatusListener} class. This will used to trigger the status of the network.
	 */
	public static void checkInternetConnection(Activity activity, NetworkStatusListener listener) {
		checkInternetConnection(activity, listener, true);
	}

	/**Verifies the Internet connection availability and calls the callback methods in {@link NetworkStatusListener} class object based on the network status.
	 * Also, displays an alert(popup) saying that there is no network connection based on the <code>showAlertOnFailure</code> parameter value.
	 * <br><code>&lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&gt;</code>
	 * @param activity {@link Activity} object reference only.
	 * @param listener Reference object of {@link NetworkStatusListener} class. This will used to trigger the status of the network.
	 * @param showAlertOnFailure if <tt>true</tt> displays an alert, otherwise not. 
	 */
	public static void checkInternetConnection(Activity activity, NetworkStatusListener listener,boolean showAlertOnFailure) {
		ConnectivityManager conMgr = (ConnectivityManager)activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
				&& conMgr.getActiveNetworkInfo().isConnected()){
			listener.onNetworkAvailable();
		}else{
			if(showAlertOnFailure)
				Util.showAlert(activity, activity.getString(R.string.alert_title), activity.getString(R.string.no_internet));
			listener.onNetworkNotAvailable();
		}
	}// checkInternetConnection()
	
	/**
	 * Displays an alert to show some information to user and this alert can be cancellable by user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the alert dialog
	 * @param alertMsg Message to display on alert dialog
	 * @throws IllegalArgumentException when button is not having valid text to
	 *             show.
	 */
	public static void showAlert(final Activity activity, String title, String alertMsg	) {
		showAlert(activity, title, alertMsg, activity.getString(android.R.string.ok), true, null);
	}

	/**
	 * Displays an alert to show some information to user and this alert can be cancellable by user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the alert dialog
	 * @param alertMsg Message to display on alert dialog
	 * @param buttonText Text should be displayed on button
	 * @throws IllegalArgumentException when button is not having valid text to
	 *             show.
	 */
	public static void showAlert(final Activity activity, String title, String alertMsg,
			String buttonText) {
		showAlert(activity, title, alertMsg, buttonText, true, null);
	}

	/**
	 * Displays an alert to show some information to user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the alert dialog
	 * @param alertMsg Message to display on alert dialog
	 * @param buttonText Text should be displayed on button
	 * @param listener callback to call on button click.
	 * @throws IllegalArgumentException when button is not having valid text to
	 *             show.
	 */
	public static void showAlert(final Activity activity, String title, String alertMsg,
			String buttonText, final OnAlertDialogButtonClickListener listener) {
		showAlert(activity, title, alertMsg, buttonText,true,listener);
	}

	/**
	 * Displays an alert to show some information to user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the alert dialog
	 * @param alertMsg Message to display on alert dialog
	 * @param buttonText Text should be displayed on button
	 * @param isCancelable <code>true</code> indicates that this dialog will be
	 *            dismissed when device back button pressed ,otherwise not.
	 * @throws IllegalArgumentException when button is not having valid text to
	 *             show.
	 */
	public static void showAlert(final Activity activity, String title, String alertMsg,
			String buttonText, boolean isCancelable) {
		showAlert(activity, title, alertMsg, buttonText, isCancelable, null);
	}

	/**
	 * Displays an alert to show some information to user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the alert dialog
	 * @param alertMsg Message to display on alert dialog
	 * @param buttonText Text should be displayed on button
	 * @param isCancelable <code>true</code> indicates that this dialog will be
	 *            dismissed when device back button pressed ,otherwise not.
	 * @param listener callback to call on button click.
	 * @throws IllegalArgumentException when button is not having valid text to
	 *             show.
	 */
	public static void showAlert(final Activity activity, String title, String alertMsg,
			String buttonText, boolean isCancelable,
			final OnAlertDialogButtonClickListener listener) {

		if (buttonText == null || "".equalsIgnoreCase(buttonText.trim())) {
			throw new IllegalArgumentException(
					"Button text must be supplied to show the alert dialog");
		}

		final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle(title);
		alert.setCancelable(isCancelable);
		alert.setMessage(alertMsg);
		alert.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (listener != null) {
					listener.onAlertDialogButtonClick();
				}
			}
		});
		AlertDialog dialog = alert.create();
		dialog.setCanceledOnTouchOutside(false);

		dialog.show();
	}// showAlert()

	/**
	 * Creates and displays the {@link AlertDialog} to get the confirmation from
	 * the user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the confirm dialog
	 * @param alertMsg Message to display on confirm dialog
	 * @param positiveButtonText Text should be displayed on positive button
	 * @param isCancelable <code>true</code> indicates that this dialog will be
	 *            dismissed when device back button pressed ,otherwise not.
	 * @param listener callback to call on respective button clicks.
	 * @throws IllegalArgumentException when at least one button is not having
	 *             valid text to show.
	 */
	public static void showConfirmAlertDialog(final Activity activity, String title,
			String alertMsg, String positiveButtonText, String negativeButtonText,
			boolean isCancelable, final OnConfirmAlertActionListener listener) {
		showConfirmAlertDialog(activity, title, alertMsg, positiveButtonText, negativeButtonText,
				null, isCancelable, listener);
	}

	/**
	 * Creates and displays the {@link AlertDialog} to get the confirmation from
	 * the user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the confirm dialog
	 * @param alertMsg Message to display on confirm dialog
	 * @param positiveButtonText Text should be displayed on positive button
	 * @param negativeButtonText Text should be displayed on negative button
	 * @param neutralButtonText Text should be displayed on neutral button
	 * @param isCancelable <code>true</code> indicates that this dialog will be
	 *            dismissed when device back button pressed ,otherwise not.
	 * @param listener callback to call on respective button clicks.
	 * @throws IllegalArgumentException when at least one button is not having
	 *             valid text to show.
	 */
	public static void showConfirmAlertDialog(final Activity activity, String title,
			String alertMsg, String positiveButtonText, String negativeButtonText,
			String neutralButtonText, boolean isCancelable,
			final OnConfirmAlertActionListener listener) {
		showConfirmAlertDialog(activity, title, alertMsg, positiveButtonText, negativeButtonText, neutralButtonText, isCancelable, listener, null);
	}


	/**
	 * Creates and displays the {@link AlertDialog} to get the confirmation from
	 * the user.
	 * 
	 * @param activity {@link Activity} reference to create alert
	 * @param title Title for the confirm dialog
	 * @param alertMsg Message to display on confirm dialog
	 * @param positiveButtonText Text should be displayed on positive button
	 * @param negativeButtonText Text should be displayed on negative button
	 * @param neutralButtonText Text should be displayed on neutral button
	 * @param isCancelable <code>true</code> indicates that this dialog will be
	 *            dismissed when device back button pressed ,otherwise not.
	 * @param listener callback to call on respective button clicks.
	 * @param cancelListener callback to call on dialog canceled
	 * @throws IllegalArgumentException when at least one button is not having
	 *             valid text to show.
	 */
	public static void showConfirmAlertDialog(final Activity activity, String title,
			String alertMsg, String positiveButtonText, String negativeButtonText,
			String neutralButtonText, boolean isCancelable,
			final OnConfirmAlertActionListener listener, final OnDialogCancelListener cancelListener) {

		if ((positiveButtonText == null || "".equalsIgnoreCase(positiveButtonText.trim()))
				&& (negativeButtonText == null || "".equalsIgnoreCase(negativeButtonText.trim()))
				&& (neutralButtonText == null || "".equalsIgnoreCase(neutralButtonText.trim()))) {
			throw new IllegalArgumentException(
					"Atleast one button text must be supplied to show the confirm alert dialog");

		}


		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle(title);
		alert.setIcon(null);
		alert.setCancelable(isCancelable);
		alert.setMessage(alertMsg);

		if(isCancelable && cancelListener!=null){
			alert.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					cancelListener.onDilogCanceled();
				}
			});
		}
		if (positiveButtonText != null) {
			alert.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (listener != null) {
						listener.onButtonClicked(AlertButtonCode.POSITIVE);
					}

				}
			});
		}
		if (negativeButtonText != null) {
			alert.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (listener != null) {
						listener.onButtonClicked(AlertButtonCode.NEGATIVE);
					}

				}
			});
		}
		if (neutralButtonText != null) {
			alert.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (listener != null) {
						listener.onButtonClicked(AlertButtonCode.NEUTRAL);
					}

				}
			});
		}
		AlertDialog dialog = alert.create();
		dialog.setCanceledOnTouchOutside(false);//Confirm dialog shoudn't be closed on out side click, because user has to select yes/no
		dialog.show();
	}// showAlert()
}
