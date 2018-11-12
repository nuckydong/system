package com.gopher.system.model.vo.request;

import com.gopher.system.model.User;
import com.gopher.system.util.Page;

public class UserPageRequst extends Page<User>{
	private int currentLoginUser;
	
	private int customerId;

	public int getCurrentLoginUser() {
		return currentLoginUser;
	}

	public void setCurrentLoginUser(int currentLoginUser) {
		this.currentLoginUser = currentLoginUser;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	

}
