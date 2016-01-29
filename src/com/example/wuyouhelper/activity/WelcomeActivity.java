package com.example.wuyouhelper.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.wuyouhelper.R;
import com.example.wuyouhelper.general.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * ��ӭҳ
 * 
 * @author: cl
 * @date: 2016-1-28-����3:13:44
 */
public class WelcomeActivity extends Activity {
	/**
	 * �ȴ�ʱ��,1s
	 */
	public static long WAIT_TIME = 1 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		getVersion();
		startTimer();
	}

	/**
	 * ��ȡ�汾��Ϣ
	 * 
	 * @author: cl
	 * @date: 2016-1-28-����4:08:17
	 */
	private void getVersion() {
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			Constants.VERSION_NAME = packageInfo.versionName;
			Constants.VERSION_CODE = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ʼ��ʱ��,�ȴ�һ��ʱ������ת
	 * 
	 * @author: cl
	 * @date: 2016-1-27-����6:38:32
	 */
	private void startTimer() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						HomeActivity.class);
				startActivity(intent);
				finish();
			}
		}, WelcomeActivity.WAIT_TIME);
	}

}
