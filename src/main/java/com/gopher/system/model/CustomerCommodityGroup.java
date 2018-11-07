package com.gopher.system.model;
/**
 * 客户的商品菜单分组
 * @author dongyangyang
 *
 */
public class CustomerCommodityGroup extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7270441457487201156L;
    /**
     * 所属客户
     */
	private int customerId;
    /**
     * 名称
     */
	private String name;
	/**
	 * 备注,说明
	 */
	private String remark;
	/**
	 * 排序
	 */
	private int sort;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	

}
