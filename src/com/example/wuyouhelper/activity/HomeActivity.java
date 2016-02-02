package com.example.wuyouhelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Utils.checkForUpdate(this);
		fillHome();
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
