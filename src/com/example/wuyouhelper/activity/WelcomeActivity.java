package com.example.wuyouhelper.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.wuyouhelper.General;
import com.example.wuyouhelper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		startTimer();
	}

	/**
	 * 开始计时器,等待一段时间再跳转
	 * @author: cl
	 * @date: 2016-1-27-下午6:38:32
	 */
	private void startTimer() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
				startActivity(intent);
			}
		}, General.WAIT_TIME);
	}

}
