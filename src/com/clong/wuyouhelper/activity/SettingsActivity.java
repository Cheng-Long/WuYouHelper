package com.clong.wuyouhelper.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.view.SettingItemView;

/**
 * 设置页
 * 
 * @author: cl
 * @date: 2016-2-20-下午10:33:26
 */
public class SettingsActivity extends Activity {
	private SharedPreferences sharedPreferences;
	private boolean auto_update;
	private SettingItemView siv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		antoUpdateItem();
	}

	/**
	 * 后退按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-21-下午4:57:28
	 */
	public void back(View v) {
		finish();
	}

	/**
	 * 自动更新项
	 * 
	 * @author: cl
	 * @date: 2016-2-21-上午12:34:17
	 */
	private void antoUpdateItem() {
		siv = (SettingItemView) findViewById(R.id.siv_autoUpdate);

		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		auto_update = sharedPreferences.getBoolean("auto_update", true);
		siv.setSwitch(auto_update);
		siv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				auto_update = sharedPreferences.getBoolean("auto_update", true);
				if (auto_update) {
					siv.setSwitch(false);
					sharedPreferences.edit().putBoolean("auto_update", false)
							.commit();
				} else {
					siv.setSwitch(true);
					sharedPreferences.edit().putBoolean("auto_update", true)
							.commit();
				}
			}
		});
	}
}
