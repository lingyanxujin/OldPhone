package com.tan.oldphone.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.tan.oldphone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HorizontalScrollViewAdapter {

	private LayoutInflater mInflater;
	private ArrayList<HashMap<String, Object>> mDatas;

	public HorizontalScrollViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> mDatas) {
		mInflater = LayoutInflater.from(context);
		this.mDatas = mDatas;
	}

	public int getCount() {
		return mDatas.size();
	}

	public Object getItem(int position) {
		return mDatas.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.activity_index_gallery_item, parent, false);
			viewHolder.mImg = (ImageView) convertView
					.findViewById(R.id.id_index_gallery_item_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mImg.setImageResource((Integer) mDatas.get(position).get(
				"image"));
		return convertView;
	}

	private class ViewHolder {
		ImageView mImg;
	}

}
