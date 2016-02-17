package com.example.wuyouhelper.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuyouhelper.R;
import com.example.wuyouhelper.general.Utils;

/**
 * 主页-菜单
 * 
 * @author: cl
 * @date: 2016-1-28-下午3:14:01
 */
public class HomeActivity extends Activity {

	/**
	 * 主页菜单图片ID
	 */
	public static int[] IV_HOME_ITEM = { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools };

	/**
	 * 主页菜单说明ID
	 */
	public static int[] TV_HOME_ITEM = { R.string.home_safe,
			R.string.home_callmsgsafe, R.string.home_apps,
			R.string.home_taskmanager, R.string.home_netmanager,
			R.string.home_trojan, R.string.home_sysoptimize,
			R.string.home_tools };

	private ImageButton ib_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Utils.checkForUpdate(this);
		fillHome();
		settingListener();
	}

	/**
	 * 设置按钮-监听
	 * 
	 * @author: cl
	 * @date: 2016-2-17-下午1:36:14
	 */
	private void settingListener() {
		ib_settings = (ImageButton) findViewById(R.id.ib_settings);
		ib_settings.setOnTouchListener(new OnTouchListener() {

			@SuppressLint({ "ResourceAsColor", "ClickableViewAccessibility" })
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				GradientDrawable gradientDrawable = (GradientDrawable) ib_settings
						.getBackground();
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					gradientDrawable.setColor(0x70000000);
					break;

				case MotionEvent.ACTION_UP:
					gradientDrawable.setColor(0x50000000);

					break;
				}
				//true表示事件传递到这里就结束了,false表示事件还未处理完,会继续传递
				//true 就是不会传递给下一个, 比如A按钮onTouch里面return true 那么A如果还有个onclick事件的话,onclick就不会被响应了
				//false 事件继续传递,onclick可以响应,但是这时A里面如果有多个动作监听,比如down和up,那么只有down会响应一次,因为up也被传递下去了
				return true;
			}
		});
	}

	/**
	 * 填充Home主页面
	 * 
	 * @author: cl
	 * @date: 2016-1-28-下午12:05:29
	 */
	private void fillHome() {
		GridView gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewHolder = null;
				if (convertView == null) {
					viewHolder = new ViewHolder();
					convertView = View.inflate(HomeActivity.this,
							R.layout.home_list_item, null);
					viewHolder.iv = (ImageView) convertView
							.findViewById(R.id.iv_home_item);
					viewHolder.tv = (TextView) convertView
							.findViewById(R.id.tv_home_item);
					convertView.setTag(viewHolder);
				} else {
					viewHolder = (ViewHolder) convertView.getTag();
				}
				viewHolder.iv
						.setImageResource(HomeActivity.IV_HOME_ITEM[position]);
				viewHolder.tv.setText(HomeActivity.TV_HOME_ITEM[position]);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return HomeActivity.TV_HOME_ITEM[position];
			}

			@Override
			public int getCount() {
				return HomeActivity.TV_HOME_ITEM.length;
			}
		});
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
