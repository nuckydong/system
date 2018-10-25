package com.gopher.system.model;
/**
 * 订单对应的商品
 * @author dongyangyang
 *
 */
public class OrderCommodity {
    private int id;
    /**
     * 订单ID
     */
    private int orderId;
    /**
     * 商品ID
     */
    private int commodityId;
    /**
     * 订单产生时 商品的价格
     */
    private int currentPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
    
    

}
