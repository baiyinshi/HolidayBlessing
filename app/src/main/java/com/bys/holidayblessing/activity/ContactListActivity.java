package com.bys.holidayblessing.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.adapter.MyListAdapter;
import com.bys.holidayblessing.tools.Common;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人列表
 * 
 * @author Bys
 *
 */
public class ContactListActivity extends Activity {

	private String map_content;
	private Cursor cursor;
	private List<Map<String, Object>> arraylist = new ArrayList<Map<String, Object>>();
	MyListAdapter myAdapter = null;

	/** 获取库Phon表字段 **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID };

	/** 联系人显示名称 **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** 电话号码 **/
	private static final int PHONES_NUMBER_INDEX = 1;

	// 标题
	@ViewInject(R.id.title_txt)
	private TextView textView;

	// 返回按钮
	@ViewInject(R.id.backImg)
	private ImageView img_back;

	@ViewInject(R.id.empty)
	private TextView tv_point;

	@ViewInject(R.id.contactlist)
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactlist);
		ViewUtils.inject(this);
		textView.setText("联系人");
		img_back.setVisibility(View.VISIBLE);
		Bundle bundle = getIntent().getExtras();
		map_content = bundle.getString("content");
		/** 得到手机通讯录联系人信息 **/
		getPhoneContacts();
		myAdapter = new MyListAdapter(this, arraylist);
		mListView.setAdapter(myAdapter);
	}

	@OnClick(R.id.Button01)
	private void chooseAll(View v) {
		LogUtils.i("选择全部");
		for (int i = 0; i < arraylist.size(); i++) {
			Map<String, Object> map = arraylist.get(i);
			map.put("ischeck", 1);
		}
		myAdapter.notifyDataSetChanged();
	}

	@OnClick(R.id.Button02)
	private void cancelAll(View v) {
		LogUtils.i("取消全部");
		for (int i = 0; i < arraylist.size(); i++) {
			Map<String, Object> map = arraylist.get(i);
			map.put("ischeck", 0);
		}
		myAdapter.notifyDataSetChanged();
	}

	@OnClick(R.id.btn_add)
	private void doAdd(View v) {
		LogUtils.e("添加群发短信——>" + map_content);
		String mobile = null;
		for (int i = 0; i < arraylist.size(); i++) {
			Map<String, Object> map = arraylist.get(i);
			if (Integer.parseInt(map.get("ischeck").toString()) == 1) {
				if (null == mobile) {
					mobile = map.get("number").toString();
				} else {
					mobile = mobile + ";" + map.get("number").toString();
				}
			}
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.putExtra("address", mobile);
		if (Common.readconfig(this, "ischeck").equals("yes")) {
			map_content = map_content + Common.readconfig(this, "mySign");
		}
		intent.putExtra("sms_body", map_content);
		intent.setType("vnd.android-dir/mms-sms");
		startActivity(intent);
		finish();
	}

	@OnClick(R.id.backImg)
	private void doBack(View v) {
		LogUtils.e("返回");
		finish();
	}

	@OnItemClick(R.id.contactlist)
	private void autoCompleteClick(AdapterView<?> parent, View view,
			int position, long id) {
		LogUtils.e("Lisview监听器");
		Map<String, Object> map = arraylist.get(position);
		map.put("ischeck", 1);
		myAdapter.notifyDataSetChanged();
	}

	/** 得到手机通讯录联系人信息 **/
	private void getPhoneContacts() {
		// 查询已存在的联系人信息 并将信息绑定到autocompleteTextView中
		ContentResolver cr = getContentResolver();
		// 获得查询的信息
		cursor = cr.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null,
				null);
		if (null != cursor) {
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 得到手机号码
				String phoneNumber = cursor.getString(PHONES_NUMBER_INDEX);
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = cursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				map.put("ischeck", 0);
				map.put("name", contactName);
				map.put("number", phoneNumber);
				arraylist.add(map);
			}
			cursor.close();
		} else {
			tv_point.setVisibility(View.VISIBLE);
		}
	}
}
