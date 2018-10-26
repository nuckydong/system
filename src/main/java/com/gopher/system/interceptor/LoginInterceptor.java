package com.gopher.system.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.response.LoginResponse;
import com.gopher.system.service.CacheService;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private CacheService cacheService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    	final String token = httpServletRequest.getParameter("token");
    	if(!StringUtils.hasText(token)){
    		// 没有传token
			httpServletResponse.getWriter().write(JSON.toJSONString(new Result(-1,"您还没有登录,请登录",false)));
    		return false;
    	}
    	Object obj = cacheService.get(token);
    	if(null == obj){
    		// 缓存中没有对应的缓存
    		httpServletResponse.getWriter().write(JSON.toJSONString(new Result(-1,"您还没有登录,请登录",false)));
    		return false;
    	}
    	LoginResponse rsp = (LoginResponse)obj;
    	if(null != rsp){
    		if(rsp.getExpiry() <= System.currentTimeMillis()){
    			// 令牌过期 请重新登录
    			httpServletResponse.getWriter().write(JSON.toJSONString(new Result(-1,"会话已过期,请重新登录",false)));
    			return false;
    		}
    	}
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
