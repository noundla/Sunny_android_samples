package com.vl.samples.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vl.samples.R;

public class SingleItemSelectionListActivity extends Activity {
	
	public static final String[] array_select_product_name = { "Select Product", "About SchoolDude", "ConserveDirect", "Critical Alarm Automation", "FSAutomation", "FSDirect", "InventoryDirect",
		"ITAMDirect", "ITDirect", "MaintenanceDirect", "MySchoolDude", "PlanningDirect", "PMDirect", "SurveyDirect", "TripDirect", "UtilityDirect" };

	private Activity mActivity;

	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		setContentView(R.layout.single_selection_list);
		mListView = (ListView)findViewById(R.id.listView1);
		mListView.setAdapter(new MyAdapter());
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(mActivity, "clicked:: "+position, Toast.LENGTH_LONG).show();
				
			}
		});
		
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {

			return array_select_product_name.length;
		}

		@Override
		public String getItem(int position) {

			return array_select_product_name[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position,View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView = mActivity.getLayoutInflater().inflate(R.layout.selection_item_row, null);
			}
			TextView textView = (TextView)convertView.findViewById(R.id.text);
			textView.setText(array_select_product_name[position]);
			return convertView;
		}

	}
}
