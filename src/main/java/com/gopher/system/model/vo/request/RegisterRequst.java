package com.gopher.system.model.vo.request;

public class RegisterRequst {
	/**
	 * 账户
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 公司(企业)名称
	 */
	private String company;
	/**
	 * 电话号码
	 */
	private String phone;
	
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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
