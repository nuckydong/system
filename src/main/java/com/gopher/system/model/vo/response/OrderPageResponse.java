package com.gopher.system.model.vo.response;

import java.util.Date;

public class OrderPageResponse {
	
	/**
	 * 订单号 除id主键外 唯一标识
	 */
	private String number;
	/**
	 * 客户ID
	 */
	private int customerId;
	/**
	 * @see com.gopher.system.constant.State
	 * 状态 逻辑删除
	 */
	private int state;
	/**
	 * 备注留言
	 */
	private String remark;
	
    private int id;
    
    private Date  createTime;
    
    private Date  updateTime;
    
    private String priceGroupNumber;
    
	private String customerName;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPriceGroupNumber() {
		return priceGroupNumber;
	}

	public void setPriceGroupNumber(String priceGroupNumber) {
		this.priceGroupNumber = priceGroupNumber;
	}

}
