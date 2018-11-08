package com.gopher.system.service.impl;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.constant.State;
import com.gopher.system.dao.mysql.CustomerDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.model.vo.request.CustomerPageRequst;
import com.gopher.system.model.vo.request.CustomerRequst;
import com.gopher.system.service.CustomerPriceService;
import com.gopher.system.service.CustomerService;
import com.gopher.system.util.Page;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private CustomerPriceService customerPriceService;

	@Override
	@Transactional
	public void add(CustomerRequst customerRequst) {
		if (null == customerRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String namme = customerRequst.getName();
		final String phone = customerRequst.getMobilePhone();
		if (!StringUtils.hasText(namme)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		if (!StringUtils.hasText(phone)) {
			throw new BusinessRuntimeException("电话不能为空");
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(customer, customer);
		customerDAO.insert(customer);
		final int priceGroupId = customerRequst.getPriceGroupId();
		if (priceGroupId >= 0) {
			final int customerId = customer.getId();
			CustomerPrice customerPrice = new CustomerPrice();
			customerPrice.setCustomerId(customerId);
			customerPrice.setPriceGroupId(priceGroupId);
			customerPriceService.add(customerPrice);
		}
	}

	@Override
	public Customer findByName(String customerName) {
		Customer customer = new Customer();
		customer.setName(customerName);
		return customerDAO.findOne(customer);
	}

	@Override
	public Customer findById(int customerId) {
		Customer customer = new Customer();
		customer.setId(customerId);
		return customerDAO.findOne(customer);
	}

	@Override
	public void update(CustomerRequst customerRequst) {
		if (null == customerRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int customerId = customerRequst.getId();
		final String namme = customerRequst.getName();
		final String phone = customerRequst.getMobilePhone();
		if (customerId <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		if (!StringUtils.hasText(namme)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		if (!StringUtils.hasText(phone)) {
			throw new BusinessRuntimeException("电话不能为空");
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(customer, customer);
		customerDAO.update(customer);
		final int priceGroupId = customerRequst.getPriceGroupId();
		CustomerPrice customerPriceDB = customerPriceService.getByCustomerId(customerId);
		if (null != customerPriceDB) {
			if (!Objects.equals(priceGroupId, customerPriceDB.getPriceGroupId())) {
				customerPriceService.delete(customerPriceDB.getId());
				if (priceGroupId >= 0) {
					CustomerPrice customerPrice = new CustomerPrice();
					customerPrice.setCustomerId(customerId);
					customerPrice.setPriceGroupId(priceGroupId);
					customerPriceService.add(customerPrice);
				}
			}
		}
	}

	@Override
	public void delete(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		Customer customer = new Customer();
		customer.setId(id);
		customer = customerDAO.findOne(customer);
		if(null != customer) {
			customer.setState(State.INVALID.getState());
			customerDAO.update(customer);
		}
	}

	@Override
	public Page<Customer> getPage(CustomerPageRequst customerPageRequst) {
		return null;
	}

}
