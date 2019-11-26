package com.quiet.live.hall.utils;

import com.alibaba.fastjson.JSONObject;
import com.quiet.live.hall.utils.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonUtil {
	//记录日志
	private static Logger log = LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * 解析json
	 * @param response
	 * @param jsonStr
	 */
	public static Map<String, Object> getMapByJsonStr(String jsonStr){
		if(StringUtil.isEmpty(jsonStr)){
			return null;
		}
		//将字符串转为json对象
		JSONObject jsonObj = JSONObject.parseObject(jsonStr);
		Set<String> set = jsonObj.keySet();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : set) {
			map.put(key, jsonObj.get(key));
		}
		return map;
	}
	/**
	 * 取到对象
	 * @param obj
	 * @param jsonobj
	 * @return
	 */
	public static Object getObjectByJsonStr(Object obj,JSONObject jsonobj){
		if(obj==null||jsonobj==null){
			return null;
		}
		Class<? extends Object> cobj =  obj.getClass();
		//获取对象的所有属性
		Field[] fields = cobj.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			field.setAccessible(true);
			String name = field.getName();
			String type = field.getGenericType().toString();
			if(jsonobj.get(name)!=null){
				String MethodName = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造set方法
				try {
					if("int".equals(type)){
						Method method = cobj.getMethod("set"+MethodName, int.class);
						int value = Integer.parseInt(jsonobj.getString(name));
						method.invoke(obj, value);
					}else if("class java.lang.Boolean".equals(type)){
						boolean flag =false;
						if(jsonobj.get(name).equals("1")){
							flag = true;
						}
						Method method = cobj.getMethod("set"+MethodName, Boolean.class);
						method.invoke(obj, flag);
					}else if("class java.lang.String".equals(type)){
						Method method = cobj.getMethod("set"+MethodName, String.class);
						String value = (String)jsonobj.get(name);
						method.invoke(obj, value);
					}else if("float".equals(type)){
						Method method = cobj.getMethod("set"+MethodName, float.class);
						String valuestr = jsonobj.get(name)+"";
						if(valuestr.indexOf(".")>0){
							float value =  Float.parseFloat(valuestr);
							method.invoke(obj, value);
						}else{
							int value = Integer.parseInt(valuestr);
							method.invoke(obj, value);
						}
					}else if("double".equals(type)){
						Method method = cobj.getMethod("set"+MethodName, double.class);
						String valuestr = jsonobj.get(name)+"";
						if(valuestr.indexOf(".")>0){
							Double value = Double.parseDouble(valuestr);
							method.invoke(obj, value);
						}else{
							int value = Integer.parseInt(valuestr);
							method.invoke(obj, value);
						}
					}
				} catch (Exception e) {
					log.error("Object error", e);
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
    
}
