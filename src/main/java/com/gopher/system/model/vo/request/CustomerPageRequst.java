package com.gopher.system.model.vo.request;

import com.gopher.system.model.Customer;
import com.gopher.system.util.Page;

public class CustomerPageRequst extends Page<Customer>{
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
