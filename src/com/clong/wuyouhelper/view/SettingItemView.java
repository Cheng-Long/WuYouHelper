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

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.clong.wuyouhelper";
	private TextView tv_desc;
	private ImageView iv_switch;
	private int desc;
	private int switch_on;
	private int switch_off;

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

		// 获取自定义属性值
		desc = attrs.getAttributeResourceValue(NAMESPACE, "desc", 0);
		switch_on = attrs.getAttributeResourceValue(NAMESPACE, "switch_on", 0);
		switch_off = attrs
				.getAttributeResourceValue(NAMESPACE, "switch_off", 0);
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

		setDesc(desc);
		setSwitch(true);
	}

	/**
	 * 左侧项说明设置
	 * 
	 * @author: cl
	 * @param resid
	 *            资源ID
	 * @date: 2016-2-22-下午9:26:00
	 */
	public void setDesc(int resid) {
		tv_desc.setText(resid);
	}

	/**
	 * 右侧开关设置
	 * 
	 * @author: cl
	 * @param flag
	 *            ture-开,false-关
	 * @date: 2016-2-22-下午9:31:00
	 */
	public void setSwitch(boolean flag) {
		iv_switch.setImageResource(flag ? switch_on : switch_off);
	}
}
