package com.quiet.live.hall.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Created by william zhong .
 */
@Component
public class RedisComponent  {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

    @Autowired
    Environment env;



	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public double increment(String key, long value) {
		return stringRedisTemplate.opsForHash().increment("primarykey", key, value);
	}

	public void setEx(String key, String value, int seconds) {
		stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
	}

	public String get(String key) {
		return this.stringRedisTemplate.opsForValue().get(key);
	}

	public void del(String key) {
		this.stringRedisTemplate.delete(key);
	}

	public boolean exists(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	public void expire(String key, int seconds) {
		stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	public void set(String key, Object value) {
		set(key, JSON.toJSONString(value));
	}

	public void setEx(String key, Object value, int seconds) {
		setEx(key, JSON.toJSONString(value), seconds);
	}

	public <T> T get(String key, Class<T> clazz) {
		String jsonObj = get(key);
		if (jsonObj != null) {
			return JSON.parseObject(jsonObj, clazz);
		}
		return null;
	}

	public <T> List<T> getList(String key, Class<T> clazz) {
		String jsonObj = get(key);
		if (jsonObj != null) {
			return JSON.parseArray(jsonObj, clazz);
		}
		return null;
	}
	
	public long incrementNormal(String key, long delte){
		return stringRedisTemplate.opsForValue().increment(key, delte);
	}
	
	public void zAdd(String key, String name, double score) {
		stringRedisTemplate.opsForZSet().add(key, name, score);
	}
	
	public Double zScore(String key, String name) {
		return stringRedisTemplate.opsForZSet().score(key, name);
	}
	
	public Long zRank(String key, String name) {
		return stringRedisTemplate.opsForZSet().rank(key, name);
	}
	
	public TypedTuple<String> zRankFirst(String key) {
		Set<TypedTuple<String>> set = stringRedisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
		if (CollectionUtils.isNotEmpty(set)) {
			return set.iterator().next();
		}
		
		return null;
	}
	
	public Set<TypedTuple<String>> zRange(String key) {
		return stringRedisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
	}

	public Cursor<TypedTuple<String>> zScan(String key) {
		return stringRedisTemplate.opsForZSet().scan(key, ScanOptions.NONE);
	}
	
	public <T> List<T> getHashValues(String key, Class<T> clazz) {
		List<Object> jsonObjList = stringRedisTemplate.opsForHash().values(key);
		if (CollectionUtils.isEmpty(jsonObjList)) {
			return Collections.emptyList();
		}
		
		List<T> list = new ArrayList<>(jsonObjList.size());
		for (Object object : jsonObjList) {
			if (object != null) {
				list.add(JSON.parseObject((String) object, clazz));
			}
		}
		
		return list;
	}
	
	public void setHashValue(String key, String hashKey, Object value) {
		stringRedisTemplate.opsForHash().put(key, hashKey, JSON.toJSONString(value));
	}
	
	public void setHash(String key, Map<String, Object> map) {
		if (MapUtils.isEmpty(map)) {
			return;
		}
		
		for (Map.Entry<String,Object> entry : map.entrySet()) {
			entry.setValue(JSON.toJSONString(entry.getValue()));
		}
		
		stringRedisTemplate.opsForHash().putAll(key, map);
	}
	
}
