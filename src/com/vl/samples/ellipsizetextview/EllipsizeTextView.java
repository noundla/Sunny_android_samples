package com.vl.samples.ellipsizetextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.LeadingMarginSpan.LeadingMarginSpan2;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class EllipsizeTextView extends TextView {
	private static String TAG = "EllipsizeTextView";
	private String mText=null;
	private final int MAX_LINES = 3;
	private EllipsizeTextView instance;
	public EllipsizeTextView(Context context) {
		super(context);
		
	}
	public EllipsizeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	public EllipsizeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	private void wrapText(String text) {
		instance = this;
		mText = text;
		this.post(new Runnable() {

			@Override
			public void run() {

				int[] messageLocations = new int[2];
				getLocationOnScreen(messageLocations);
				int lineCount = getLineCount();
				Log.d(TAG, "dddddddddd-line count: "+getLineCount());
				if(lineCount>MAX_LINES){
					instance.setMaxLines(MAX_LINES);
					int end = mText.length();			
					SpannableString ss = new SpannableString(mText);
					ss.setSpan(new MyLeadingMarginSpan2(MAX_LINES, instance), 0, end, 0);
					instance.setText(ss);
				}
			}
		});

	}
	
	
	private static class MyLeadingMarginSpan2 implements LeadingMarginSpan2 {

		private int lines;
		private EllipsizeTextView instance;
		private int count = 0;

		public MyLeadingMarginSpan2(int lines, EllipsizeTextView instance) {
			this.lines = lines;
			this.instance = instance;
		}

		@Override
		public int getLeadingMargin(boolean first) {
			return 0;
		}

		@Override
		public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top,
				int baseline, int bottom, CharSequence text, int start, int end,
				boolean first, Layout layout) {
			if(start == 0)
				count = 0;
			count++;
			if(lines == count) {
				float textwidth = instance.getPaint().measureText(text.subSequence(start, end).toString());
				float dotsTextWidth = instance.getPaint().measureText("...");
				float viewWidth = instance.getMeasuredWidth();
				if((textwidth+dotsTextWidth) < viewWidth){
					//it means some empty space is available after the text, so feel free to add the dots
					instance.setSpilttedTextView(end);
				}else{
					//need to remove 3 letters from the text because no extra space is available to fill the dots
					instance.setSpilttedTextView(end-3);
					
					
					//remove the last word instead of 3 letters and fill the dots
					//String tt = mText.subSequence(0, end).toString();
					//int lastWordPos = tt.lastIndexOf(" ");
					//instance.setSpilttedTextView(lastWordPos);
				}
				
			}
		}

		@Override
		public int getLeadingMarginLineCount() {
			return lines;
		}
	}

	public void setSpilttedTextView(int end) {
		setText(mText.substring(0, end)+"...");
	}
	
	public void setCustomText(String text) {
		setText(text);
		wrapText(text);
	}

}
