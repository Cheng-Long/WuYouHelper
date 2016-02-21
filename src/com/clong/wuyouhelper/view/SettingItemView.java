package com.clong.wuyouhelper.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clong.wuyouhelper.R;

/**
 * 自定义设置项View
 * 
 * @author: cl
 * @date: 2016-2-21-下午5:01:15
 */
public class SettingItemView extends RelativeLayout {

	private TextView tv_desc;
	private ImageView iv_switch;

	@SuppressLint("NewApi")
	public SettingItemView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView();
	}

	public SettingItemView(Context context) {
		super(context);

		initView();
	}

	/**
	 * 初始化
	 * 
	 * @author: cl
	 * @date: 2016-2-21-下午4:47:08
	 */
	private void initView() {
		View.inflate(getContext(), R.layout.settings_item_view, this);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		iv_switch = (ImageView) findViewById(R.id.iv_switch);
	}

	public void setText(int resid) {
		tv_desc.setText(resid);
	}

	public void setImageResource(int resId) {
		iv_switch.setImageResource(resId);
	}
}
