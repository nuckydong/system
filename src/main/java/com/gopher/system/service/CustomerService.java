package com.gopher.system.service;

import com.gopher.system.model.Customer;

public interface CustomerService extends BaseService<Customer,Integer>{
	
	public Customer findByName(final String customerName);

}
