package com.gopher.system.service;

import com.gopher.system.model.vo.response.WechatAuthResponse;
import com.gopher.system.model.vo.response.WechatTokenResponse;
import com.gopher.system.model.vo.response.WechatUserInfoResponse;

public interface WechatService {
	
	public WechatAuthResponse getSession(final String code);
	
	public WechatTokenResponse getToken();
	
	public WechatUserInfoResponse getUserInfo(final String accessToken,final String openId);
	
	
	public WechatUserInfoResponse getUserInfo(final String code);

}
