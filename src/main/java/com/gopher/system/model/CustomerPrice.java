package com.gopher.system.model;
/**
 *  客户和定价表关系表,一个定价表可以用于多个客户
 * @author nucky
 *
 */
public class CustomerPrice {
	
	private int id;
	/**
	 * 定价表id
	 */
	private int priceGroupId;
	/**
	 * 客户id
	 */
	private int customerId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPriceGroupId() {
		return priceGroupId;
	}

	public void setPriceGroupId(int priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	

}
