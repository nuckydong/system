package com.gopher.system.model;

import org.springframework.data.annotation.Transient;

/**
 * 订单
 * @author dongyangyang
 *
 */
public class Order extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7935986002398694816L;
	
	/**
	 * 订单号 除id主键外 唯一标识
	 */
	private String number;
	/**
	 * 客户ID
	 */
	private int customerId;
	@Transient
	private String customerName;
	/**
	 * @see com.gopher.system.constant.State
	 * 状态 逻辑删除
	 */
	private int state;
	/**
	 * 备注留言
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
