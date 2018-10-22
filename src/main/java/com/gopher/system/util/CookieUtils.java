package com.gopher.system.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class CookieUtils {
	/**
	 * 默认 cookie 生命周期 24小时
	 */
	public static final int DEFAULT_TOKEN_ALIVE = 24 * 60 * 60;
	/**
	 * cookie 的键
	 */
	public static final String COOKIE_KEY = "TOKEN";
	/**
	 * 默认路径
	 */
	public static final String DEFAULT_PATH = "/";
	/**
	 * 域
	 */
	public static final String DEFAULT_DOMAIN = ".ramosy.com";
	
    private final static Logger LOG = LoggerFactory.getLogger(CookieUtils.class);
    
    /**
     * 客户端添加cookie
     * @param response
     * @param value cookie值
     */
	public static void addCookie(HttpServletResponse response, String value) {
		addCookie(response, value, DEFAULT_TOKEN_ALIVE);
	}
	
	/**
	 * 
	 * @param response
	 * @param value cookie的值
	 * @param alive cookie生命周期
	 */
	public static void addCookie(HttpServletResponse response, String value, int alive) {
		addCookie(response, COOKIE_KEY, value, alive, DEFAULT_PATH, DEFAULT_DOMAIN);
	}
    /**
     * 
     * @param response
     * @param key cookie的键
     * @param val cookie的值
     * @param alive cookie的生命周期
     * @param path  默认路径
     * @param domain 作用的域
     */
	public static void addCookie(HttpServletResponse response, String key, String val, int alive, String path,
			String domain) {
		Cookie cookie = new Cookie(key, val);
		cookie.setMaxAge(alive);
		cookie.setPath(path);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}
    
	/**
	 * 删除客户端cookie
	 * @param request
	 */
	public static void removeCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			LOG.info("被清除的cookie:{}",JSON.toJSONString(cookies));
			for (Cookie cookie : cookies) {
				cookie.setValue(null);
				cookie.setMaxAge(0);
			}
		}
	}

}
