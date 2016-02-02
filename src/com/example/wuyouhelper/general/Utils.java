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
 * ������
 * 
 * @author: cl
 * @date: 2016-1-28-����3:39:17
 */
public class Utils {

	private static Context context;

	private static int newVersionCode;

	private static String description;

	private static String downloadUrl;

	public static String URL_JSON = "http://10.0.2.2/long/update.json";

	private static ProgressDialog mypDialog;

	/**
	 * ������
	 * 
	 * @author: cl
	 * @date: 2016-1-28-����3:46:07
	 */
	public static void checkForUpdate(Context context) {
		Utils.context = context;
		// ���ӷ�������ȡjson
		// ����������뻺��,����ʱ����30s,1min��
		// ��������ڼ�,��������ӻ�ֱ�ӵ��ûص�����RequestCallBack,ʹ�û����еķ��ؽ��
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, Utils.URL_JSON,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException httpexception, String s) {
						Toast.makeText(
								Utils.context,
								"�޷����ӵ�������(" + httpexception.getExceptionCode()
										+ ")", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseinfo) {
						alertUpdate(responseinfo.result);
					}
				});

	}

	/**
	 * ����json����,�ж��Ƿ���ʾ����
	 * 
	 * @author: cl
	 * @date: 2016-1-29-����12:01:39
	 */
	private static void alertUpdate(String result) {
		try {
			// ����json
			JSONObject object = new JSONObject(result);
			newVersionCode = object.getInt("versionCode");
			description = object.getString("description");
			downloadUrl = object.getString("downloadUrl");
		} catch (JSONException e) {
			Toast.makeText(Utils.context, "�������ݽ���ʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

		if (newVersionCode > Constants.VERSION_CODE) {
			// ��ʾ�Ƿ����
			Builder builder = new Builder(Utils.context);
			builder.setIcon(R.drawable.ic_launcher)
					.setTitle(R.string.alert_title).setMessage(description);
			builder.setNegativeButton("�´���˵", null);
			builder.setPositiveButton("��������", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					alertDownload();
				}
			});
			AlertDialog alertDialog = builder.show();
			alertDialog.getWindow().setLayout(
					alertDialog.getWindow().getAttributes().width, 400);

		}
	}

	/**
	 * �������ؽ��ȴ�������
	 * 
	 * @author: cl
	 * @date: 2016-1-29-����3:10:35
	 */
	private static void alertDownload() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			mypDialog = new ProgressDialog(Utils.context);
			// ���ý�������񣬷��Ϊ���Σ��п̶ȵ�
			mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			// ����ProgressDialog ����ͼ��
			mypDialog.setIcon(R.drawable.ic_launcher);
			// ����ProgressDialog ����
			mypDialog.setTitle(R.string.alert_title);
			// ����ProgressDialog ��ʾ��Ϣ
			mypDialog.setMessage("��װ��������...");
			// ����ProgressDialog �Ľ������Ƿ���ȷ
			mypDialog.setIndeterminate(false);
			// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
			mypDialog.setCancelable(true);
			// ��ProgressDialog��ʾ
			mypDialog.show();

			// ��ʼ����
			final String filePath = Environment.getExternalStorageDirectory()
					+ "/"
					+ downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1,
							downloadUrl.length());
			HttpUtils http = new HttpUtils();
			http.download(downloadUrl, filePath, true, true,
					new RequestCallBack<File>() {

						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							super.onLoading(total, current, isUploading);

							// ����ProgressDialog ����������
							mypDialog.setProgress((int) current);
							mypDialog.setMax((int) total);
						}

						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							Toast.makeText(Utils.context, "�������",
									Toast.LENGTH_SHORT).show();
							mypDialog.dismiss();
							installApp(arg0.result);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// �ļ���������ɵ����
							if (arg1.equalsIgnoreCase("maybe the file has downloaded completely")) {
								mypDialog.setProgress(1);
								mypDialog.setMax(1);
								Toast.makeText(Utils.context, "��װ���Ѵ���",
										Toast.LENGTH_SHORT).show();
								mypDialog.dismiss();
								installApp(new File(filePath));
							} else {
								// �������س�������
								mypDialog.setMessage(arg1);
							}
						}
					});
		} else {
			Toast.makeText(Utils.context, "û���ҵ�SD��", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ��ת��ϵͳ��װ���ҳ��,��װ��ɺ��Զ���
	 * 
	 * @author: cl
	 * @date: 2016-1-29-����3:54:37
	 */
	private static void installApp(File result) {
		/*
		 * ��װ�����ѡ�� Intent intent = new Intent(Intent.ACTION_VIEW);
		 * intent.addCategory(Intent.CATEGORY_DEFAULT);
		 * intent.setDataAndType(Uri.fromFile(result),
		 * "application/vnd.android.package-archive");
		 * Utils.context.startActivity(intent);
		 */

		// ��װ��ɺ��ѡ���
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(result),
				"application/vnd.android.package-archive");
		Utils.context.startActivity(intent);
//		android.os.Process.killProcess(android.os.Process.myPid());�ɵ���ǰ����,Զ��˷���-_-.

	}

}
