package com.gopher.system.service;

import com.gopher.system.model.CustomerPrice;

public interface CustomerPriceService {
	/**
	 * 根据客户id 获取当前的定价号
	 * @param customerId
	 * @return
	 */
	public String getPriceNumberByCustomerId(int customerId);
	
	public CustomerPrice get(CustomerPrice customerPrice);
	
	public void add(CustomerPrice customerPrice);
	
	public void delete(CustomerPrice customerPrice);
	
	public void update(CustomerPrice customerPrice);

}
