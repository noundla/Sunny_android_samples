package com.vl.samples.listview;



import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.vl.samples.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable{

	private boolean mChecked;
	
	public CheckableLinearLayout(Context context) {
		super(context);
		
	}
	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	
	@Override
	public void setSelected(boolean selected) {
		setChecked(selected);
	}
	
	
	@Override
	public void setChecked(boolean checked) {
		StateListDrawable sld = (StateListDrawable) getBackground();
		if(checked) {
			//android:background="@drawable/genre_cell_selector" 
			//android:textColor="@color/cell_highlighted"
			if(sld!=null)
				sld.setState(new int[]{android.R.attr.state_checked});
			
			View v = findViewById(R.id.layout);
			setHighlighted(v,true);
			
		} else {
			if(sld!=null)
				sld.setState(new int[]{android.R.attr.state_empty});
			
			View v = findViewById(R.id.layout);
			setHighlighted(v,false);
		}
		postInvalidate();
		
	}
	@Override
	public boolean isChecked() {
		return mChecked;
	}
	@Override
	public void toggle() {
		mChecked = !mChecked;
		postInvalidate();
	}
	
	
	private void setHighlighted(View v,boolean isHighlighted)
	{
		if(v instanceof Checkable ){
			((Checkable)v).setChecked(isHighlighted);
		}
		else{
			v.setSelected(isHighlighted);
		}
			
	}
	
	

}
