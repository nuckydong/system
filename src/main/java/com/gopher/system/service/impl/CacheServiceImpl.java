package com.gopher.system.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.gopher.system.service.CacheService;
@Service
public class CacheServiceImpl implements CacheService{
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Override
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void set(String key, Object value,int timeout) {
		redisTemplate.opsForValue().set(key, value, timeout,TimeUnit.SECONDS);
	}
	
	@Override
	public void expire(String key,int timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}



}
