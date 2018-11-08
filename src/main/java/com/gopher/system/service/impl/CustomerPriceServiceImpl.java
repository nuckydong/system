package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CustomerPriceDAO;
import com.gopher.system.exception.BusinessRuntimeException;
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
	public CustomerPrice get(int id) {
		CustomerPrice customerPrice = new CustomerPrice();
		customerPrice.setId(id);
		return customerPriceDAO.findOne(customerPrice);
	}

	@Override
	public void add(CustomerPrice customerPrice) {
		if(customerPrice == null) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int customerId = customerPrice.getCustomerId();
		final int prcieGroupId = customerPrice.getPriceGroupId();
		if(customerId <= 0) {
			throw new BusinessRuntimeException("无效的客户ID");
		}
		if(prcieGroupId <= 0) {
			throw new BusinessRuntimeException("无效的价格表ID");
		}
		customerPriceDAO.insert(customerPrice);
	}

	@Override
	public void delete(int id) {
		CustomerPrice customerPrice = new CustomerPrice();
		customerPrice.setId(id);
		customerPriceDAO.delete(customerPrice);
	}

	@Override
	public void update(CustomerPrice customerPrice) {
		if(customerPrice == null) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		if(customerPrice.getId() <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		final int customerId = customerPrice.getCustomerId();
		final int prcieGroupId = customerPrice.getPriceGroupId();
		if(customerId <= 0) {
			throw new BusinessRuntimeException("无效的客户ID");
		}
		if(prcieGroupId <= 0) {
			throw new BusinessRuntimeException("无效的价格表ID");
		}
		 customerPriceDAO.update(customerPrice);
	}

	@Override
	public String getPriceNumberByCustomerId(int customerId) {
		CustomerPrice customerPriceDB = this.getByCustomerId(customerId);
		 String number = "";
		if(customerPriceDB != null) {
			PriceGroup priceGroup = priceGroupService.getPriceGroup(customerPriceDB.getPriceGroupId());
			if(null != priceGroup) {
				number = priceGroup.getNumber();
			}
		}
		return number;
	}

	@Override
	public CustomerPrice getByCustomerId(int customerId) {
		CustomerPrice customerPrice = new CustomerPrice();
		customerPrice.setCustomerId(customerId);
		CustomerPrice customerPriceDB = customerPriceDAO.findOne(customerPrice);
		return customerPriceDB;
	}

}
