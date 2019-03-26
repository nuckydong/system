package com.gopher.system.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtils {
	private HashMap<String,String> hash2;
	public static final String USER_KEY="user";
	public static final String APPLICATION ="application";
	private ThreadLocalUtils() {

	}
	private final static ThreadLocal<Map<String, Object>> contextHolder = new ThreadLocal<>();

	public static void setObject(String key, Object obj) {
		Map<String, Object> map = contextHolder.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			contextHolder.set(map);
		}
		map.put(key, obj);
	}

	public static void set(Map<String, Object> map) {
		contextHolder.set(map);
	}

	public static Map<String, Object> getMap() {
		return contextHolder.get();
	}

	public static Object getObject(String key) {
		Map<String, Object> map = contextHolder.get();
		if (map == null)
			return null;
		else
			return map.get(key);
	}

	public static void remove() {
		contextHolder.remove();
	}
}
