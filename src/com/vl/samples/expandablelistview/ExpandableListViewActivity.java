package com.vl.samples.expandablelistview;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.vl.samples.R;

public class ExpandableListViewActivity extends Activity {
	private ExpandableListView mExpandableListView;
	//parent group
	private ArrayList<String> parentList;
	//each child 
	private String[] countryList = new String[]{"India","Pak","US","UK","Bang","Dubai","China","Japan"};
	private String[] stateList = new String[]{"AP","MP","Karnataka","Tamilnadu","Kerala"};
	private String[] distList = new String[]{"Karimnagar","Chittoor","Kurnool","Kadapa","Ananthapuram","Adilabad","RR"};
	private String[] Town = new String[]{"Chittoor-town","Jagtial","Kurnool-town","Metpally","C.pet"};
	private String[] village = new String[]{"Kpalem","BarathNagar","K.kad","Cherlapally"};
	
	//child list at one place
	private ArrayList<String[]> childList;
	
	private MyExpandableListAdapter  myExpandableListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_listview);
		
		mExpandableListView = (ExpandableListView)findViewById(R.id.expandableListView1);
		
		parentList = new ArrayList<String>();
		parentList.add("Contry");
		parentList.add("State");
		parentList.add("District");
		parentList.add("Town");
		parentList.add("Village");
		
		
		
		
		childList = new ArrayList<String[]>();
		childList.add(countryList);
		childList.add(stateList);
		childList.add(distList);
		childList.add(Town);
		childList.add(village);
		
		
		myExpandableListAdapter = new MyExpandableListAdapter(this,parentList,childList);
		mExpandableListView.setAdapter(myExpandableListAdapter);
		mExpandableListView.setGroupIndicator(null);
		
		expandGroups();
		
		mExpandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Log.d("sssssssss", "sssssssssssssss");
				Toast.makeText(ExpandableListViewActivity.this, "parent:"+groupPosition+", child:"+childPosition+" clicked", Toast.LENGTH_LONG).show();
				return true;
			}
		});
		
		mExpandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				Toast.makeText(ExpandableListViewActivity.this, "parent:"+groupPosition+" clicked", Toast.LENGTH_LONG).show();
				return false;
			}
		});
	}
	private void expandGroups(){
		for(int i=0;i<myExpandableListAdapter.getGroupCount();i++){
			mExpandableListView.expandGroup(i);
		}
	}
}
