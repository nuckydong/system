package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.constant.State;
import com.gopher.system.dao.mysql.CustomerDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerPrice;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.CustomerPageRequst;
import com.gopher.system.model.vo.request.CustomerRequst;
import com.gopher.system.model.vo.response.CustomerResponse;
import com.gopher.system.service.CustomerPriceService;
import com.gopher.system.service.CustomerService;
import com.gopher.system.service.OrderService;
import com.gopher.system.service.PriceGroupService;
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
		BeanUtils.copyProperties(customerRequst, customer);
		customerDAO.insert(customer);
		final int priceGroupId = customerRequst.getPriceGroupId();
		if (priceGroupId > 0) {
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
	public CustomerResponse findById2(int customerId) {
		Customer customer = new Customer();
		customer.setId(customerId);
		customer = customerDAO.findOne(customer);
		CustomerResponse result = null;
		if(null != customer) {
			result = new CustomerResponse();
			BeanUtils.copyProperties(customer, result);
			PriceGroup pg = getPriceGroupByCustomerId(customer.getId());
			if(pg != null ) {
				result.setPriceGroupId(pg.getId());
				result.setPriceGroupName(pg.getName());
				result.setPriceGroupNumber(pg.getNumber());
			}
		}
		return result;
	}
	@Override
	public Customer findById(int customerId) {
		Customer customer = new Customer();
		customer.setId(customerId);
		customer = customerDAO.findOne(customer);
		return customer;
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
		BeanUtils.copyProperties(customerRequst, customer);
		customerDAO.update(customer);
		final int priceGroupId = customerRequst.getPriceGroupId();
		CustomerPrice customerPriceDB = customerPriceService.getByCustomerId(customerId);
		if (null != customerPriceDB) {
			if (!Objects.equals(priceGroupId, customerPriceDB.getPriceGroupId())) {
				customerPriceService.delete(customerPriceDB.getId());
				if (priceGroupId > 0) {
					CustomerPrice customerPrice = new CustomerPrice();
					customerPrice.setCustomerId(customerId);
					customerPrice.setPriceGroupId(priceGroupId);
					customerPriceService.add(customerPrice);
				}
			}
		}
	}
    @Autowired
	private OrderService orderService;
    
	@Override
	@Transactional
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
		orderService.deleteByCustomerId(id);
	}
	@Autowired
	private PriceGroupService priceGroupService;
	
	private PriceGroup getPriceGroupByCustomerId(int customerId) {
		CustomerPrice customerPrice = customerPriceService.getByCustomerId(customerId);
		PriceGroup result = null;
		if(null != customerPrice) {
			result = priceGroupService.getPriceGroup(customerPrice.getPriceGroupId());
		}
		return result;
	}
	@Override
	public Page<CustomerResponse> getPage(CustomerPageRequst customerPageRequst) {
		List<Customer> list =customerDAO.findPage(customerPageRequst);
		final int totalCount = customerDAO.count(customerPageRequst);
		Page<CustomerResponse> result= new Page<CustomerResponse>();
		List<CustomerResponse> li = null; 
		if(null != list) {
			li = new ArrayList<>(list.size());
			for (Customer customer : list) {
				CustomerResponse rsp = new CustomerResponse();
				BeanUtils.copyProperties(customer, rsp);
				PriceGroup pg = getPriceGroupByCustomerId(customer.getId());
				if(pg != null ) {
					rsp.setPriceGroupId(pg.getId());
					rsp.setPriceGroupName(pg.getName());
					rsp.setPriceGroupNumber(pg.getNumber());
				}
				System.out.println(JSON.toJSONString(rsp));
				li.add(rsp);
			}
			result.setList(li);
		}
		result.setTotalCount(totalCount);
		result.setPageNumber(customerPageRequst.getPageNumber());
		result.setPageSize(customerPageRequst.getPageSize());
		return result;
	}

}
