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
    /**
     * https://api.weixin.qq.com/cgi-bin/token?appid=wx27fdb1adf819fbc0&secret=0a38b9dd78305fdbea16ff140de70e1a&grant_type=client_credential
     */
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
    /**
     * https://api.weixin.qq.com/cgi-bin/token?appid=wx27fdb1adf819fbc0&secret=0a38b9dd78305fdbea16ff140de70e1a&grant_type=client_credential
     */
	@Override
	public WechatTokenResponse getToken() {
		String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s",this.grantTypeToken,this.appId,this.secret);
		String response = HttpConenection.getInstance().sendHttpPost(Url);
		LOG.info("获取到token：{}",response);
		return JSON.parseObject(response, WechatTokenResponse.class);
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
		LOG.info("获取到的用户信息：{}",response);
		return null;
	}


	@Override
	public WechatUserInfoResponse getUserInfo(String code) {
		WechatTokenResponse token = this.getToken();
		if(null == token){
			LOG.error("获取ACCESS_TOKEN失败");
			return null;
		}
		WechatAuthResponse session = this.getSession(code);
		if(null == session){
			LOG.error("获取SESSION失败");
			return null;
		}
		final String access_token = token.getAccess_token();
		final String open_id = session.getOpen_id();
		
		if(StringUtils.hasText(access_token) && StringUtils.hasText(open_id)){
			return this.getUserInfo(access_token, open_id);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("access_token", "15_WO1RajrEiL205ZCU0wcNwYIww8IdzBHcb9df12PW0jHUPdBWOtJsqQb7t0trlvra_bh-7ad3m8GT5b8Wdgo9wIuk2hf2wk4kSnZjP8O2FwmT1nCF9jz-gEiA_t3iuIYUJiuninus3on1HHNWQIRjACAWOX");
		params.put("openid", "oWUh35CFPZyeGOh_G3YxfOkJyIUk");
		params.put("lang", "zh_CN");
		String response = HttpConenection.getInstance().sendHttpPost("https://api.weixin.qq.com/cgi-bin/user/info", params);
		LOG.info("获取到的用户信息：{}",response);
	}

}
