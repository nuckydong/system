package com.gopher.system.model.vo.response;

public class CustomerResponse {
	private int id;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 客户描述
	 */
	private String description;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * 固定电话
	 */
	private String telephone;
	/**
	 * 微信号码
	 */
	private String wechat;

	private String qq;
	
	private String priceGroupName;
	
	private String priceGroupNumber;
	
	private int priceGroupId;

	public int getPriceGroupId() {
		return priceGroupId;
	}

	public void setPriceGroupId(int priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public String getPriceGroupName() {
		return priceGroupName;
	}

	public void setPriceGroupName(String priceGroupName) {
		this.priceGroupName = priceGroupName;
	}

	public String getPriceGroupNumber() {
		return priceGroupNumber;
	}

	public void setPriceGroupNumber(String priceGroupNumber) {
		this.priceGroupNumber = priceGroupNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	

}
