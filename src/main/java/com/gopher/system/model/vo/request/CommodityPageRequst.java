package com.gopher.system.model.vo.request;

import com.gopher.system.model.Commodity;
import com.gopher.system.util.Page;

public class CommodityPageRequst extends Page<Commodity>{
	/**
	 * 商品名称模糊查询
	 */
	private String name;
	/**
	 * 商品类型 全匹配
	 */
	private int commodityTypeId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCommodityTypeId() {
		return commodityTypeId;
	}

	public void setCommodityTypeId(int commodityTypeId) {
		this.commodityTypeId = commodityTypeId;
	}
	
	

}
