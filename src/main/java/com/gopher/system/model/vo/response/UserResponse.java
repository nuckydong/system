package com.gopher.system.model.vo.response;

import com.gopher.system.model.User;

public class UserResponse extends User{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9013277738231778285L;
	
	private String customerName;
	
	private int customerId;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	

}
