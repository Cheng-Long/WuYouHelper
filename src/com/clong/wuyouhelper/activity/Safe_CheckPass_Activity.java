package com.clong.wuyouhelper.activity;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.constants.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 手机防盗_验证密码
 * 
 * @author: cl
 * @date: 2016-2-29-下午1:10:25
 */
public class Safe_CheckPass_Activity extends Activity {

	/**
	 * 存储的密码
	 */
	private String safe_password;
	/**
	 * 编辑框输入的密码
	 */
	private EditText et_password;
	/**
	 * 显示密码的标记
	 */
	private boolean showPass_flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.safe_checkpass_activity);
		et_password = (EditText) findViewById(R.id.et_password);
		checkConfig();
	}

	/**
	 * 后退按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-29-下午1:38:30
	 */
	public void back(View v) {
		finish();
	}

	/**
	 * 显示密码
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-29-下午1:10:50
	 */
	public void showPass(View v) {
		showPass_flag = !showPass_flag;
		// 要显示的图片ID
		int resid = 0;

		if (showPass_flag) {
			// 显示密码
			resid = R.drawable.btn_check_on;
			et_password
					.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		} else {
			// 还原密码
			resid = R.drawable.btn_check_off;
			et_password.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		// 输入光标一直在输入文本后面
		Selection.setSelection(et_password.getText(), et_password.getText()
				.toString().length());

		// 变更TextView的drawableLeft图片
		@SuppressWarnings("deprecation")
		Drawable leftDrawable = getResources().getDrawable(resid);
		leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
				leftDrawable.getMinimumHeight());
		((TextView) v).setCompoundDrawables(leftDrawable, null, null, null);
	}

	/**
	 * 确定提交
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-29-下午1:10:57
	 */
	public void submit(View v) {
		if (et_password.getText().toString().equals(safe_password)) {
			startActivity(new Intent(this, SafeActivity.class));
			Constants.SAFE_PASS = true;
			finish();
		} else {
			et_password.setText("");
			et_password.setHint(R.string.safe_retry);
		}
	}

	/**
	 * 重置密码
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-29-下午1:11:04
	 */
	public void reset(View v) {
		Toast.makeText(this, "重置密码", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 检查是否有防盗密码,据此来跳转视图
	 * 
	 * @author: cl
	 * @date: 2016-2-29-下午1:17:09
	 */
	private void checkConfig() {
		SharedPreferences sharedPreferences = getSharedPreferences("config",
				MODE_PRIVATE);
		safe_password = sharedPreferences.getString("safe_password", null);
		// 1.未开启手机防盗;2.应用开启后输入过密码
		if (TextUtils.isEmpty(safe_password) || Constants.SAFE_PASS) {
			startActivity(new Intent(this, SafeActivity.class));
			finish();
		}
		// 否则停留验证密码
	}
}
