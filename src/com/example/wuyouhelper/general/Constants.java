package com.example.wuyouhelper.general;

import com.example.wuyouhelper.R;

/**
 * 全局常量类
 * 
 * @author: cl
 * @date: 2016-1-27-下午6:44:45
 */
public class Constants {
	
	/**
	 * 请求服务器获取json数据,验证版本信息的url
	 */
	public static String URL_JSON = "https://github.com/wyouflf/xUtils";

	/**
	 * 版本名(n.n)
	 */
	public static String VERSION_NAME;

	/**
	 * 版本号(n)
	 */
	public static int VERSION_CODE;

	/**
	 * 等待时间,1s
	 */
	public static long WAIT_TIME = 1 * 1000;

	/**
	 * 主页菜单图片ID
	 */
	public static int[] IV_HOME_ITEM = { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };

	/**
	 * 主页菜单说明ID
	 */
	public static int[] TV_HOME_ITEM = { R.string.home_safe,
			R.string.home_callmsgsafe, R.string.home_apps,
			R.string.home_taskmanager, R.string.home_netmanager,
			R.string.home_trojan, R.string.home_sysoptimize,
			R.string.home_tools, R.string.home_settings };
}
