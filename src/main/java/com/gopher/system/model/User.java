package com.gopher.system.model;

import org.springframework.data.annotation.Transient;

public class User extends BaseModel {
	/**
	 * 客户用户
	 */
	public final static int CUSTOMER= 1;
	/**
	 * 管理者用户
	 */
	public final static int MANAGER = 2;

    private static final long serialVersionUID = 2039078978371646988L;

    private String name;
    
    private String account;
    
    private String password;
    
    private String phone;
    
    private String wechat;
    
    private int userType = CUSTOMER;
    @Transient
    private int customerId;

    public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [name=");
		builder.append(name);
		builder.append(", account=");
		builder.append(account);
		builder.append(", password=");
		builder.append(password);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", wechat=");
		builder.append(wechat);
		builder.append(", userType=");
		builder.append(userType);
		builder.append("]");
		return builder.toString();
	}

}
