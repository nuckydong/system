package com.gopher.system.model;
/**
 * 客户初始化选中的商品
 * @author dongyangyang
 *
 */
public class CustomerInitCommodity {
	
	private int id;
	/**
	 * 客户ID
	 */
	private int customerId;
	/**
	 * 商品ID
	 */
	private int commodityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}
	
	

}
