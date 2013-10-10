package com.vl.samples.ellipsizetextview;

import com.vl.samples.R;

import android.app.Activity;
import android.os.Bundle;

public class EllipsizeTextViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ellipsize);
		EllipsizeTextView ellipsizeTextView = (EllipsizeTextView)findViewById(R.id.ellipsizeTextView1);
		ellipsizeTextView.setCustomText("sdfasdfasd fgusdg fjhsadg fhgasd fjghadfjghs adf asdgh fadfsdghafjsga dhas dfhasdfsdfh gshad fjhsa gdfgasjdfjasjdfasdfjgasjdfhgjhsdafgshadgfjhgasjdfgahsdgasdjfhgasjfhgasjdfhgasjdfjshadfgjsdhfgjhsadgfshadgfjasdhfgjhsdagf");
		EllipsizeTextView ellipsizeTextViews = (EllipsizeTextView)findViewById(R.id.ellipsizeTextView2);
		ellipsizeTextViews.setCustomText("sdfasdasdfsafsdfasdfsdffasd fgusdg fjhsadg fhgasd fjghadfjghs adf asdgh fadfsdghafjsga dhas dfhasdfsdfh gshad fjhsa gdfgasjdfjasjdfasdfjgasjdfhgjhsdafgshadgfjhgasjdfgahsdgasdjfhgasjfhgasjdfhgasjdfjshadfgjsdhfgjhsadgfshadgfjasdhfgjhsdagf");
	}
}
