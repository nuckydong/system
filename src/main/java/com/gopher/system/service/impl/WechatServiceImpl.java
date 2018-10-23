package com.gopher.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gopher.system.service.WechatService;
import com.gopher.system.util.HttpConenection;
@Service
public class WechatServiceImpl implements WechatService{
	
	@Value("${wechat.sessionHost}")
	private String sessionHost;

	@Value("${wechat.appId}")
	private String appId;

	@Value("${wechat.secret}")
	private String secret;

	@Value("${wechat.grantType}")
	private String grantType;
	
    private static final Logger LOG = LoggerFactory.getLogger(WechatServiceImpl.class);

	@Override
	public void wechatLogin() {
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code","123");
		params.put("grantType", grantType);
		String result = HttpConenection.getInstance().sendHttpPost(sessionHost, params);
		LOG.info(result);
	}

}
