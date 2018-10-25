package com.gopher.system.model;
/**
 * 商品价格 (对应不同商户 具有不同的价格)
 * @author dongyangyang
 *
 */
public class CommodityPrice extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1519964506001485708L;
	/**
	 * 对应客户
	 */
	private int customerId;
	/**
	 * 对应商品
	 */
	private int commodityId;
	/**
	 * 价格 单位(分)
	 */
	private int price;
	
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	

}
