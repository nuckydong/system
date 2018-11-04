package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CustomerPriceDAO;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.service.CustomerPriceService;
@Service
public class CustomerPriceServiceImpl implements CustomerPriceService{
	@Autowired
    private CustomerPriceDAO customerPriceDAO;

	@Override
	public CustomerPrice get(CustomerPrice customerPrice) {
		
		return customerPriceDAO.findOne(customerPrice);
	}

	@Override
	public void add(CustomerPrice customerPrice) {
		customerPriceDAO.insert(customerPrice);
	}

	@Override
	public void delete(CustomerPrice customerPrice) {
		customerPriceDAO.delete(customerPrice);
	}

	@Override
	public void update(CustomerPrice customerPrice) {
		 customerPriceDAO.update(customerPrice);
	}

}
