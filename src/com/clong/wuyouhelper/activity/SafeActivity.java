package com.clong.wuyouhelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	 * 生命周期方法,onStop()到onStart()时执行,即不可见到可见时
	 */
	@Override
	protected void onRestart() {
		super.onRestart();

		// 设置向导完成后返回时调用
		checkConfig();
	}

	/**
	 * 已开启-发送远程指令按钮;未开启-开启防盗保护按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-3-2-上午10:48:12
	 */
	public void start(View v) {
		// 根据是否输入过防盗密码来判断是否开启防盗
		if (Constants.SAFE_PASS) {
			Toast.makeText(this, "发送指令", Toast.LENGTH_SHORT).show();
		} else {
			startActivity(new Intent(this, Safe_Setup_Activity.class));
		}
	}

	/**
	 * 进入设置向导按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-3-2-上午10:49:32
	 */
	public void restart(View v) {
		startActivity(new Intent(this, Safe_Setup_Activity.class));
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

	/**
	 * 检查是否有防盗密码,据此来显示视图
	 * 
	 * @author: cl
	 * @date: 2016-2-29-上午11:05:44
	 */
	private void checkConfig() {
		// 手机防盗密码已输入-表示已开启防盗,变更布局
		if (Constants.SAFE_PASS) {
			findViewById(R.id.tv_verticalLine).setVisibility(View.VISIBLE);
			findViewById(R.id.bt_restart).setVisibility(View.VISIBLE);

			((ImageView) findViewById(R.id.iv_state))
					.setImageResource(R.drawable.home_safe);
			((TextView) findViewById(R.id.tv_stateDesc))
					.setText(R.string.safe_remind_on);
			((TextView) findViewById(R.id.bt_startSafe))
					.setText(R.string.safe_excute);
		}
		// 未输入-表示未开启防盗,显示默认布局
	}
}
