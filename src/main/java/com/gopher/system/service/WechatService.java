package com.gopher.system.service;

import com.gopher.system.model.vo.WechatLoginResponse;

public interface WechatService {
	
	public WechatLoginResponse wechatLogin(String code);

}
