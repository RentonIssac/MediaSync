package com.renton.mediasync;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class StatusExpandAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater = null;
	private List<GroupStatusEntity> groupList;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param oneList
	 */
	public StatusExpandAdapter(Context context,
			List<GroupStatusEntity> group_list) {
		this.groupList = group_list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 返回一级Item总数
	 */
	@Override
	public int getGroupCount() {
		
		return groupList == null ? 0 : groupList.size();
	}

	/**
	 * 返回二级Item总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
	return	groupList == null ? 0
                : (groupList.get(groupPosition) == null ? 0 : (groupList
                        .get(groupPosition).getChildList() == null ? 0
                        : groupList.get(groupPosition).getChildList().size()));
	}

	/**
	 * 获取一级Item内容
	 */
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupList.get(groupPosition);
	}

	/**
	 * 获取二级Item内容
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groupList.get(groupPosition).getChildList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		GroupViewHolder holder = new GroupViewHolder();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.group_status_item, null);
		}
		holder.groupName = (TextView) convertView
				.findViewById(R.id.one_status_name);

		holder.groupName.setText(TimeFormat.format("yyyy.MM.dd",groupList.get(groupPosition).getGroupName()));

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder viewHolder = null;
		ChildStatusEntity entity = (ChildStatusEntity) getChild(groupPosition,
				childPosition);
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.child_status_item, null);
			viewHolder.content_text = (TextView) convertView
					.findViewById(R.id.content_text);
		    viewHolder.img=(ImageView) convertView.findViewById(R.id.img);
		}
		viewHolder.content_text.setText(entity.getContentText());
        viewHolder.img.setImageResource(entity.getImgSrc());
		convertView.setTag(viewHolder);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private class GroupViewHolder {
		TextView groupName;
	}

	private class ChildViewHolder {
		public TextView content_text;
		public ImageView img;
	}

}
