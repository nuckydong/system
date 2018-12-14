package com.gopher.system.model.vo.request;

import com.gopher.system.model.Order;
import com.gopher.system.util.Page;

public class OrderPageRequst extends Page<Order> {

	private int customerId;

	private long startTime;

	private long endTime;

	private String number;

	private int change;
	
	private int send;

	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getSend() {
		return send;
	}

	public void setSend(int send) {
		this.send = send;
	}

}
