package com.quiet.live.hall.utils.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quiet.live.hall.utils.base.HttpUtil;
import com.quiet.live.hall.utils.json.JSONUtils;

@Component
public class BaiduApiLocation {

//	public static void main(String[] args) {
//		getAddPostion("澳柯玛大街18号");
//	}
 
	@Value("${baiduApiUrl}")
	private String baiduApiUrl;
	
	@Value("${baiduAccount}")
	private String baiduAccount;
	
//	public String getAddPostion(String address) {
//		String resultStr = "";
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("address", address);
//		map.put("output", "json");
//		map.put("ak", baiduAccount);
//		String tmp = HttpUtil.sendPost(baiduApiUrl, JSON.toJSONString(map));
//		Map<String, String> tmpMap = JSONUtils.jsonToMap(tmp, String.class,  String.class);
//		if ("0".equals(tmpMap.get("status"))) {
//			Map<String, String> resultMap = JSONUtils.jsonToMap(tmpMap.get("result"), String.class,  String.class);
//			Map<String, String> locationMap = JSONUtils.jsonToMap(resultMap.get("location"), String.class,  String.class);
//			String lng = locationMap.get("lng");
//			String lat = locationMap.get("lat");
//			resultStr =  lng + "," + lat ;
//			return resultStr;
//		}
//		return null;
//		
//	}
	
//	 public static void main(String[] args) {
//	        String dom = "北京王府井";
//	        String coordinate = getCoordinate(dom);
//	        System.out.println("'" + dom + "'的经纬度为：" + coordinate);
//	        // System.err.println("######同步坐标已达到日配额6000限制，请明天再试！#####");
//	    }

	    // 调用百度地图API根据地址，获取坐标
	    public String getCoordinate(String address) {
	        if (address != null && !"".equals(address)) {
	            address = address.replaceAll("\\s*", "").replace("#", "栋");
	            String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + baiduAccount;
	            String json = loadJSON(url);
	            if (json != null && !"".equals(json)) {
	                JSONObject obj = JSONObject.parseObject(json);
	                if ("0".equals(obj.getString("status"))) {
	                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
	                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
	                    DecimalFormat df = new DecimalFormat("#.######");
	                    return df.format(lng) + "," + df.format(lat);
	                }
	            }
	        }
	        return null;
	    }

	    public String loadJSON(String url) {
	        StringBuilder json = new StringBuilder();
	        try {
	            URL oracle = new URL(url);
	            URLConnection yc = oracle.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
	            String inputLine = null;
	            while ((inputLine = in.readLine()) != null) {
	                json.append(inputLine);
	            }
	            in.close();
	        } catch (MalformedURLException e) {} catch (IOException e) {}
	        return json.toString();
	    }

	    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
	    /*
	     * public String MD5(String md5) { try { java.security.MessageDigest md = java.security.MessageDigest .getInstance("MD5"); byte[] array = md.digest(md5.getBytes()); StringBuffer sb = new StringBuffer(); for (int i = 0; i < array.length; ++i) { sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100) .substring(1, 3)); } return sb.toString(); } catch (java.security.NoSuchAlgorithmException e) {
	     * } return null; }
	     */
}
