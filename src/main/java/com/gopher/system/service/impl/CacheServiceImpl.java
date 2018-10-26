package com.gopher.system.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.gopher.system.service.CacheService;
@Service
public class CacheServiceImpl implements CacheService{

	@Override
	public Object get(String key) {
		return localCache.get(key);
	}

	@Override
	public void set(String key, Object value) {
		localCache.put(key, value);
	}
	private static final Map<String,Object> localCache = new ConcurrentHashMap<>();


}
