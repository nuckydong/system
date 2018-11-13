package com.gopher.system.model.vo.request;

import com.gopher.system.model.Order;
import com.gopher.system.util.Page;

public class OrderPageRequst extends Page<Order> {
	
      private int customerId;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
      
}
