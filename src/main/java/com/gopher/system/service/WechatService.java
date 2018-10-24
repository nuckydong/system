package com.gopher.system.service;

import com.gopher.system.model.vo.response.WechatLoginResponse;

public interface WechatService {
	
	public WechatLoginResponse getSession(String code);

}
