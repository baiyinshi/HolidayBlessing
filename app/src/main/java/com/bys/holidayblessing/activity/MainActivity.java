package com.bys.holidayblessing.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.fragment.CategoryFragment;
import com.bys.holidayblessing.fragment.CollectionFragment;
import com.bys.holidayblessing.fragment.HomeFragment;
import com.bys.holidayblessing.update.UpdateManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	@ViewInject(R.id.tabhost)
	private FragmentTabHost mTabHost;

	private Class<?> fragmentArray[] = { HomeFragment.class,
			CollectionFragment.class, CategoryFragment.class };
	private int iconArray[] = { R.drawable.tabbar_recommend_top,
			R.drawable.tabbar_category_top, R.drawable.tabbar_favorite_top };
	private String titleArray[] = { "节日", "分类", "收藏" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.customTagPrefix = "HolidayBlessing"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = true; // LogUtils.i(...) 的 adb log 输出
		ViewUtils.inject(this);
		setupTabView();
		// 初始化接口，应用启动的时候调用
		// 参数：appId, appSecret, 调试模式
		AdManager.getInstance(this).init("8d05e65df17c5060",
				"844b79062b475c2e", false);
		// 检查配置，SDK运行失败时可以用来检查配置是否齐全
		SpotManager.getInstance(this).checkPermission(this);
		showApp();
	}

	private void setupTabView() {
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setDividerDrawable(null);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(titleArray[i])
					.setIndicator(getTabItemView(i));
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.tab_item_press);
		}
	}

	private View getTabItemView(int index) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.tab_bottom_nav, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
		imageView.setImageResource(iconArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.tv_icon);
		textView.setText(titleArray[index]);
		return view;
	}

	// 广告条接口调用（适用于应用）
	public void showApp() {
		// 将广告条adView添加到需要展示的layout控件中
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.adslayout);
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		adLayout.addView(adView);
		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {

			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");

			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
	}

	// 广告条接口调用（适用于游戏）
	public void showGame() {
		// 实例化LayoutParams(重要)
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; //
		// 这里示例为右下角
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 调用Activity的addContentView函数
		this.addContentView(adView, layoutParams);
		// 监听广告条接口
		adView.setAdListener(new AdViewListener() {
			@Override
			public void onSwitchedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "广告条切换");
			}

			@Override
			public void onReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告成功");
			}

			@Override
			public void onFailedToReceivedAd(AdView arg0) {
				Log.i("YoumiAdDemo", "请求广告失败");
			}
		});
	}

	// 展示插播广告，可以不调用loadSpot独立使用
	public void showAds() {
		// 插播接口调用
		// 开发者可以到开发者后台设置展示频率，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）
		// 自4.03版本增加云控制是否开启防误点功能，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）

		// 加载插播资源
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setShowInterval(20);// 设置20秒的显示时间间隔
		SpotManager.getInstance(this).setSpotOrientation(
				SpotManager.ORIENTATION_PORTRAIT);
		SpotManager.getInstance(MainActivity.this).showSpotAds(
				MainActivity.this, new SpotDialogListener() {
					@Override
					public void onShowSuccess() {
						Log.i("YoumiAdDemo", "展示成功");
					}

					@Override
					public void onShowFailed() {
						Log.i("YoumiAdDemo", "展示失败");
					}

					@Override
					public void onSpotClosed() {
						Log.e("YoumiAdDemo", "插屏关闭");
					}

				});
		// 可以根据需要设置Theme，如下调用，如果无特殊需求，直接调用上方的接口即可
		// SpotManager.getInstance(YoumiAdDemo.this).showSpotAds(YoumiAdDemo.this,
		// android.R.style.Theme_Translucent_NoTitleBar);
		// //
	}

	public void checkupdate() {
		UpdateManager localUpdateManager = new UpdateManager(this,
				getString(R.string.versionfilename));
		try {
			localUpdateManager.checkUpdate();
			return;
		} catch (Resources.NotFoundException localNotFoundException) {
			localNotFoundException.printStackTrace();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

}
