package com.clong.wuyouhelper.activity;

import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.constants.Constants;
import com.clong.wuyouhelper.utils.MailUtil;

/**
 * 手机防盗_验证密码
 * 
 * @author: cl
 * @date: 2016-2-29-下午1:10:25
 */
public class Safe_CheckPass_Activity extends Activity {

	/**
	 * 当前上下文
	 */
	private static Activity activity;

	/**
	 * 存储的密码
	 */
	private String safe_password;
	/**
	 * 密码编辑框
	 */
	private EditText et_password;
	/**
	 * 显示密码的标记
	 */
	private boolean showPass_flag;

	private SharedPreferences sharedPreferences;

	private static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 弹窗显示邮件是否发送成功
			String title;
			String content;
			if (msg.obj == null) {
				title = "发送成功";
				content = "新的防盗密码已发送至安全邮箱";
			} else {
				title = "发送失败";
				content = msg.obj.toString();
			}
			new Builder(activity).setTitle(title).setMessage(content)
					.setPositiveButton("确定", null).show();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.safe_checkpass_activity);
		activity = this;

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
		Builder builder = new Builder(activity);
		builder.setTitle("重置密码").setMessage("新密码将以邮件形式发送至安全邮箱,确认发送邮件重置密吗?");
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 需要访问网络,所以在子线程中执行
				new Thread() {
					public void run() {
						try {
							// 发送重置密码到安全邮箱
							String uuid = UUID.randomUUID().toString();
							uuid = uuid.substring(0, 6);
							String to = "1011056799@qq.com";
							String subject = "无忧助手-手机防盗重置密码";
							String content = "重置了手机防盗密码,新密码为：" + uuid;
							MailUtil.sendEmail(to, subject, content);

							// 更新密码
							sharedPreferences.edit()
									.putString("safe_password", uuid).commit();
							safe_password = uuid;

							handler.sendEmptyMessage(0);

							// 发送短信
							// MsgUtil.sendMsg("18707175171", "重置了手机防盗密码,新密码为："
							// + uuid);
						} catch (Exception e) {
							Message message = handler.obtainMessage();
							message.obj = e.getMessage();
							handler.sendMessage(message);
						}
					};
				}.start();
			}
		});
		builder.show();
	}

	/**
	 * 检查是否有防盗密码,据此来跳转视图
	 * 
	 * @author: cl
	 * @date: 2016-2-29-下午1:17:09
	 */
	private void checkConfig() {
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		safe_password = sharedPreferences.getString("safe_password", null);
		// 1.未开启手机防盗;2.应用开启后输入过密码
		if (TextUtils.isEmpty(safe_password) || Constants.SAFE_PASS) {
			startActivity(new Intent(this, SafeActivity.class));
			finish();
		}
		// 否则停留验证密码
	}
}
