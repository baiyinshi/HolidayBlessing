package com.bys.holidayblessing.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.bys.holidayblessing.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 搜索界面
 * 
 * @author Bys
 *
 */
public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		openBoard();
	}

	
	// 显示键盘
		private void openBoard() {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}, 300);
		}

	
}
