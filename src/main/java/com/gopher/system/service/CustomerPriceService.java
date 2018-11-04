package com.gopher.system.service;

import com.gopher.system.model.CustomerPrice;

public interface CustomerPriceService {
	
	public CustomerPrice get(CustomerPrice customerPrice);
	
	public void add(CustomerPrice customerPrice);
	
	public void delete(CustomerPrice customerPrice);
	
	public void update(CustomerPrice customerPrice);

}
