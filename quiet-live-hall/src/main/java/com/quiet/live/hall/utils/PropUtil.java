package com.quiet.live.hall.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * 公共配置项
 * @author luoml
 *
 */
public class PropUtil {
	private static Logger log = LoggerFactory.getLogger(PropUtil.class);
	
	private volatile static Map<String, String> prop = new HashMap<>();
	private volatile static Map<String, String> commonProp = new HashMap<>();
	//使用本地参数
	private static Environment localEnv;
	private static final Map<String, Set<PropChangeListener>> listenerMap = new ConcurrentHashMap<>();
	
	public static String get(String key) {
		return getInner(key);
	}
	public static String get(String key, String defaultValue) {
		String value = getInner(key);
		return StringUtils.isNotEmpty(value) ? value : defaultValue;
	}
	public static int getInt(String key) {
		String value = getInner(key);
		return Integer.parseInt(value);
	}
	public static int getInt(String key, int defaultValue) {
		String result = getInner(key);
		return result != null ? Integer.parseInt(result) : defaultValue;
	}
	
	
	private static String getInner(String key){
		if(localEnv != null){
			return localEnv.getProperty(key);
		}
		String result = prop.get(key);
		if(result == null){
			result = commonProp.get(key);
		}
		return result;
	}
	public static void addListener(String key, PropChangeListener listener) {
		if (listenerMap.get(key) == null) {
			HashSet<PropChangeListener> set = new HashSet<>();
			set.add(listener);
			listenerMap.put(key, set);
		} else {
			listenerMap.get(key).add(listener);
		}
	}
	
	static{
		addListener("log.level.switch", new PropChangeListener() {
			@Override
			public void onChange(String key, String oldValue, String newValue) {
				ch.qos.logback.classic.Logger loggerLevel = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
				if("debug".equalsIgnoreCase(newValue)){
					loggerLevel.setLevel(ch.qos.logback.classic.Level.DEBUG);
					log.info("Logback日志级别改成DEBUG");
				}else if("info".equalsIgnoreCase(newValue)){
					loggerLevel.setLevel(ch.qos.logback.classic.Level.INFO);
					log.info("Logback日志级别改成INFO");
				}else if("error".equalsIgnoreCase(newValue)){
					loggerLevel.setLevel(ch.qos.logback.classic.Level.ERROR);
					log.error("Logback日志级别改成ERROR");
				}
			}
		});
	}
	static void loadChange(Map<String, String> prop, Map<String, String> commonProp) {
		for (String key : listenerMap.keySet()) {
			if(prop != null){
				String oldValue = PropUtil.get(key);
				String newValue = prop.get(key);
				if (!StringUtils.equals(oldValue, newValue)) {
					for (PropChangeListener listener : listenerMap.get(key)) {
						listener.onChange(key, oldValue, newValue);
					}
				}
			}
			if(commonProp != null){
				Map<String, String> localCommonProp = PropUtil.getCommonProp();
				String oldCommonValue = localCommonProp.get(key);
				String newCommonValue = commonProp.get(key);
				if (!StringUtils.equals(oldCommonValue, newCommonValue)) {
					for (PropChangeListener listener : listenerMap.get(key)) {
						listener.onChange(key, oldCommonValue, newCommonValue);
					}
				}
			}
		}
	}
	static void setProp(Map<String, String> prop){
		PropUtil.prop = prop;
	}
	static void set(String key, Object val){
		if(val != null && val.getClass() == String.class){
			prop.put(key, (String)val);
		}
	}
	static void setEnv(Environment env){
		PropUtil.localEnv = env;
	}
	static void setCommonProp(Map<String, String> commonProp){
		PropUtil.commonProp = commonProp;
	}
	static Map<String, String> getProp(){
		return PropUtil.prop;
	}
	static Map<String, String> getCommonProp(){
		return PropUtil.commonProp;
	}
	
}
