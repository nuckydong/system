package com.gopher.system.service;

import com.gopher.system.model.Customer;

public interface CustomerService{
	
	public Integer insert(Customer t);
	
	public Customer findByName(final String customerName);
	
	public Customer findById(final int customerId);

}
