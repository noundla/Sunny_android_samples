package com.vl.samples;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TimePicker;

public class SamplesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TimePicker tp = (TimePicker) this.findViewById(R.id.time_picker);
        tp.setIs24HourView(true);
        /*Typeface myTypeface = Typeface.createFromAsset(this.getAssets(),"fonts/la_fraktouille.ttf");
        ((TextView)findViewById(R.id.t1)).setTypeface(myTypeface);*/
        
    }
}