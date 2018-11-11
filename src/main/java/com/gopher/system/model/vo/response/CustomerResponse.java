package com.gopher.system.model.vo.response;

import com.gopher.system.model.Customer;

public class CustomerResponse extends Customer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -810733447781096388L;

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
	
	

}
