package com.gopher.system.service;

import com.gopher.system.model.CustomerPrice;

public interface CustomerPriceService {
	/**
	 * 根据客户id 获取当前的定价号
	 * @param customerId
	 * @return
	 */
	public String getPriceNumberByCustomerId(int customerId);
	
	public CustomerPrice getByCustomerId(int customerId);
	
	public CustomerPrice get(int id);
	
	public void add(CustomerPrice customerPrice);
	
	public void delete(int id);
	
	public void update(CustomerPrice customerPrice);

}
