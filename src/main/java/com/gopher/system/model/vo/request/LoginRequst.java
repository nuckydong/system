package com.gopher.system.model.vo.request;

public class LoginRequst {
	/**
	 * 账户
	 */
	private String account;
	/**
	 * 密码(密文传输MD5加密)
	 */
	private String password;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
