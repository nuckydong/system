package com.gopher.system.model;
/**
 * 商品类型(等级) 员工餐/客餐
 * @author dongyangyang
 *
 */
public class CommodityType  extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -173762377599927440L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 说明
	 */
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
