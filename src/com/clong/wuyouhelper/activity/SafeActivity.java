package com.clong.wuyouhelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.constants.Constants;

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
		checkConfig();
	}

	/**
	 * 后退按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-23-下午5:06:11
	 */
	public void back(View v) {
		getSharedPreferences("config", MODE_PRIVATE).edit()
				.putString("safe_password", "123").commit();
		Constants.SAFE_PASS = true;
		finish();
	}

	/**
	 * 检查是否有防盗密码,据此来显示视图
	 * 
	 * @author: cl
	 * @date: 2016-2-29-上午11:05:44
	 */
	private void checkConfig() {
		// 手机防盗密码已输入-表示已开启防盗
		if (Constants.SAFE_PASS) {
			findViewById(R.id.tv_verticalLine).setVisibility(View.VISIBLE);
			findViewById(R.id.bt_restart).setVisibility(View.VISIBLE);

			((ImageView) findViewById(R.id.iv_state))
					.setImageResource(R.drawable.home_safe);
			((TextView) findViewById(R.id.tv_stateDesc))
					.setText(R.string.safe_remind_on);
			((TextView) findViewById(R.id.bt_startSafe))
					.setText(R.string.safe_excute);
		} else {
			// 未输入-表示未开启防盗
		}
	}
}
