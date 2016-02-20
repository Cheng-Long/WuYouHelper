package com.clong.wuyouhelper.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.clong.wuyouhelper.R;

/**
 * 设置页
 * 
 * @author: cl
 * @date: 2016-2-20-下午10:33:26
 */
public class SettingsActivity extends Activity {
	private SharedPreferences sharedPreferences;
	private ImageView iv_switch;
	private boolean auto_update;
	private RelativeLayout rl_autoUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);

		antoUpdateListener();
	}

	/**
	 * 自动更新-监听
	 * 
	 * @author: cl
	 * @date: 2016-2-21-上午12:34:17
	 */
	private void antoUpdateListener() {
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		iv_switch = (ImageView) findViewById(R.id.iv_switch);
		auto_update = sharedPreferences.getBoolean("auto_update", true);
		if (auto_update) {
			iv_switch.setImageResource(R.drawable.switch_on_normal);
		} else {
			iv_switch.setImageResource(R.drawable.switch_off_normal);
		}
		rl_autoUpdate = (RelativeLayout) findViewById(R.id.rl_autoUpdate);
		rl_autoUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				auto_update = sharedPreferences.getBoolean("auto_update", true);
				if (auto_update) {
					iv_switch.setImageResource(R.drawable.switch_off_normal);
					sharedPreferences.edit().putBoolean("auto_update", false)
							.commit();
				} else {
					iv_switch.setImageResource(R.drawable.switch_on_normal);
					sharedPreferences.edit().putBoolean("auto_update", true)
							.commit();
				}
			}
		});
	}
}
