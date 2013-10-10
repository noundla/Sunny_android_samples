package com.vl.samples.activityForResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vl.samples.R;

public class ResultActivity extends Activity {
	EditText m_et ;
	Button m_okBtn;
	private final int RESULT=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		m_et = (EditText)findViewById(R.id.editText1);
		m_et.setVisibility(View.VISIBLE);
		
		m_okBtn = (Button)findViewById(R.id.ok);
		m_okBtn.setVisibility(View.VISIBLE);
		
		m_okBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String resultdata = m_et.getText().toString();
				Intent result = new Intent();
				result.putExtra("returnData", resultdata);
				setResult(RESULT_OK, result);
				finish();
			}
		});
		
		
	}
}
