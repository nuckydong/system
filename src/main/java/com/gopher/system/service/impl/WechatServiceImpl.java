package com.gopher.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.vo.response.WechatLoginResponse;
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
	public WechatLoginResponse getSession(String code) {
		if(!StringUtils.hasText(code)) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		WechatLoginResponse result = new WechatLoginResponse();
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code",code);
		params.put("grantType", grantType);
		String response = HttpConenection.getInstance().sendHttpPost(sessionHost, params);
		LOG.info(response);
		if(null != response) {
			result = JSON.parseObject(response, WechatLoginResponse.class);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		String appId="wx27fdb1adf819fbc0";
		String secret="0a38b9dd78305fdbea16ff140de70e1a";
		String grantType="authorization_code";
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code","023SWBmR0cSQC82FUQmR06xKmR0SWBm2");
		params.put("grantType", grantType);
		String result = HttpConenection.getInstance().sendHttpPost(url, params);
		LOG.info(result);
	}

}
