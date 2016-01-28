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
 * ������
 * 
 * @author: cl
 * @date: 2016-1-28-����3:39:17
 */
public class Utils {

	/**
	 * ���������ص�Json
	 */
	private static String result;

	/**
	 * ������
	 * 
	 * @author: cl
	 * @date: 2016-1-28-����3:46:07
	 */
	public static void checkForUpdate(Context context) {
		//���ӷ�������ȡjson
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, Constants.URL_JSON,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException httpexception, String s) {
						System.out.println("����ʧ��"+s);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseinfo) {
						result = responseinfo.result;
						System.out.println(result);
					}
				});
		
		//����Json�ж��Ƿ����
		if(result != null){
			try {
				JSONObject jsonObject = new JSONObject(result);
				int newVersionCode = jsonObject.getInt("versionCode");
				if(newVersionCode > Constants.VERSION_CODE){
					Toast.makeText(context, "��ȡ���°汾��Ϣ"+result, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(context, "���ݽ����쳣", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} 
		}else{
			Toast.makeText(context, "��������ʧ��", Toast.LENGTH_SHORT).show();
		}
	}

}
