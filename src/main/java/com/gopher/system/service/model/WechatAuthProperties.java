package com.gopher.system.service.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WechatAuthProperties {
	
	@Value("${wechat.sessionHost}")
	private String sessionHost;

	@Value("${wechat.appId}")
	private String appId;

	@Value("${wechat.secret}")
	private String secret;

	@Value("${wechat.grantType}")
	private String grantType;

	public String getSessionHost() {
		return sessionHost;
	}

	public void setSessionHost(String sessionHost) {
		this.sessionHost = sessionHost;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
}
