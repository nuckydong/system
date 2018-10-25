package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CustomerDAO;
import com.gopher.system.model.Customer;
import com.gopher.system.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public Integer insert(Customer t) {
		return customerDAO.insert(t);
	}

	@Override
	public void update(Customer t) {
		customerDAO.update(t);
	}

	@Override
	public void delete(Integer pk) {
		customerDAO.delete(pk);
	}

	@Override
	public List<Customer> findList(Customer t) {
		return customerDAO.findList(t);
	}

	@Override
	public Customer findOne(Integer pk) {
		return customerDAO.findOne(pk);
	}

	@Override
	public Customer findByName(String customerName) {
		Customer customer = new Customer();
		customer.setName(customerName);
		return customerDAO.findOne(customer);
	}

}
