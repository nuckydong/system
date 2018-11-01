package com.gopher.system.model;

/**
 * 商品价格 (对应不同商户 具有不同的价格)
 * 
 * @author dongyangyang
 *
 */
public class CommodityPrice {
	private int id;
	/**
	 * 对应商品
	 */
	private int commodityId;
	/**
	 * 所属哪个定价号
	 */
	private int priceGroupId;
	/**
	 * 价格 单位(分)
	 */
	private int price;

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
