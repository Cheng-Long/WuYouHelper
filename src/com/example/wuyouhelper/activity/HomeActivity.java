package com.example.wuyouhelper.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuyouhelper.R;
import com.example.wuyouhelper.general.Constants;
import com.example.wuyouhelper.general.Utils;

/**
 * 主页-菜单
 * 
 * @author: cl
 * @date: 2016-1-28-下午3:14:01
 */
public class HomeActivity extends Activity {

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

			@SuppressLint("ViewHolder")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = View.inflate(HomeActivity.this,
						R.layout.home_list_item, null);
				ImageView iv = (ImageView) view.findViewById(R.id.iv_home_item);
				TextView tv = (TextView) view.findViewById(R.id.tv_home_item);
				iv.setImageResource(Constants.IV_HOME_ITEM[position]);
				tv.setText(Constants.TV_HOME_ITEM[position]);
				return view;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return Constants.TV_HOME_ITEM[position];
			}

			@Override
			public int getCount() {
				return Constants.TV_HOME_ITEM.length;
			}
		});
	}
}
