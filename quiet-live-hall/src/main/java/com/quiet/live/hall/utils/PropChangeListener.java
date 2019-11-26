package com.quiet.live.hall.utils;

/**
 * 配置信息修改监听器
 * @author marlon.luo
 *
 */
public interface PropChangeListener {
	void onChange(String key, String oldValue, String newValue);
}
