package com.gopher.system.service;

import com.gopher.system.model.vo.request.LoginRequst;
import com.gopher.system.model.vo.request.LogoutRequst;
import com.gopher.system.model.vo.request.RegisterRequst;
import com.gopher.system.model.vo.response.LoginResponse;

public interface AuthService {
	/**
	 * 商户注册 平台
	 * @param registerRequst
	 */
	public void register(RegisterRequst registerRequst);
	/**
	 * 商户登录
	 */
	public LoginResponse login(LoginRequst loginRequest);
	/**
	 * 登出
	 */
	public void logout(LogoutRequst logoutRequest);
	
	

}
