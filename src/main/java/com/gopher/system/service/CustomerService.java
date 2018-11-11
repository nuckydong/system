package com.gopher.system.service;

import com.gopher.system.model.Customer;
import com.gopher.system.model.vo.request.CustomerPageRequst;
import com.gopher.system.model.vo.request.CustomerRequst;
import com.gopher.system.model.vo.response.CustomerResponse;
import com.gopher.system.util.Page;

public interface CustomerService{
	
	public void add(CustomerRequst customerRequst);
	
	public void update(CustomerRequst customerRequst);
	
	public void delete(final int id);
	
	public Page<CustomerResponse> getPage(CustomerPageRequst customerPageRequst);
	
	public Customer findByName(final String customerName);
	
	public CustomerResponse findById(final int customerId);

}
