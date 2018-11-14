package com.gopher.system.service;

import com.gopher.system.model.CustomerUser;

public interface CustomerUserService{
	public Integer insert(CustomerUser t);
	
	public CustomerUser get(int userId);
	
	public void deleteByUser(int userId);

}
