package com.bys.holidayblessing.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.activity.DetailsActivity;
import com.bys.holidayblessing.activity.SearchActivity;
import com.bys.holidayblessing.tools.Common;
import com.bys.holidayblessing.tools.DBHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节日
 * 
 * @author Bys
 *
 */
public class HomeFragment extends Fragment {

	private final static String TAG = "HomeFragment";
	protected Context mContext;
	private DBHelper db;
	private List<Map<String, Object>> mDataClass;

	@ViewInject(R.id.listviewclass)
	private ListView listviewIndex;

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
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ViewUtils.inject(this, view);
		this.db = new DBHelper(mContext);
		loadlistviewClass();
		textView.setText(getString(R.string.rd_festival));
		return view;
	}

	@OnClick(R.id.right_img)
	public void toCollection(View view) {
		Intent intent = new Intent();
		intent.setClass(mContext, SearchActivity.class);
		startActivity(intent);
	}

	@OnItemClick(R.id.listviewclass)
	public void onImageItemClick1(AdapterView<?> parent, View view,
			int position, long id) {
		Intent intent = new Intent();
		intent.setClass(mContext, DetailsActivity.class);
		intent.putExtra("song", mDataClass.get(position).get("song").toString());
		intent.putExtra("classname", mDataClass.get(position).get("classname")
				.toString());
		startActivity(intent);
	}

	private void loadlistviewClass() {
		this.mDataClass = getDataClass();
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(mContext,
				this.mDataClass, R.layout.main_listview, new String[] {
						"ItemImage", "classname", "rl", "classs", "ids",
						"ItemImage_right" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle, R.id.ItemRl, R.id.ItemClasss,
						R.id.ItemIds, R.id.ItemImage_right });
		this.listviewIndex.setAdapter(localSimpleAdapter);

	}

	private List<Map<String, Object>> getDataClass() {
		List<Map<String, Object>> localArrayList = new ArrayList<Map<String, Object>>();
		Cursor localCursor = this.db
				.query("select id,classname,grade,song,classs,ids,rl,rlrq,jgdays from v_dx_class where isok=1 and not rlrq is null order by case when CAST (jgdays as INT) >=-1 then 0 else 1 end , jgdays");
		if (null != localCursor) {
			Log.v(TAG, "c.getCount()=" + localCursor.getCount());
			int a = 0;
			while (localCursor.moveToNext()) {
				a++;
				HashMap<String, Object> localHashMap2 = new HashMap<String, Object>();
				if (a == 1) {
					localHashMap2.put("ItemImage",
							Integer.valueOf(R.drawable.img_list_left_new));
				} else {
					localHashMap2.put("ItemImage",
							Integer.valueOf(R.drawable.img_list_left_star));
				}
				localHashMap2.put("classid", localCursor.getString(0));
				localHashMap2.put("classname", localCursor.getString(1));
				if (localCursor.getString(2).equals("3")) {
					localHashMap2
							.put("song", ("," + localCursor.getString(3))
									.replace(",,", ","));
					localHashMap2.put("classs", "");
					localHashMap2.put("ids", localCursor.getString(5) + "条");
					int i = localCursor.getInt(8);
					if (i <= -3) {
						localHashMap2.put("rl", localCursor.getString(7));
					} else {
						String str2 = Common.getjgdaysname(i);
						localHashMap2
								.put("rl", localCursor.getString(7) + str2);
					}
				}
				localHashMap2.put("ItemImage_right",
						Integer.valueOf(R.drawable.img_list_open_item));
				localArrayList.add(localHashMap2);
			}
		}
		return localArrayList;
	}

}
