package com.bys.holidayblessing.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.dialog.AbstractChoickDialog;
import com.bys.holidayblessing.tools.DBHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏
 * 
 * @author Bys
 *
 */
public class CategoryFragment extends Fragment {

	private Context mContext;
	private DBHelper db;
	private View view;
	private List<Map<String, Object>> localArrayList;

	@ViewInject(R.id.title_txt)
	private TextView TextView;

	@ViewInject(R.id.tvTj)
	private TextView tv1;

	@ViewInject(R.id.lv_bookmark)
	private ListView lv_bookmark;
	private List<Map<String, Object>> mData;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_category, container, false);
		ViewUtils.inject(this, view);
		TextView.setText(getString(R.string.tab_favorite));
		db = new DBHelper(mContext);
		loadbookmark();
		return view;
	}

	@OnItemClick(R.id.lv_bookmark)
	private void onItemClick(AdapterView<?> parent, View v, int position,
			long id) {
		// 弹出对话框
		// SendDialog sendView = new SendDialog();
		// sendView.show(getFragmentManager(), null);
		AbstractChoickDialog dialog = new AbstractChoickDialog(mContext,
				localArrayList.get(position));
		dialog.show();
	}

	private List<Map<String, Object>> getData() {
		localArrayList = new ArrayList<Map<String, Object>>();
		Cursor localCursor = this.db
				.query("select id,title,sctime from v_dx_list_sc ");
		if (localCursor.getCount() == 0) {
			HashMap<String, Object> localHashMap1 = new HashMap<String, Object>();
			localHashMap1.put("ItemImage",
					Integer.valueOf(R.drawable.img_list_left_new));
			localHashMap1.put("id", "");
			localHashMap1.put("title", "暂无收藏！");
			localHashMap1.put("sctime", "");
			localArrayList.add(localHashMap1);
		}
		while (true) {
			if (!localCursor.moveToNext()) {
				tv1.setText("收藏夹中共" + localCursor.getCount() + "项!");
				return localArrayList;
			}
			HashMap<String, Object> localHashMap2 = new HashMap<String, Object>();
			localHashMap2.put("ItemImage",
					Integer.valueOf(R.drawable.img_list_open_item));
			localHashMap2.put("id", localCursor.getString(0));
			localHashMap2.put("title", localCursor.getString(1));
			localHashMap2.put("sctime", localCursor.getString(2));
			localArrayList.add(localHashMap2);
		}
	}

	/**
	 * 删除指定收藏
	 * 
	 * @param id
	 * @param paramString2
	 */
	public void doDelBookmark(final String id, String paramString2) {
		// new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
		// .setTitleText("提示")
		// .setContentText("确定要删除这个收藏吗？\n\n[" + paramString2 + "]")
		// .setCancelText("取消")
		// .setConfirmText("确定")
		// .showCancelButton(true)
		// .setConfirmClickListener(
		// new SweetAlertDialog.OnSweetClickListener() {
		// @Override
		// public void onClick(SweetAlertDialog sDialog) {
		// String str = "update dx_list set sc=null,sctime=null where id="
		// + id;
		// db.execsql(str);
		// Toast.makeText(mContext, "已成功删除该收藏！", 0).show();
		// loadbookmark();
		// }
		// })
		// .setCancelClickListener(
		// new SweetAlertDialog.OnSweetClickListener() {
		// @Override
		// public void onClick(SweetAlertDialog sDialog) {
		//
		// }
		// }).show();
		//
	}

	public void loadbookmark() {
		this.mData = getData();
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(mContext,
				this.mData, R.layout.category_listitem, new String[] {
						"ItemImage", "title", "sctime" }, new int[] {
						R.id.ItemImage, R.id.ItemTitle, R.id.ItemTime });
		this.lv_bookmark.setAdapter(localSimpleAdapter);
	}

}
