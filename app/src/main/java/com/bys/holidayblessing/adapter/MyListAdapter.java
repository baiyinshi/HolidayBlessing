package com.bys.holidayblessing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

public class MyListAdapter extends BaseAdapter {

	Context mContext;
	LayoutInflater inflater;
	List<Map<String, Object>> list;

	// 构造函数
	public MyListAdapter(Context context, List<Map<String, Object>> arraylist) {
		this.mContext = context;
		this.list = arraylist;
		this.inflater = LayoutInflater.from(mContext);
	}

	// @Override
	// // 将信息绑定到控件的方法
	// public void bindView(View view, Context context, Cursor cursor) {
	// ((TextView) view)
	// .setText(cursor.getString(cursor
	// .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));
	//
	// }
	//
	// @Override
	// public CharSequence convertToString(Cursor cursor) {
	// return cursor.getString(cursor
	// .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
	// }
	//
	// @Override
	// // 创建自动绑定选项
	// public View newView(Context context, Cursor cursor, ViewGroup parent) {
	// HoldeView holdeView = null;
	// if (holdeView == null) {
	// holdeView = new HoldeView();
	// View view = inflater.inflate(R.layout.contactlist_item, parent,
	// false);
	// ViewUtils.inject(holdeView, parent);
	//
	// }
	//
	// final TextView tv = (TextView) inflater.tv
	// .setText(cursor.getString(cursor
	// .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));
	// return tv;
	// }

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HoldeView holdeView = null;
		if (null == arg1) {
			holdeView = new HoldeView();
			arg1 = inflater.inflate(R.layout.contactlist_item, null);
			ViewUtils.inject(holdeView, arg1);
			arg1.setTag(holdeView);
		} else {
			holdeView = (HoldeView) arg1.getTag();
		}
		if (Integer.parseInt(list.get(arg0).get("ischeck").toString()) == 1) {
			holdeView.check.setChecked(true);
		} else {
			holdeView.check.setChecked(false);
		}
		holdeView.mname.setText(list.get(arg0).get("name").toString());
		holdeView.mnumber.setText(list.get(arg0).get("number").toString());
		return arg1;
	}

	class HoldeView {
		@ViewInject(R.id.check)
		CheckBox check;
		@ViewInject(R.id.mname)
		TextView mname;
		@ViewInject(R.id.msisdn)
		TextView mnumber;

	}
}
