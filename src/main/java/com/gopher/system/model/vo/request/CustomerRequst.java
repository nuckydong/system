package com.gopher.system.model.vo.request;

import com.gopher.system.model.Customer;

public class CustomerRequst extends Customer{
	private static final long serialVersionUID = -8196842740652683993L;
	
	private int priceGroupId;

	public int getPriceGroupId() {
		return priceGroupId;
	}

	public void setPriceGroupId(int priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	
	

}
