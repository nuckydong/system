package com.gopher.system.service;

public interface CacheService {
	/**
	 * 获取
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 删除
	 * 
	 * @param key
	 */
	void delete(String key);

	/**
	 * 设置缓存
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	void set(String key, Object value, int timeout);

	/**
	 * 重设过期时间 单位秒
	 * 
	 * @param key
	 * @param timeout
	 */
	void expire(String key, int timeout);

}
