package com.vl.samples.customDialog;



import android.app.Dialog;
import android.content.Context;
import android.view.View;


import com.vl.samples.R;

public class CustomDialog extends Dialog {
//	private TextView titleView;
//	private TextView messageView;
	
	public CustomDialog(Context context) {
		super(context);
		initContent(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		initContent(context);
	}

	private void initContent(Context context) {
		getWindow().setBackgroundDrawableResource(R.drawable.rocket_thrust);
		View view = View.inflate(context, R.layout.custom_dialog, null);
		view.setBackgroundResource(R.drawable.rocket_thrust);
		setContentView(view);
		
		
	}
	
	
	

	

	/*public void addContentView(View view) {
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.custom_dialog_content);
		viewGroup.addView(view, 0);
	}
	
	public void addContentView(View view, LayoutParams params) {
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.custom_dialog_content);
		viewGroup.addView(view, 0, params);
	}

	public void setButton1(CharSequence text, View.OnClickListener listener) {
		setButton(R.id.custom_dialog_button1, text, listener);
	}

	public void setButton2(CharSequence text, View.OnClickListener listener) {
		setButton(R.id.custom_dialog_button2, text, listener);
	}

	public void setButton3(CharSequence text, View.OnClickListener listener) {
		setButton(R.id.custom_dialog_button3, text, listener);
	}

	private void setButton(int id, CharSequence text, final View.OnClickListener listener) {
		Button b = (Button) findViewById(id);
		b.setText(text);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(listener != null){
					listener.onClick(v);
				}
				CustomDialog.this.dismiss();
			}
		});
		b.setBackgroundDrawable(DrawableSelector.getButton(getContext()));
		b.setVisibility(View.VISIBLE);
	}*/

}
