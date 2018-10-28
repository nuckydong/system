package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CustomerUserDAO;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.service.CustomerUserService;
@Service
public class CustomerUserServiceImpl implements CustomerUserService{
	@Autowired
	private CustomerUserDAO customerUserDAO;

	@Override
	public Integer insert(CustomerUser t) {
		return customerUserDAO.insert(t);
	}

	@Override
	public CustomerUser get(int userId) {
		CustomerUser userCustomer = new CustomerUser();
		userCustomer.setUserId(userId);
		return customerUserDAO.findOne(userCustomer);
	}


}
