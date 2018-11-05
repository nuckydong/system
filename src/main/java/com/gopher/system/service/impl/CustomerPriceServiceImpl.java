package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CustomerPriceDAO;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.service.CustomerPriceService;
import com.gopher.system.service.PriceGroupService;
@Service
public class CustomerPriceServiceImpl implements CustomerPriceService{
	@Autowired
    private CustomerPriceDAO customerPriceDAO;
	@Autowired
	private PriceGroupService priceGroupService;

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

	@Override
	public String getPriceNumberByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		CustomerPrice customerPrice = new CustomerPrice();
		customerPrice.setCustomerId(customerId);
		CustomerPrice customerPriceDB = customerPriceDAO.findOne(customerPrice);
		 String number = "";
		if(customerPriceDB != null) {
			PriceGroup priceGroup = priceGroupService.getPriceGroup(customerPriceDB.getPriceGroupId());
			if(null != priceGroup) {
				number = priceGroup.getNumber();
			}
		}
		return number;
	}

}
