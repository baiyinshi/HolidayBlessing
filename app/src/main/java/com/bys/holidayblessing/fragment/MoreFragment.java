package com.bys.holidayblessing.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bys.holidayblessing.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

/**
 * 更多
 * 
 * @author Bys
 *
 */
public class MoreFragment extends Fragment {

	@ViewInject(R.id.title_txt)
	private TextView textView;

	@ViewInject(R.id.layout_sign)
	private RelativeLayout layout_sign;

	// @ViewInject(R.id.layout_sign_on)
	// private RelativeLayout layout_sign_on;

	@ViewInject(R.id.layout_opinion)
	private RelativeLayout layout_opinion;

	@ViewInject(R.id.layout_version)
	private RelativeLayout layout_version;

	@ViewInject(R.id.layout_about_us)
	private RelativeLayout layout_about_us;

	@ViewInject(R.id.layout_share)
	private RelativeLayout layout_share;

	@ViewInject(R.id.checkbox)
	private CheckBox checkbox;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_more, container, false);
		ViewUtils.inject(this, view);
		textView.setText(getString(R.string.more_title));
		return view;
	}

	// 设置签名
	@OnClick(R.id.layout_sign)
	private void setSign(View view) {

	}

	// 开启签名
	@OnCompoundButtonCheckedChange(R.id.checkBox)
	private void openSign(CompoundButton buttonView, boolean isChecked) {
		LogUtils.i("checkBox");
	}

	// 意见反馈
	@OnClick(R.id.layout_opinion)
	private void toFeedBack(View view) {
	}

	// 版本更新
	@OnClick(R.id.layout_version)
	private void versionUpdate(View view) {
	}

	// 应用分享
	@OnClick(R.id.layout_share)
	private void toShare(View view) {

	}

	// 关于我们
	@OnClick(R.id.layout_about_us)
	private void toAboutUs(View view) {
	}

}
