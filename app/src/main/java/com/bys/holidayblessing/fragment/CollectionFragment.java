package com.bys.holidayblessing.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.activity.DetailsActivity;
import com.bys.holidayblessing.adapter.CollectionAdapter;
import com.bys.holidayblessing.tools.Common;
import com.bys.holidayblessing.tools.DBHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类
 * 
 * @author Bys
 *
 */
public class CollectionFragment extends Fragment {

	private Context mContext;
	private DBHelper db;

	private List<Map<String, Object>> mDataIndex;
	private CollectionAdapter adapter;

	@ViewInject(R.id.listviewIndex)
	private ExpandableListView listviewIndex;
	@ViewInject(R.id.title_txt)
	private TextView textView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_collection, container,
				false);
		ViewUtils.inject(this, view);
		this.db = new DBHelper(mContext);
		loadlistviewIndex();
		textView.setText(getString(R.string.tab_collection));
		return view;
	}

	@OnChildClick(R.id.listviewIndex)
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listsong = (List<Map<String, Object>>) mDataIndex
				.get(groupPosition).get("song");
		Intent intent = new Intent();
		intent.setClass(mContext, DetailsActivity.class);
		intent.putExtra("song", listsong.get(childPosition).get("song")
				.toString());
		intent.putExtra("classname",
				listsong.get(childPosition).get("classname").toString());
		startActivity(intent);
		return false;
	}

	private void loadlistviewIndex() {
		this.mDataIndex = getDataIndex();
		adapter = new CollectionAdapter(mContext, mDataIndex);
		this.listviewIndex.setAdapter(adapter);
	}

	/**
	 * 获取分类列表
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getDataIndex() {
		List<Map<String, Object>> localArrayList = new ArrayList<Map<String, Object>>();
		Cursor localCursor2 = this.db
				.query("select id,classname,grade,song,classs,ids from dx_class where isok=1 and isdh>=1 order by isdh");
		if (null != localCursor2) {
			while (localCursor2.moveToNext()) {
				HashMap<String, Object> localHashMap1 = new HashMap<String, Object>();
				localHashMap1.put("ItemImage",
						Integer.valueOf(R.drawable.img_list_left_page));
				localHashMap1.put("classid", localCursor2.getString(0));
				localHashMap1.put("classname", localCursor2.getString(1));
				localHashMap1.put("grade", localCursor2.getString(2));
				localHashMap1.put("classs", localCursor2.getString(4));
				localHashMap1.put("ids", localCursor2.getString(5) + "条");
				localHashMap1.put("rl", "");
				localHashMap1.put(
						"song",
						getDataChild(localCursor2.getString(0),
								localCursor2.getString(1)));
				localHashMap1.put("ItemImage_right",
						Integer.valueOf(R.drawable.img_list_more_item));
				localArrayList.add(localHashMap1);
			}
			localCursor2.close();
		}
		return localArrayList;
	}

	/**
	 * 获取子列表
	 * 
	 * @param classid
	 * @param classname
	 * @return
	 */
	private List<Map<String, Object>> getDataChild(String classid,
			String classname) {
		List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		Cursor cursor = this.db
				.query("select id,classname,grade,song,classs,ids,rl,rlrq,-3 as jgdays from dx_class where isok=1 and (rootclass='"
						+ classname
						+ "' or topclass='"
						+ classname
						+ "') order by isdh,sortid");
		if (null != cursor) {
			while (cursor.moveToNext()) {
				HashMap<String, Object> localHashMap2 = new HashMap<String, Object>();
				localHashMap2.put("ItemImage",
						Integer.valueOf(R.drawable.img_list_left));
				localHashMap2.put("classid", cursor.getString(0));
				localHashMap2.put("classname", cursor.getString(1));
				localHashMap2.put("grade", cursor.getString(2));
				localHashMap2.put("song",
						("," + cursor.getString(3)).replace(",,", ","));
				localHashMap2.put("classs", "");
				localHashMap2.put("ids", cursor.getString(5) + "条");
				int i = cursor.getInt(8);
				if (i <= -3) {
					localHashMap2.put("rl", cursor.getString(7));
				} else {
					String str2 = Common.getjgdaysname(i);
					localHashMap2.put("rl", cursor.getString(7) + str2);
				}
				arrayList.add(localHashMap2);
			}
			cursor.close();
		}
		return arrayList;
	}
}
