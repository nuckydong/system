package com.gopher.system.dao.mysql;

import com.gopher.system.model.CustomerPrice;
import com.gopher.system.model.Page;

public interface CustomerPriceDAO {
	
	public void insert(CustomerPrice customerPrice);
	
	public void update (CustomerPrice customerPrice);
	
	public CustomerPrice get(CustomerPrice customerPrice);
	
	public Page<CustomerPrice> getPage(Page<CustomerPrice> page);

}
