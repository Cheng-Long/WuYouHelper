package com.example.wuyouhelper.general;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 工具类
 * 
 * @author: cl
 * @date: 2016-1-28-下午3:39:17
 */
public class Utils {

	/**
	 * 服务器返回的Json
	 */
	private static String result;

	/**
	 * 检查更新
	 * 
	 * @author: cl
	 * @date: 2016-1-28-下午3:46:07
	 */
	public static void checkForUpdate(Context context) {
		//连接服务器获取json
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, Constants.URL_JSON,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException httpexception, String s) {
						System.out.println("连接失败"+s);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseinfo) {
						result = responseinfo.result;
						System.out.println(result);
					}
				});
		
		//根据Json判断是否更新
		if(result != null){
			try {
				JSONObject jsonObject = new JSONObject(result);
				int newVersionCode = jsonObject.getInt("versionCode");
				if(newVersionCode > Constants.VERSION_CODE){
					Toast.makeText(context, "获取到新版本信息"+result, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(context, "数据解析异常", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
		}else{
			Toast.makeText(context, "网络连接失败", Toast.LENGTH_SHORT).show();
		}
	}

}
