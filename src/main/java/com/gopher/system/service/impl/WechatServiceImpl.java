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
import com.gopher.system.model.vo.response.WechatAuthResponse;
import com.gopher.system.model.vo.response.WechatTokenResponse;
import com.gopher.system.model.vo.response.WechatUserInfoResponse;
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

	@Value("${wechat.grantType.auth}")
	private String grantTypeAuth;
	
	@Value("${wechat.grantType.token}")
	private String grantTypeToken;
	
	@Value("${wechat.tokenHost}")
	private String tokenHost;
	
	@Value("${wechat.userInfoHost}")
	private String userInfoHost;
	
    private static final Logger LOG = LoggerFactory.getLogger(WechatServiceImpl.class);

	@Override
	public WechatAuthResponse getSession(String code) {
		if(!StringUtils.hasText(code)) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		WechatAuthResponse result = new WechatAuthResponse();
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code",code);
		params.put("grantType", grantTypeAuth);
		String response = HttpConenection.getInstance().sendHttpPost(sessionHost, params);
		LOG.info(response);
		if(null != response) {
			result = JSON.parseObject(response, WechatAuthResponse.class);
		}
		return result;
	}


	@Override
	public WechatTokenResponse getToken() {
		Map<String, String> params = new HashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("grantType", grantTypeToken);
		String response = HttpConenection.getInstance().sendHttpPost(tokenHost, params);
		LOG.info(response);
		return null;
	}

    /**
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     */
	@Override
	public WechatUserInfoResponse getUserInfo(String accessToken, String openid) {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", accessToken);
		params.put("openid", openid);
		params.put("lang", "zh_CN");
		String response = HttpConenection.getInstance().sendHttpPost(userInfoHost, params);
		LOG.info(response);
		return null;
	}

}
