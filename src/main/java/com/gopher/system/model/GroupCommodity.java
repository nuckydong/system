package com.gopher.system.model;
/**
 * 客户自定义分组对应的商品列表
 * @author dongyangyang
 *
 */
public class GroupCommodity {
	
	private int id;
	
	private int groupId;
	
	private int commodityId;
	
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

}
