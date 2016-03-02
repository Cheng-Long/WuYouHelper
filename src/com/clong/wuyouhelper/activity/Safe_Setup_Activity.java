package com.clong.wuyouhelper.activity;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.constants.Constants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 手机防盗-设置向导
 * 
 * @author: cl
 * @date: 2016-3-2-下午12:59:50
 */
public class Safe_Setup_Activity extends Activity {

	/**
	 * 当前显示的布局
	 */
	private int currentView;

	private EditText et_pass;
	private EditText et_pass_sure;
	private String pass;
	private String pass_sure;
	private EditText et_phone;
	private EditText et_mail;
	private String phone;
	private String mail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.safe_setup_1);
		currentView = R.layout.safe_setup_1;
	}

	/**
	 * 后退按钮,根据当前页面的不同变更视图
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-23-下午5:06:11
	 */
	public void back(View v) {
		// 切换视图后设置参数需要重新获取一遍控件,因为此时旧的视图已被覆盖
		if (currentView == R.layout.safe_setup_1) {// 第一步的后退
			finish();
		} else if (currentView == R.layout.safe_setup_2) {// 第二部的后退
			// 保存第二步的参数
			phone = et_phone.getText().toString();
			mail = et_mail.getText().toString();

			setContentView(R.layout.safe_setup_1);
			currentView = R.layout.safe_setup_1;

			// 显示第一步的参数
			et_pass = (EditText) findViewById(R.id.et_pass);
			et_pass_sure = (EditText) findViewById(R.id.et_pass_sure);
			et_pass.setText(pass);
			et_pass_sure.setText(pass_sure);
		} else if (currentView == R.layout.safe_setup_3) {// 第三部的后退
			setContentView(R.layout.safe_setup_2);
			currentView = R.layout.safe_setup_2;

			// 显示第二步的参数
			et_phone = (EditText) findViewById(R.id.et_phone);
			et_mail = (EditText) findViewById(R.id.et_mail);
			et_phone.setText(phone);
			et_mail.setText(mail);
		}
	}

	/**
	 * 第一个 下一步
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-3-2-下午4:33:50
	 */
	public void setupFirst(View v) {
		// 切换视图前获取第一步的控件,并保存参数
		et_pass = (EditText) findViewById(R.id.et_pass);
		et_pass_sure = (EditText) findViewById(R.id.et_pass_sure);
		pass = et_pass.getText().toString();
		pass_sure = et_pass_sure.getText().toString();

		// 切换视图
		setContentView(R.layout.safe_setup_2);
		currentView = R.layout.safe_setup_2;

		// 切换视图后获取第二步的控件,并设置参数
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_mail = (EditText) findViewById(R.id.et_mail);
		et_phone.setText(phone);
		et_mail.setText(mail);
	}

	/**
	 * 第二个 下一步
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-3-2-下午4:33:50
	 */
	public void setupSecond(View v) {
		// 保存第二步的参数
		phone = et_phone.getText().toString();
		mail = et_mail.getText().toString();

		setContentView(R.layout.safe_setup_3);
		currentView = R.layout.safe_setup_3;
	}

	/**
	 * 设置向导 完成
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-3-2-下午4:33:50
	 */
	public void setupFinish(View v) {
		getSharedPreferences("config", MODE_PRIVATE).edit()
				.putString("safe_password", "123").commit();
		Constants.SAFE_PASS = true;
		finish();
	}
}
