package com.gopher.system.service;

import com.gopher.system.model.vo.request.RegisterRequst;

public interface AuthService {
	/**
	 * 商户注册 平台
	 * @param registerRequst
	 */
	public void register(RegisterRequst registerRequst);
	/**
	 * 商户登录
	 */
	public void login();
	/**
	 * 登出
	 */
	public void logout();
	
	

}
