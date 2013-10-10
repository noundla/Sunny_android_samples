package com.vl.samples.charts;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.vl.samples.R;

public class ChartsActivity extends Activity {
	// these_labela has elemnes[label,maxX,maxY]
		static int draw_only_this_idx = -1;
		static int[] drawSizes;
		/**used to draw labels. This specifies the X-Axis start position which means (0,0) position of graph*/
		private int XAxisLineAt=0;

		/**Indicate the length of the line which has to be drawn below X-Axis and before Y-Axis*/
		private int indicationLineLength = 3;
		/**height of the total graph(It means Bitmap height)*/
		private int bitMapHeight = 200;
		/**width of the total graph(It means Bitmap width)*/
		private int bitMapwidth = 320;
		/**graph top margin*/
		private int topMargin = 25;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.charts); 

			ImageView image = (ImageView) findViewById(R.id.testy_img);


			Bitmap emptyBmap = Bitmap.createBitmap(bitMapwidth,
					bitMapHeight, Config.ARGB_8888);

			int width =  emptyBmap.getWidth();
			int height = emptyBmap.getHeight();
			Bitmap charty = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

			charty = quicky_XY(emptyBmap);

			image.setImageBitmap(charty);          
		} 

		public Bitmap quicky_XY(Bitmap bitmap){
			// xode to get bitmap onto screen
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			final int color = 0xffffffff; 
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);   
			final float roundPx = 12;

			// get the little rounded cornered outside
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


			// ---- NOw just draw on this bitmap


			// Set the labels info manually
			String[] cur_elt_array = new String[4];
			cur_elt_array[0]="Voltage";
			cur_elt_array[1]="volts";
			cur_elt_array[2]="99";  // max-graph Y-axis
			cur_elt_array[3]="0";    //min-graph Y-axis

			Vector labels = new Vector();
			labels.add(cur_elt_array);

			draw_the_grid(canvas,labels);  
			drawXAndYAxis(canvas);


			//plotting the lines
			Vector data_2_plot = new Vector();  

			data_2_plot.add("0.2") ;
			data_2_plot.add("1.2") ;
			data_2_plot.add("9.6") ;
			data_2_plot.add("100") ;
			data_2_plot.add("44.2") ;
			data_2_plot.add("20.2") ;
			data_2_plot.add("16.2") ;
			data_2_plot.add("20.2") ;
			data_2_plot.add("16.2") ;

			plotArrayList(canvas ,  data_2_plot ,  labels , "the title" , 0 ,Color.GREEN); 

			//plotting the lines
			Vector data_2_plot1 = new Vector();  

			data_2_plot1.add("4.2") ;
			data_2_plot1.add("2.2") ;
			data_2_plot1.add("8.6") ;
			data_2_plot1.add("60") ;
			data_2_plot1.add("94.2") ;
			data_2_plot1.add("80.2") ;
			data_2_plot1.add("66.2") ;
			plotArrayList(canvas ,  data_2_plot1 ,  labels , "the title" , 0 ,Color.RED);

			canvas.drawBitmap(bitmap, rect, rect, paint);

			return output;
		}    


		// these_labels is vector of [label,units,max.min]
		//drawing the simple graph
		public void  draw_the_grid(Canvas canvas,  Vector these_labels){
			double rounded_max = 0.0;
			double rounded_min = 0.0;
			double rounded_max_temp;
			Object curElt;  
			String[] cur_elt_array;
			int left_margin_d, right_margin_d;



			if( draw_only_this_idx == -1)      
				curElt = these_labels.elementAt(0);  // default  it to 1st one if non set 
			else
				curElt = these_labels.elementAt(draw_only_this_idx);  // now just the 1st elt

			cur_elt_array = (String[])curElt;

			rounded_max = getCeilOrFloor(Double.parseDouble(cur_elt_array[2]) , true);

			rounded_min = getCeilOrFloor(Double.parseDouble(cur_elt_array[3]) , false);
			Log.d("rounded_max , rounded_min", rounded_max+" , "+rounded_min);

			// ok so now we have the max value of the set just get a cool ceiling and we go on
			final Paint paint = new Paint();  
			paint.setTextSize(15);
			//left_margin_d: will hold the y-axis highest label width
			left_margin_d =  getCurTextLengthInPixels(paint, Double.toString(rounded_max));
			Log.d("left_margin_d", left_margin_d+"");
			//keep the position for later drawing -- leave space for the legend

			//		int p_height = 170;
			//		int p_width = 220;

			int p_height = bitMapHeight-30;
			int p_width = bitMapwidth-30;
			int[] tmp_draw_sizes = {2 + left_margin_d, topMargin,p_width - 2 - left_margin_d ,p_height - topMargin -5};
			drawSizes = tmp_draw_sizes; //keep it for later processing


			//with the margins worked out draw the plotting grid
			paint.setStyle(Paint.Style.FILL); 
			paint.setColor(Color.parseColor("#fffacd"));  

			//drawing background of the graph
			canvas.drawRect(drawSizes[0], drawSizes[1],drawSizes[0]+ drawSizes[2], drawSizes[1]+ drawSizes[3] , paint);

			//--------------finally draw the grid lines-------------//
			paint.setColor(Color.GRAY );
			paint.setStyle(Paint.Style.STROKE);
			//drawing outline of the graph background
			canvas.drawRect(drawSizes[0], drawSizes[1],drawSizes[0]+ drawSizes[2], drawSizes[1]+ drawSizes[3] , paint);

			//draw vertical grid lines
			int verticalLines = 30;
			for(int i=1; i < verticalLines ; i++){
				paint.setColor(Color.GRAY );
				//vertical lines
				canvas.drawLine(drawSizes[0]+ (i * drawSizes[2] / verticalLines), drawSizes[1], drawSizes[0] + (i * drawSizes[2] / verticalLines), drawSizes[1] + drawSizes[3], paint);

				drawSmallLines:{
					paint.setColor(Color.BLACK );
					int indicationLineLength2 = indicationLineLength;
					if(i%3==0 )
						indicationLineLength2 = indicationLineLength2+4;

					//drawing small line under vertical lines(this draws all lines except 1st(0th) and last position)
					canvas.drawLine(drawSizes[0] + (i * drawSizes[2] / verticalLines), drawSizes[1] + drawSizes[3],drawSizes[0] + (i * drawSizes[2] / verticalLines),drawSizes[1] + drawSizes[3]+indicationLineLength2, paint);

					//at first line(0th position)
					if(i==1)
						canvas.drawLine(drawSizes[0] + ((i-1) * drawSizes[2] / verticalLines), drawSizes[1] + drawSizes[3],drawSizes[0] + ((i-1) * drawSizes[2] / verticalLines),drawSizes[1] + drawSizes[3]+indicationLineLength+4, paint);
					//at last position
					if(i==verticalLines-1)
						canvas.drawLine(drawSizes[0] + ((i+1) * drawSizes[2] / verticalLines), drawSizes[1] + drawSizes[3],drawSizes[0] + ((i+1) * drawSizes[2] / verticalLines),drawSizes[1] + drawSizes[3]+indicationLineLength+4, paint);
				}
			}
			int horizontalLines = 5;
			for(int i=1; i < horizontalLines ; i++)
			{
				paint.setColor(Color.GRAY );
				//horizontal lines
				canvas.drawLine(drawSizes[0], drawSizes[1] + (i * drawSizes[3] / horizontalLines), drawSizes[0] + drawSizes[2], drawSizes[1] + (i * drawSizes[3] / horizontalLines), paint);


				drawSmallLines:{
					paint.setColor(Color.BLACK );
					//drawing small line before horizontal lines(this draws all lines except 1st(0th) and last position)
					canvas.drawLine(drawSizes[0]-indicationLineLength, drawSizes[1] + (i * drawSizes[3] / horizontalLines), drawSizes[0] , drawSizes[1] + (i * drawSizes[3] / horizontalLines), paint);
					//at first line
					if(i==1){
						canvas.drawLine(drawSizes[0]-indicationLineLength, drawSizes[1] + ((i-1) * drawSizes[3] / horizontalLines), drawSizes[0] , drawSizes[1] + ((i-1) * drawSizes[3] / horizontalLines), paint);
					}
					//at last position
					if(i==horizontalLines-1){
						canvas.drawLine(drawSizes[0]-indicationLineLength, drawSizes[1] + ((i+1) * drawSizes[3] / horizontalLines), drawSizes[0] , drawSizes[1] + ((i+1) * drawSizes[3] / horizontalLines), paint);
						XAxisLineAt = drawSizes[1] + ((i+1) * drawSizes[3] / horizontalLines);
					}
				}
			}


			//draw rectangle at last two blocks(This requires in Adeptra app graph)
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.GRAY );
			canvas.drawRect(drawSizes[2]+ drawSizes[0]-2*(drawSizes[2]/verticalLines), topMargin, drawSizes[0]+drawSizes[2], drawSizes[1]+drawSizes[3], paint);

			//draw 'now' text vertically
			paint.setTextSize(10);
			Rect rect = new Rect();
			paint.getTextBounds("now", 0, "now".length(), rect);
			paint.setColor(Color.RED);
			canvas.drawText("now",drawSizes[2]+ drawSizes[0]-2*(drawSizes[2]/verticalLines) , drawSizes[1]+(drawSizes[3]/2), paint);
			
			
			
			//drawing Y-Axis labels
			drawYAaxisValuesForGrid(canvas, cur_elt_array[1] , ((int)rounded_max)+"" , ((int)rounded_min)+"", cur_elt_array[0] , 2 ,0 );
			//drawing X-Axis labels
			drawXAxisLablesForGrid(canvas,new String[]{});
		}  // --- end of draw_grid --- 

		// provate void print_axis_values_4_grid(Graphics thisDrawingArea, string cur_units , string cur_max , string cur_min , string cur_label , ByVal x_guide As Integer, ByVal this_idx As Integer)
		public void drawYAaxisValuesForGrid(Canvas thisDrawingArea, String cur_units , String cur_max , String cur_min , String cur_label , int  x_guide , int  this_idx ){
			String this_str;
			int delta = ( Integer.valueOf(cur_max).intValue()  -  Integer.valueOf(cur_min).intValue() ) / 5;
			final Paint paint = new Paint();

			paint.setColor( Color.BLACK ); 
			paint.setTypeface( Typeface.MONOSPACE ); 

			//Font smallyFont = Font.getDefault().derive(Font.PLAIN, 12);
			// thisDrawingArea.setFont(smallyFont);
			paint.setTextSize(8);


			for(int i = 0; i<6 ; i++)
			{
				// 'work our the values so is proper


				this_str =Integer.toString( (Integer.valueOf(cur_min).intValue()  + delta * i) );


				//			final int point = this_str.indexOf('.');
				//			if (point > 0) {
				//				// If has a decimal point, may need to clip off after or force 2 decimal places
				//				this_str = this_str + "00";
				//				this_str =  this_str.substring(0,point+3);
				//			} else {
				//				//this_str = this_str + ".00";
				//			}

				thisDrawingArea.drawText(this_str,  drawSizes[0]-5-getCurTextLengthInPixels(paint, this_str), drawSizes[1] + drawSizes[3]  -  (i * drawSizes[3] / 5)+3  , paint);
				//(or)
				//			if (i == 5) 
				//				//                  thisDrawingArea.drawText(this_str,  x_guide - 2, drawSizes[1] + drawSizes[3] - (i *drawSizes[3] / 5)  );
				//				thisDrawingArea.drawText(this_str,  x_guide - 2, drawSizes[1] + drawSizes[3] - (i *drawSizes[3] / 5) , paint );
				//			else
				//				//thisDrawingArea.drawText(this_str,  x_guide- 2, drawSizes[1] + drawSizes[3]  -  (i * drawSizes[3] / 5) -3, paint);
				//				thisDrawingArea.drawText(this_str,  drawSizes[0]-3-getCurTextLengthInPixels(paint, this_str), drawSizes[1] + drawSizes[3]  -  (i * drawSizes[3] / 5)+3  , paint);
			}

			// smallyFont = Font.getDefault().derive(Font.BOLD, 12);
			// thisDrawingArea.setFont(smallyFont);
			paint.setTextSize(10);
			switch(this_idx )  
			{
			case 0:
				//drawing the title based on graphWidth and title length
				thisDrawingArea.drawText(cur_label ,  (bitMapwidth/2)-(getCurTextLengthInPixels(paint, cur_label)/2), drawSizes[1] -15 , paint);
				break;

			case 1:

				// int len = getFont().getAdvance(cur_label +" - " +cur_units)
				thisDrawingArea.drawText("  " + cur_label +" - " +cur_units,  x_guide - 2 -30, drawSizes[1] -15, paint );
				break;

			}



		}  // --- end of print_axis_values_4_grid ---

		/**Draws labels for X-Axis.
		 * @param canvas Canvas object reference to draw labels on it
		 * @param xLabels labels of X-Axis*/
		private void drawXAxisLablesForGrid(Canvas canvas,String[] xLabels){
			final Paint paint = new Paint();
			int textSize=7;
			paint.setColor( Color.BLACK ); 
			paint.setTypeface( Typeface.MONOSPACE ); 
			paint.setTextSize(textSize);


			int yPosition = XAxisLineAt + indicationLineLength + textSize + 1;
			final int totalLengthOf3Lines = drawSizes[2]/10;

			Date d = new Date();
			d.setHours(10); //FIXME: 10-> start hour at 0th position
			d.setMinutes(57);//FIXME: 57-> start time at 0th position
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			for(int i=0;i<=10;i++){
				if(i%2==0)
					paint.setColor(Color.BLUE);
				else
					paint.setColor(Color.BLACK);

				canvas.drawText(sdf.format(d), ((totalLengthOf3Lines*i)+drawSizes[0])-getCurTextLengthInPixels(paint, sdf.format(d))/2, yPosition, paint);
				d.setMinutes(24+d.getMinutes());//FIXME: 24-> interval time in minutes
			}
		}
		/**draw X and Y axis lines with heavy stroke
		 * @param canvas Canvas object reference to draw lines on it*/
		private void drawXAndYAxis(Canvas canvas){
			Paint paint = new Paint();
			paint.setStrokeWidth(1.2f);
			paint.setColor(Color.BLACK);
			canvas.drawLine(drawSizes[0], drawSizes[3]+topMargin, drawSizes[0]+drawSizes[2], drawSizes[3]+topMargin, paint);
			canvas.drawLine(drawSizes[0], drawSizes[1], drawSizes[0], drawSizes[3]+topMargin, paint);
		}

		private Point  scale_point(int this_x , double this_y  , Point drawPoint , 
				int scr_x  , int scr_y  , int scr_width  , int src_height  , 
				double maxX  , double minX  , double  maxY  , double minY  ){
			int temp_x, temp_y;
			Point temp = new Point();   

			if (maxY == minY)  //skip bad data
				return null;

			//don't touch it if is nothing
			try{
				temp_x = scr_x + (int)( ((double)this_x - minX) * ((double)scr_width / (maxX - minX)) );
				temp_y = scr_y + (int)( (maxY - this_y) * ((double)src_height / (maxY - minY)) );

				temp.x = temp_x;
				temp.y= temp_y;
				drawPoint = temp;
			} 
			catch  (Exception e){
				return null;
			}

			return temp;

		} // --- end of scale_point --

		public boolean plotArrayList(Canvas cavas, Vector arrayList , Vector labels , String title , int only_this_idx ,int lineColor){
			int idx;
			int lRow ;
			int nParms;
			int  i, points2Plot, shifted_idx ; 
			int prevX, prevY ;
			int curX=0, curY=0 ; 

			Point currentPoint = new Point();
			currentPoint.set(0,0);

			double curMaxX, curMinX, curMaxY=20, curMinY=0, cur_rangeY;
			int curStartX, curPoints2Plot; 

			final int POINTS_TO_CHANGE = 30;
			double cur_OBD_val;

			//Object curElt;  
			String curElt; 
			String[] cur_elt_array;
			Object curElt2;  
			String[] cur_elt_array2;

			final Paint paint = new Paint();

			try{

				points2Plot = arrayList.size();
				{
					curStartX = 0;
					curPoints2Plot = points2Plot;
					curMaxX = curPoints2Plot;
					curMinX = 0;
				}

				//'Create the plot points for this series from the ChartPoints array:

				curElt = (String)arrayList.elementAt(0);

				//the lines have to come out good
				paint.setStyle(Paint.Style.STROKE);
				//                  
				//for(  nParms = 0 ; nParms < cur_elt_array.length ; nParms++ )
				nParms = only_this_idx;
				{

					//get cur item labels
					curElt2 = labels.elementAt(nParms);
					cur_elt_array2  = (String[]) curElt2;

					curMaxY = getCeilOrFloor (Double.parseDouble(cur_elt_array2[2]) , true);
					curMinY = getCeilOrFloor (Double.parseDouble(cur_elt_array2[3]) , false);

					curPoints2Plot = arrayList.size();                     
					curMaxX = curPoints2Plot;

					curElt = (String)arrayList.elementAt(0);
					cur_OBD_val = Double.parseDouble( curElt);

					currentPoint = scale_point(0, cur_OBD_val, currentPoint, 
							drawSizes[0], drawSizes[1], drawSizes[2], drawSizes[3], 
							curMaxX, curMinX, curMaxY, curMinY); //'(CInt(curAxisValues.Mins(nParms - 2) / 5) + 1) * 5)

					curX = currentPoint.x;
					curY = currentPoint.y;

					paint.setColor(lineColor);


					// the point is only cool when samples are low
					if ( curPoints2Plot < POINTS_TO_CHANGE)
						cavas.drawRect(curX-2, curY-2, curX-2 + 4,curY-2+ 4 , paint); 

					prevX = curX;
					prevY = curY;



					//'go and plot point for this parm -- pont after the 1st one 
					for (lRow = curStartX +1 ; lRow< curStartX + curPoints2Plot -1 ; lRow++)
					{

						curElt = (String)arrayList.elementAt(lRow);

						cur_OBD_val = Double.parseDouble( curElt);




						//'work out an approx if cur Y values not avail(e.g. nothing)
						// if (! (cur_elt_array[nParms ] == null ) )   //skip bad one
						if( cur_OBD_val == Double.NaN) continue;  //skip bad one
						{                  


							currentPoint=scale_point(lRow, cur_OBD_val, currentPoint,  
									drawSizes[0], drawSizes[1], drawSizes[2], drawSizes[3], 
									curMaxX, curMinX, curMaxY, curMinY);

							curX = currentPoint.x;
							curY = currentPoint.y;

							if ( curPoints2Plot < POINTS_TO_CHANGE)
								cavas.drawRect(curX-2, curY-2, curX-2 +4, curY-2 + 4, paint ); 

							cavas.drawLine( prevX, prevY, curX, curY, paint);
							prevX = curX;
							prevY = curY;

						} // ' if end of this_array(lRow, nParms - 1)<> nothing

					} // end of for lrow

				} // end of for nParmns


				//cavas.invalidate();
				return true;
			}
			catch (Exception e)
			{
				return false;

			}

		} // --- end of plot_array_list  --


		// need the width of the labels
		private int getCurTextLengthInPixels(Paint this_paint, String this_text) {
			FontMetrics tp = this_paint.getFontMetrics();
			Rect rect = new Rect();
			this_paint.getTextBounds(this_text, 0, this_text.length(), rect);
			return rect.width();
		} // --- end of getCurTextLengthInPixels  ---


		public double getCeilOrFloor(double this_val ,  boolean is_max  ){
			double this_min_tmp;
			int  this_sign;
			int  this_10_factor=0;
			double this_rounded;

			if (this_val == 0.0) 
			{
				this_rounded = 0.0;
				return this_rounded;
			}

			this_min_tmp = Math.abs(this_val);

			if (this_min_tmp >= 1.0 && this_min_tmp < 10.0) 
				this_10_factor = 1;
			else if (this_min_tmp >= 10.0 && this_min_tmp < 100.0) 
				this_10_factor = 10;
			else if (this_min_tmp >= 100.0 && this_min_tmp < 1000.0) 
				this_10_factor = 100;
			else if (this_min_tmp >= 1000.0 && this_min_tmp < 10000.0) 
				this_10_factor = 1000;
			else if (this_min_tmp >= 10000.0 && this_min_tmp < 100000.0) 
				this_10_factor = 10000;



			//'cover when min is pos and neg
			if (is_max) 
			{
				if (this_val > 0.0) 
					this_sign = 1;
				else
					this_sign = -1;

			}    
			else
			{
				if (this_val > 0.0) 
					this_sign = -1;
				else
					this_sign = 1;

			}


			if (this_min_tmp > 1) 
				this_rounded = (double)(((int)(this_min_tmp / this_10_factor) + this_sign) * this_10_factor);
			else
			{
				this_rounded = (int)(this_min_tmp * 100.0);
				//' cover same as above bfir number up to .001 less than tha it will skip
				if (this_rounded >= 1 && this_rounded < 9) 
					this_10_factor = 1;
				else if (this_rounded >= 10 && this_rounded < 99) 
					this_10_factor = 10;
				else if (this_rounded >= 100 && this_rounded < 999) 
					this_10_factor = 100;

				this_rounded = (double)(((int)((this_rounded) / this_10_factor) + this_sign) * this_10_factor);
				this_rounded = (int)(this_rounded) / 100.0;

			}

			if (this_val < 0) 
				this_rounded = -this_rounded;

			return  this_rounded;

		} // --- end of get_ceiling_or_floor ---

}
