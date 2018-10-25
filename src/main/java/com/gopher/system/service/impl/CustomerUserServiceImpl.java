package com.gopher.system.service.impl;

import java.util.List;

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
	public void update(CustomerUser t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer pk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerUser> findList(CustomerUser t) {
		return customerUserDAO.findList(t);
	}

	@Override
	public CustomerUser findOne(Integer pk) {
		return customerUserDAO.findOne(pk);
	}

}
