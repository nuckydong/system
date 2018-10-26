package com.gopher.system.service;

public interface CacheService {
	
	Object get(String key);
	
	void set(String key, Object value); 

}
