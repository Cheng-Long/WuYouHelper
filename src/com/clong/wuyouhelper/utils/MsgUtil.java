package com.clong.wuyouhelper.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 发送短信,需要连接网络
 * 
 * @author: cl
 * @date: 2016-3-1-下午3:26:13
 */
public class MsgUtil {

	/**
	 * 发送短信
	 * 
	 * @author: cl
	 * @param phone
	 *            接收人号码
	 * @param content
	 *            发送的短信内容
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @date: 2016-3-1-下午4:51:44
	 */
	public static String sendMsg(String phone, String content) throws HttpException, IOException {
		String url1 = "http://gbk.sms.webchinese.cn";
		return excuteRequest(phone, content, url1, true);
	}

	/**
	 * 查询剩余可发送短信数
	 * 
	 * @author: cl
	 * @return 剩余数
	 * @throws HttpException
	 * @throws IOException
	 * @date: 2016-3-1-下午4:34:49
	 */
	public static String getNum() throws HttpException, IOException {
		String url2 = "http://sms.webchinese.cn/web_api/SMS/GBK/?Action=SMS_Num";
		return excuteRequest(null, null, url2, false);
	}

	/**
	 * 
	 * @author: cl
	 * @param phone
	 *            接收的电话号码
	 * @param content
	 *            短信内容
	 * @param url
	 *            请求地址
	 * @param flag
	 *            true-发送短信,false-查询剩余可发送短信数
	 * @return flag=true,发送短信状态;flag=false,剩余可发送短信数
	 * @throws HttpException
	 * @throws IOException
	 * @date: 2016-3-1-下午4:30:41
	 */
	private static String excuteRequest(String phone, String content,
			String url, boolean flag) throws HttpException, IOException {
		// 短信发送后返回值的说明,大于0则表示成功
		Map<String, Object> desc = new HashMap<String, Object>();
		desc.put("-1", "没有该用户账户");
		desc.put("-2", "接口密钥不正确");
		desc.put("-21", "MD5接口密钥加密不正确");
		desc.put("-3", "短信数量不足");
		desc.put("-11", "该用户被禁用");
		desc.put("-14", "短信内容出现非法字符");
		desc.put("-4", "手机号格式不正确");
		desc.put("-41", "手机号码为空");
		desc.put("-42", "短信内容为空");
		desc.put("-51", "短信签名格式不正确,格式为:【签名内容】");
		desc.put("-6", "IP限制");

		// ------------------start----------------------
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", "clong"),// 注册的用户名
				new NameValuePair("Key", "4ad0303bda444cd3ec00"),// 注册成功后，登录网站后得到的密钥
				new NameValuePair("smsMob", phone),// 手机号码
				new NameValuePair("smsText", content) };// 短信内容
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println("---" + h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes(
				"gbk"));
		// 释放连接
		post.releaseConnection();
		// ------------------end----------------------

		int count = Integer.parseInt(result);
		if (flag) {
			return count > 0 ? "短信发送数量:" + count : desc.get(result).toString();
		} else {
			return count > 0 ? "剩余可发送短信数量:" + count : desc.get(result)
					.toString();
		}
	}

}
