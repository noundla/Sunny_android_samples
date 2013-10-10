package com.vl.samples.expandablelistview;

import java.util.ArrayList;

import com.vl.samples.R;

import android.app.Activity;
import android.test.MoreAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{
	private Activity mActivity;
	private ArrayList<String> mParentList;
	private ArrayList<String[]> mChildList;
	
	public MyExpandableListAdapter(Activity activity, ArrayList<String> parentList, ArrayList<String[]> childList){
		mActivity = activity;
		mParentList = parentList;
		mChildList = childList;
		

	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		//check for childgroup based on parent group position
		if(mChildList!=null && mChildList.size()>= groupPosition){
			String[] childGroup = mChildList.get(groupPosition); //child group of a specific parent
			//check for child in child group
			if(childGroup!=null && childGroup.length>=childPosition){
				return childGroup[childPosition];
			}
		}
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String str = (String)getChild(groupPosition, childPosition);

		if(convertView==null)
			convertView = mActivity.getLayoutInflater().inflate(R.layout.child_item, null);

		TextView text = (TextView)convertView.findViewById(R.id.textView1);
		text.setText(str);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChildList.get(groupPosition).length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mParentList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mParentList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String str = (String)getGroup(groupPosition);

		if(convertView==null)
			convertView = mActivity.getLayoutInflater().inflate(R.layout.parent_group_item, null);

		TextView text = (TextView)convertView.findViewById(R.id.textView1);
		text.setText(str);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		//here true will enable click event for the child
		return true;
	}
	
	
	


}
