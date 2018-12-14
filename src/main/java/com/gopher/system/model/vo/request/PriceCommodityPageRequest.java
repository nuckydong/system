package com.gopher.system.model.vo.request;

import com.gopher.system.model.Commodity;
import com.gopher.system.util.Page;

public class PriceCommodityPageRequest extends Page<Commodity>{
	private String commodityName;//商品名称
	private int commodityTypeId;//商品分类
	private int level;//商品等级
	private int id;//priceGroupId
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

}
