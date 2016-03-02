package com.clong.wuyouhelper.activity;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.constants.Constants;
import com.clong.wuyouhelper.utils.StringUtil;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
	 * 手机按键上的后退
	 */
	@Override
	public void onBackPressed() {
		backAction();
	}

	/**
	 * 布局上的后退按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-23-下午5:06:11
	 */
	public void back(View v) {
		backAction();
	}

	/**
	 * 后退动作,根据当前页面的不同变更视图
	 * 
	 * @author: cl
	 * @date: 2016-3-2-下午9:34:09
	 */
	private void backAction() {
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

		// 校验密码
		if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass_sure)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		} else if (!pass.equals(pass_sure)) {
			Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}

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

		if (!StringUtil.isPhoneNO(phone)) {
			Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
			return;
		} else if (!StringUtil.isMail(mail)) {
			Toast.makeText(this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
			return;
		}

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
		Editor edit = getSharedPreferences("config", MODE_PRIVATE).edit();
		edit.putString("safe_password", StringUtil.toMD5(pass + phone + mail))
				.putString("safe_phone", phone).putString("safe_mail", mail)
				.commit();
		Constants.SAFE_PASS = true;
		finish();
	}
}
