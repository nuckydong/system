package com.gopher.system.dao.mysql;

import java.util.List;

import com.gopher.system.model.CustomerPrice;
import com.gopher.system.model.Page;

public interface CustomerPriceDAO {
	
	public void insert(CustomerPrice customerPrice);
	
	public void update (CustomerPrice customerPrice);
	
	public void delete (CustomerPrice customerPrice);
	
	public CustomerPrice findOne(CustomerPrice customerPrice);
	
	public int count(Page<CustomerPrice> page);
	
	public List<CustomerPrice> findList(Page<CustomerPrice> page);

}
