package com.clong.wuyouhelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clong.wuyouhelper.R;
import com.clong.wuyouhelper.utils.UpdateUtil;

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

	/**
	 * 点击退出时的时间记录
	 */
	private long exitTime = 0;

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		if (sharedPreferences.getBoolean("auto_update", true)) {
			UpdateUtil.checkForUpdate(this);
		}
		fillHome();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 侧边栏按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-26-下午4:41:59
	 */
	public void sidebar(View v) {
		Toast.makeText(this, "侧边栏功能,开发中...", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 设置按钮
	 * 
	 * @author: cl
	 * @param v
	 * @date: 2016-2-26-下午4:42:24
	 */
	public void setting(View v) {
		startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
	}

	/**
	 * 填充Home主页面,并监听子菜单
	 * 
	 * @author: cl
	 * @date: 2016-1-28-下午12:05:29
	 */
	private void fillHome() {
		GridView gv_home = (GridView) findViewById(R.id.gv_home);
		gv_home.setAdapter(new HomeAdapter());
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (HomeActivity.TV_HOME_ITEM[position]) {
				// 手机防盗菜单
				case R.string.home_safe:
					startActivity(new Intent(HomeActivity.this,
							Safe_CheckPass_Activity.class));
					break;
				}
			}
		});
	}

	class HomeAdapter extends BaseAdapter {
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
			viewHolder.iv.setImageResource(HomeActivity.IV_HOME_ITEM[position]);
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
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
