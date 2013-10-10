package com.vl.samples.scrollview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vl.samples.R;

public class ListViewInScrollviewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_in_scrollview);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new String[]{"valuelabs","valuelabs","valuelabs","valuelabs","valuelabs","valuelabs","valuelabs","valuelabs","valuelabs","valuelabs"}));
		
	}
}
