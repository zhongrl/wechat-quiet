/**
 * 
 */
package com.quiet.live.hall.utils.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class HttpUtil {
	
	/**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * // add by chenrm 2016年7月27日, 上午10:45:23 [从 TCLCustomerLoginController 迁移过来的 ]
     */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		 // add by lijiaqi 2016年10月25日, 上午10:04:12 [使用StringBuilder代替字符串+号拼接]
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// add by pengyan 2016年4月22日,上午11:55:49,pengyan [ 设置超时 时间为2秒]
			conn.setConnectTimeout(90000);
			conn.setReadTimeout(90000);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	} 
	
	/**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * // add by chenrm 2016年7月27日, 上午10:45:23 [从 TCLCustomerLoginController 迁移过来的 ]
     */
	public static String sendPostJson(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		 // add by lijiaqi 2016年10月25日, 上午10:04:12 [使用StringBuilder代替字符串+号拼接]
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type","application/json");
			conn.setRequestProperty("Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// add by pengyan 2016年4月22日,上午11:55:49,pengyan [ 设置超时 时间为2秒]
			conn.setConnectTimeout(90000);
			conn.setReadTimeout(90000);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	} 
	
	
	
	/**
	 * 通过网站域名URL获取该网站的源码--用于获取公共头尾<br/>
	 * 内容换行输出
	 * @param url 请求的地址
	 * @return 返回请求的结果
	 */
	public static String sendGet(String urlStr) {
		InputStream in = null;
		BufferedReader reader = null;
		String resultStr ="";
        try {
			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url
					.openConnection();
			httpCon.setRequestMethod("GET");
			in = httpCon.getInputStream();
 
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {   
                sb.append(line);   
            }
			resultStr = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader!=null)	reader.close();
				if(in!=null)	in.close();
			} catch (Exception e) {
			}
		}
		return resultStr;
	 }
}
