package com.bys.holidayblessing.dialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.activity.ContactListActivity;
import com.bys.holidayblessing.tools.Common;
import com.bys.holidayblessing.tools.DBHelper;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Map;

public class AbstractChoickDialog extends Dialog implements
		View.OnClickListener {

	private DBHelper db;
	private Map<String, Object> songmap;
	private Context mContext;
	private View mRootView;

	private Button btn_send;
	private Button btn_send2;
	private Button btn_email;
	private Button btn_copy;
	private Button btn_share;
	private Button btn_category;
	private Button btn_cancel;

	public AbstractChoickDialog(Context context, Map<String, Object> map) {
		super(context);
		mContext = context;
		songmap = map;
		initView(mContext);
	}

	protected void initView(Context context) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pop_menu);
		mRootView = findViewById(R.id.rootView);
		db = new DBHelper(mContext);
		mRootView.setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		Window dialogWindow = getWindow();
		ColorDrawable dw = new ColorDrawable(0x0000ff00);
		dialogWindow.setBackgroundDrawable(dw);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
		btn_send2 = (Button) findViewById(R.id.btn_send2);
		btn_send2.setOnClickListener(this);
		btn_email = (Button) findViewById(R.id.btn_email);
		btn_email.setOnClickListener(this);
		btn_copy = (Button) findViewById(R.id.btn_copy);
		btn_copy.setOnClickListener(this);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);
		btn_category = (Button) findViewById(R.id.btn_category);
		btn_category.setOnClickListener(this);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
	}

	// 发送短信
	@OnClick(R.id.btn_send)
	private void sendMMS(View v) {
		LogUtils.e("发送短信");
		Intent localIntent = new Intent("android.intent.action.SENDTO",
				Uri.parse("smsto:"));
		String str = songmap.get("title").toString();
		if (Common.readconfig(mContext, "ischeck").equals("yes"))
			str = str + Common.readconfig(mContext, "mySign");
		localIntent.putExtra("sms_body", str);
		mContext.startActivity(localIntent);
		dismiss();
	}

	// 群发短信
	@OnClick(R.id.btn_send2)
	private void sendAllMMS(View v) {
		Intent intent = new Intent();
		intent.setClass(mContext, ContactListActivity.class);
		Bundle bData = new Bundle();
		bData.putString("content", songmap.get("title").toString());
		intent.putExtras(bData);
		mContext.startActivity(intent);
		dismiss();
	}

	// 发送邮件
	@OnClick(R.id.btn_email)
	private void sendEmail(View v) {
		Intent localIntent = new Intent("android.intent.action.SENDTO",
				Uri.parse("mailto:"));
		String str = songmap.get("title").toString();
		if (Common.readconfig(mContext, "ischeck").equals("yes"))
			str = str + Common.readconfig(mContext, "mySign");
		localIntent.putExtra("android.intent.extra.TEXT", str);
		mContext.startActivity(localIntent);
		dismiss();
	}

	// 复制
	@OnClick(R.id.btn_copy)
	private void doCopy(View v) {
		ClipboardManager localClipboardManager = (ClipboardManager) mContext
				.getSystemService("clipboard");
		String str1 = songmap.get("title").toString();
		localClipboardManager.setText(str1);
		String str2 = localClipboardManager.getText().toString();
		if (str2.equals(str1)) {
			Toast.makeText(mContext, "短信内容已经成功复制到剪贴板中！", 0).show();
		} else {
			Toast.makeText(mContext, "复制短信内容失败！", 0).show();
		}
		dismiss();
	}

	// 分享
	@OnClick(R.id.btn_share)
	private void doShare(View v) {
		Intent localIntent = new Intent("android.intent.action.SEND");
		localIntent.setType("text/plain");
		localIntent.putExtra("android.intent.extra.SUBJECT", "分享");
		localIntent.putExtra("android.intent.extra.TEXT", songmap.get("title")
				.toString());
		mContext.startActivity(Intent.createChooser(localIntent, "分享"));
		dismiss();
	}

	// 收藏
	@OnClick(R.id.btn_category)
	private void doCategory(View v) {
		String str = "update dx_list set sc=1,sctime='"
				+ Common.getDate("yyyy-MM-dd HH:mm") + "' where  id="
				+ songmap.get("id");
		this.db.execsql(str);
		Toast.makeText(mContext, "已成功加入收藏！", 0).show();
		dismiss();
	}

	// 取消
	@OnClick(R.id.btn_cancel)
	private void doCancel(View v) {
		dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			sendMMS(v);
			break;
		case R.id.btn_send2:
			sendAllMMS(v);
			break;
		case R.id.btn_email:
			sendEmail(v);
			break;
		case R.id.btn_copy:
			doCopy(v);
			break;
		case R.id.btn_share:
			doShare(v);
			break;
		case R.id.btn_category:
			doCategory(v);
			break;
		case R.id.btn_cancel:
			doCancel(v);
			break;
		default:
			break;
		}
	}
}
