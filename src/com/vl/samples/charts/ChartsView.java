package com.vl.samples.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ChartsView extends View {
	
	Paint paint = new Paint();
	public ChartsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ChartsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ChartsView(Context context) {
		super(context);
		init();
	}

	public void init(){
		paint.setColor(Color.BLUE);//here you can set any color you wish for your line
	}
	public void showData(){
		
	}

	@Override
	public void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		
		int leftBottomBorder = 50;
		int rightTopBorder = 10;
		
		if(width>leftBottomBorder && height>0){
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(2);
			//draw X-axis
			canvas.drawLine(leftBottomBorder, height-leftBottomBorder, width-rightTopBorder, height-leftBottomBorder, paint);
			//draw Y-axis
			canvas.drawLine(leftBottomBorder, rightTopBorder, leftBottomBorder, height-leftBottomBorder, paint);
			//draw numbers

		}
	}

}
