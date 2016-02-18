package com.example.wuyouhelper.general;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.wuyouhelper.R;
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

	private static Context context;

	private static int newVersionCode;

	private static String description;

	private static String downloadUrl;

	public static String URL_JSON = "http://c-long.6655.la/long/update.json";

	private static ProgressDialog mypDialog;

	/**
	 * 检查更新
	 * 
	 * @author: cl
	 * @date: 2016-1-28-下午3:46:07
	 */
	public static void checkForUpdate(Context context) {
		Utils.context = context;
		// 连接服务器获取json
		// 请求结果会进入缓存,持续时间大概30s,1min内
		// 缓存存在期间,请求该链接会直接调用回调函数RequestCallBack,使用缓存中的返回结果
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, Utils.URL_JSON,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException httpexception, String s) {
						Toast.makeText(
								Utils.context,
								"无法连接到服务器(" + httpexception.getExceptionCode()
										+ ")", Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseinfo) {
						alertUpdate(responseinfo.result);
					}
				});

	}

	/**
	 * 解析json数据,判断是否提示更新
	 * 
	 * @author: cl
	 * @date: 2016-1-29-下午12:01:39
	 */
	private static void alertUpdate(String result) {
		try {
			// 解析json
			JSONObject object = new JSONObject(result);
			newVersionCode = object.getInt("versionCode");
			description = object.getString("description");
			downloadUrl = object.getString("downloadUrl");
		} catch (JSONException e) {
			Toast.makeText(Utils.context, "更新数据解析失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

		if (newVersionCode > Constants.VERSION_CODE) {// 有新版本
			// 提示是否更新
			Builder builder = new Builder(Utils.context);
			builder.setIcon(R.drawable.ic_launcher)
					.setTitle(R.string.alert_title).setMessage(description);
			builder.setNegativeButton("下次再说", null);
			builder.setPositiveButton("马上体验", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					alertDownload();
				}
			});
			AlertDialog alertDialog = builder.show();
			alertDialog.getWindow().setLayout(
					alertDialog.getWindow().getAttributes().width, 400);

		} else {// 当前已是最新版本

		}
	}

	/**
	 * 弹出下载进度窗口下载
	 * 
	 * @author: cl
	 * @date: 2016-1-29-下午3:10:35
	 */
	private static void alertDownload() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mypDialog = new ProgressDialog(Utils.context);
			// 设置进度条风格，风格为长形，有刻度的
			mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// 设置ProgressDialog 标题图标
			mypDialog.setIcon(R.drawable.ic_launcher);
			// 设置ProgressDialog 标题
			mypDialog.setTitle(R.string.alert_title);
			// 设置ProgressDialog 提示信息
			mypDialog.setMessage("安装包下载中...");
			// 设置ProgressDialog 的进度条是否不明确
			mypDialog.setIndeterminate(false);
			// 设置ProgressDialog 是否可以按退回按键取消
			mypDialog.setCancelable(true);
			// 让ProgressDialog显示
			mypDialog.show();

			// 开始下载
			final String filePath = Environment.getExternalStorageDirectory()
					+ "/"
					+ downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
			HttpUtils http = new HttpUtils();
			http.download(downloadUrl, filePath, true, true,
					new RequestCallBack<File>() {

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							super.onLoading(total, current, isUploading);

							// 设置ProgressDialog 进度条进度
							mypDialog.setProgress((int) current);
							mypDialog.setMax((int) total);
						}

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							Toast.makeText(Utils.context, "下载完成",
									Toast.LENGTH_SHORT).show();
							mypDialog.dismiss();
							installApp(arg0.result);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// 文件已下载完成的情况
							if (arg1.equalsIgnoreCase("maybe the file has downloaded completely")) {
								mypDialog.setProgress(1);
								mypDialog.setMax(1);
								Toast.makeText(Utils.context, "安装包已存在",
										Toast.LENGTH_SHORT).show();
								mypDialog.dismiss();
								installApp(new File(filePath));
							} else {
								// 其他下载出错的情况
								mypDialog.setMessage(arg1);
							}
						}
					});
		} else {
			Toast.makeText(Utils.context, "没有找到SD卡", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 跳转到系统安装软件页面,安装完成后自动打开
	 * 
	 * @author: cl
	 * @date: 2016-1-29-下午3:54:37
	 */
	private static void installApp(File result) {
		/*
		 * 安装完成无选择 Intent intent = new Intent(Intent.ACTION_VIEW);
		 * intent.addCategory(Intent.CATEGORY_DEFAULT);
		 * intent.setDataAndType(Uri.fromFile(result),
		 * "application/vnd.android.package-archive");
		 * Utils.context.startActivity(intent);
		 */

		// 安装完成后可选择打开
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(result),
				"application/vnd.android.package-archive");
		Utils.context.startActivity(intent);
		// android.os.Process.killProcess(android.os.Process.myPid());干掉当前进程,远离此方法-_-.

	}

}
