package com.gopher.system.dao.mysql;

import com.gopher.system.model.Customer;

public interface CustomerDAO {
	
	public Customer findOne(Customer customer);
	
	public void update(Customer customer);
	
	public void insert(Customer customer);

}
