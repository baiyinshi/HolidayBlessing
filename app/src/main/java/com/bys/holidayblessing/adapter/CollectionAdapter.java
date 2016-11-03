package com.bys.holidayblessing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

public class CollectionAdapter extends BaseExpandableListAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private LayoutInflater inflater;

	public CollectionAdapter(Context mContext,
			List<Map<String, Object>> mDataIndex) {
		this.context = mContext;
		this.list = mDataIndex;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return list.get(arg0).get("song");
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		HoldeView1 holdeView;
		if (null == arg3) {
			holdeView = new HoldeView1();
			arg3 = inflater.inflate(R.layout.collection_list_child, null);
			ViewUtils.inject(holdeView, arg3);
			arg3.setTag(holdeView);
		} else {
			holdeView = (HoldeView1) arg3.getTag();
		}
		holdeView.ItemImage.setImageResource(R.drawable.img_list_left_star);
		holdeView.classname.setText(((List<Map<String, Object>>) list.get(arg0)
				.get("song")).get(arg1).get("classname").toString());
		// holdeView.rl.setText(((List<Map<String, Object>>) list.get(arg0).get(
		// "song")).get(arg1).get("rl").toString());
		holdeView.classs.setText(((List<Map<String, Object>>) list.get(arg0)
				.get("song")).get(arg1).get("classs").toString());
		holdeView.ids.setText(((List<Map<String, Object>>) list.get(arg0).get(
				"song")).get(arg1).get("ids").toString());
		holdeView.ItemImage_right.setImageResource(R.drawable.image_list_right);
		return arg3;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return Integer.parseInt(list.get(arg0).get("classs").toString());
	}

	@Override
	public Object getGroup(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		HoldeView holdeView;
		if (null == arg2) {
			holdeView = new HoldeView();
			arg2 = inflater.inflate(R.layout.main_listview, null);
			ViewUtils.inject(holdeView, arg2);
			arg2.setTag(holdeView);
		} else {
			holdeView = (HoldeView) arg2.getTag();
		}
		holdeView.ItemImage.setImageResource(R.drawable.img_list_left_page);
		holdeView.classname.setText(list.get(arg0).get("classname").toString());
		holdeView.rl.setText(list.get(arg0).get("rl").toString());
		holdeView.classs.setText(list.get(arg0).get("classs") + "项 ");
		holdeView.ids.setText(list.get(arg0).get("ids").toString());
		holdeView.ItemImage_right
				.setImageResource(R.drawable.img_list_more_item);
		return arg2;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	class HoldeView {
		@ViewInject(R.id.ItemImage)
		ImageView ItemImage;
		@ViewInject(R.id.ItemTitle)
		TextView classname;
		@ViewInject(R.id.ItemRl)
		TextView rl;
		@ViewInject(R.id.ItemClasss)
		TextView classs;
		@ViewInject(R.id.ItemIds)
		TextView ids;
		@ViewInject(R.id.ItemImage_right)
		ImageView ItemImage_right;
	}

	class HoldeView1 {
		@ViewInject(R.id.ItemImage)
		ImageView ItemImage;
		@ViewInject(R.id.ItemTitle)
		TextView classname;
		@ViewInject(R.id.ItemRl)
		TextView rl;
		@ViewInject(R.id.ItemClasss)
		TextView classs;
		@ViewInject(R.id.ItemIds)
		TextView ids;
		@ViewInject(R.id.ItemImage_right)
		ImageView ItemImage_right;
	}
}
