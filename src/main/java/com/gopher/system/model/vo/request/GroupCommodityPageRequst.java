package com.gopher.system.model.vo.request;

import com.gopher.system.model.GroupCommodity;
import com.gopher.system.util.Page;

public class GroupCommodityPageRequst extends Page<GroupCommodity>{
	private String commodityName;
	private int commodityTypeId;
	private int level;
	private int id;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public int getCommodityTypeId() {
		return commodityTypeId;
	}
	public void setCommodityTypeId(int commodityTypeId) {
		this.commodityTypeId = commodityTypeId;
	}

}
