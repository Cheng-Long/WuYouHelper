package com.example.wuyouhelper.general;

import com.example.wuyouhelper.R;

/**
 * ȫ�ֳ�����
 * 
 * @author: cl
 * @date: 2016-1-27-����6:44:45
 */
public class Constants {
	
	/**
	 * �����������ȡjson����,��֤�汾��Ϣ��url
	 */
	public static String URL_JSON = "https://github.com/wyouflf/xUtils";

	/**
	 * �汾��(n.n)
	 */
	public static String VERSION_NAME;

	/**
	 * �汾��(n)
	 */
	public static int VERSION_CODE;

	/**
	 * �ȴ�ʱ��,1s
	 */
	public static long WAIT_TIME = 1 * 1000;

	/**
	 * ��ҳ�˵�ͼƬID
	 */
	public static int[] IV_HOME_ITEM = { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };

	/**
	 * ��ҳ�˵�˵��ID
	 */
	public static int[] TV_HOME_ITEM = { R.string.home_safe,
			R.string.home_callmsgsafe, R.string.home_apps,
			R.string.home_taskmanager, R.string.home_netmanager,
			R.string.home_trojan, R.string.home_sysoptimize,
			R.string.home_tools, R.string.home_settings };
}
