package com.clong.wuyouhelper.activity;

import com.clong.wuyouhelper.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * 手机防盗页
 * 
 * @author: cl
 * @date: 2016-2-23-下午5:05:30
 */
public class SafeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.safe_activity);
	}

	/**
	 * 后退按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-23-下午5:06:11
	 */
	public void back(View v) {
		finish();
	}
}
