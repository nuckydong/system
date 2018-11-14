package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.CustomerUserDAO;
import com.gopher.system.exception.BusinessRuntimeException;
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
		if(userId <=0){
			throw new BusinessRuntimeException("无效的用户ID");
		}
		CustomerUser userCustomer = new CustomerUser();
		userCustomer.setUserId(userId);
		return customerUserDAO.findOne(userCustomer);
	}

	@Override
	public void deleteByUser(int userId) {
		if(userId <=0){
			throw new BusinessRuntimeException("无效的用户ID");
		}
		CustomerUser userCustomer = new CustomerUser();
		userCustomer.setUserId(userId);
		customerUserDAO.delete(userCustomer);
	}


}
