package com.vl.samples.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
	private static final String TAG= "CustomScrollView";
	public CustomScrollView(Context context) {
		super(context);

	}
	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}
	
	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "onInterceptTouchEvent: DOWN super false" );
                    super.onTouchEvent(ev);
                    break;

            case MotionEvent.ACTION_MOVE:
                    return false; // redirect MotionEvents to ourself

            case MotionEvent.ACTION_CANCEL:
                    Log.d(TAG, "onInterceptTouchEvent: CANCEL super false" );
                    super.onTouchEvent(ev);
                    break;

            case MotionEvent.ACTION_UP:
                    Log.d(TAG, "onInterceptTouchEvent: UP super false" );
                    return false;

            default: Log.d(TAG, "onInterceptTouchEvent: " + action ); break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        Log.i(TAG, "onTouchEvent. action: " + ev.getAction() );
         return true;
    }

}
