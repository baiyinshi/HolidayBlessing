package com.bys.holidayblessing.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.dialog.AbstractChoickDialog;
import com.bys.holidayblessing.tools.DBHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends Activity {

	private DBHelper db;
	private List<Map<String, Object>> mDataList;
	private String classname;
	private String song;
	private String songto_selected = "";
	private String[] arrayOfString;

	// 标题
	@ViewInject(R.id.title_txt)
	private TextView textView;

	// 返回按钮
	@ViewInject(R.id.backImg)
	private ImageView img_back;

	@ViewInject(R.id.listviewList)
	private ListView listviewList;

	@ViewInject(R.id.layoutSong0)
	private LinearLayout layout1;

	@ViewInject(R.id.layoutSong1)
	private LinearLayout layout2;

	@ViewInject(R.id.layoutSong2)
	private LinearLayout layout3;

	@ViewInject(R.id.layoutSong3)
	private LinearLayout layout4;

	@ViewInject(R.id.layoutSong4)
	private LinearLayout layout5;

	@ViewInject(R.id.layoutSong5)
	private LinearLayout layout6;

	@ViewInject(R.id.tvSong1)
	private TextView tvSong1;

	@ViewInject(R.id.tvSong2)
	private TextView tvSong2;

	@ViewInject(R.id.tvSong3)
	private TextView tvSong3;

	@ViewInject(R.id.tvSong4)
	private TextView tvSong4;

	@ViewInject(R.id.tvSong5)
	private TextView tvSong5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ViewUtils.inject(this);
		img_back.setVisibility(View.VISIBLE);
		db = new DBHelper(this);
		song = getIntent().getExtras().getString("song");
		arrayOfString = song.split(",");
		classname = getIntent().getExtras().getString("classname");
		System.out.println(classname);
		textView.setText(classname);
		loadlistviewList("");
		layout1.setBackgroundResource(R.drawable.song_bar_action);
		layout1.setClickable(false);
	}

	// 返回
	@OnClick(R.id.backImg)
	private void doBack(View view) {
		finish();
	}

	// 全部
	@OnClick(R.id.layoutSong0)
	private void setAll(View view) {
		this.songto_selected = "";
		loadlistviewList("");
		layout1.setBackgroundResource(R.drawable.song_bar_action);
		layout1.setClickable(false);
	}

	@OnClick(R.id.layoutSong1)
	private void setLovers(View view) {
		this.songto_selected = tvSong1.getText().toString();
		loadlistviewList("");
		layout2.setBackgroundResource(R.drawable.song_bar_action);
		layout2.setClickable(false);
	}

	@OnClick(R.id.layoutSong2)
	private void setFriends(View view) {
		this.songto_selected = tvSong2.getText().toString();
		loadlistviewList("");
		layout3.setBackgroundResource(R.drawable.song_bar_action);
		layout3.setClickable(false);
	}

	@OnClick(R.id.layoutSong3)
	private void setElder(View view) {
		this.songto_selected = tvSong3.getText().toString();
		loadlistviewList("");
		layout4.setBackgroundResource(R.drawable.song_bar_action);
		layout4.setClickable(false);
	}

	@OnClick(R.id.layoutSong4)
	private void setCustomer(View view) {
		this.songto_selected = tvSong4.getText().toString();
		loadlistviewList("");
		layout5.setBackgroundResource(R.drawable.song_bar_action);
		layout5.setClickable(false);
	}

	@OnClick(R.id.layoutSong5)
	private void setYounger(View view) {
		this.songto_selected = tvSong5.getText().toString();
		loadlistviewList("");
		layout6.setBackgroundResource(R.drawable.song_bar_action);
		layout6.setClickable(false);
	}

	@OnItemClick(R.id.listviewList)
	private void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 弹出对话框
		AbstractChoickDialog dialog = new AbstractChoickDialog(this,
				mDataList.get(position));
		dialog.show();
		// // 弹出对话框
		// SendDialog sendView = new SendDialog();
		// sendView.show(getFragmentManager(), "");
	}

	public void loadlistviewList(String paramString) {
		this.mDataList = getDataList(paramString);
		SimpleAdapter localSimpleAdapter = new SimpleAdapter(this,
				this.mDataList, R.layout.details_listitem, new String[] {
						"ItemImage", "title" }, new int[] { R.id.ItemImage,
						R.id.ItemTitle });
		this.listviewList.setAdapter(localSimpleAdapter);
		if ("".equals(paramString))
			setSong();
	}

	private List<Map<String, Object>> getDataList(String paramString) {
		List<Map<String, Object>> localArrayList = new ArrayList<Map<String, Object>>();
		new HashMap<String, Object>();
		String str1 = "select id,title from dx_list where  ";
		String str5;
		Cursor localCursor = null;
		if ("".equals(paramString)) {
			str5 = str1 + " classname='" + this.classname + "'";
			if ((!"".equals(this.songto_selected))
					&& (this.song.indexOf(this.songto_selected) > -1))
				str5 = str5 + " and song='" + this.songto_selected + "' ";
			String str6 = str5 + " order by id";
			LogUtils.e(str6);
			localCursor = this.db.query(str6);
		}
		if (null != localCursor) {
			while (localCursor.moveToNext()) {
				Map<String, Object> localHashMap3 = new HashMap<String, Object>();
				localHashMap3.put("ItemImage",
						Integer.valueOf(R.drawable.img_list_open_item));
				localHashMap3.put("id", localCursor.getString(0));
				localHashMap3.put("title", localCursor.getString(1));
				localArrayList.add(localHashMap3);
			}
			localCursor.close();
		}
		return localArrayList;
	}

	private void setSong() {
		layout2.setVisibility(View.GONE);
		layout3.setVisibility(View.GONE);
		layout4.setVisibility(View.GONE);
		layout5.setVisibility(View.GONE);
		layout6.setVisibility(View.GONE);
		layout1.setClickable(true);
		layout2.setClickable(true);
		layout3.setClickable(true);
		layout4.setClickable(true);
		layout5.setClickable(true);
		layout6.setClickable(true);
		layout1.setBackgroundResource(R.drawable.song_btn_bg);
		layout2.setBackgroundResource(R.drawable.song_btn_bg);
		layout3.setBackgroundResource(R.drawable.song_btn_bg);
		layout4.setBackgroundResource(R.drawable.song_btn_bg);
		layout5.setBackgroundResource(R.drawable.song_btn_bg);
		layout6.setBackgroundResource(R.drawable.song_btn_bg);
		int i = 0;
		while (true) {
			i++;
			if (i == arrayOfString.length) {
				break;
			}
			String str = arrayOfString[i].trim();
			switch (i) {
			case 1:
				tvSong1.setText(str);
				layout2.setVisibility(View.VISIBLE);
				break;
			case 2:
				tvSong2.setText(str);
				layout3.setVisibility(View.VISIBLE);
				break;
			case 3:
				tvSong3.setText(str);
				layout4.setVisibility(View.VISIBLE);
				break;
			case 4:
				tvSong4.setText(str);
				layout5.setVisibility(View.VISIBLE);
				break;
			case 5:
				tvSong5.setText(str);
				layout6.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}

	}

}
