package com.gopher.system.service;

import com.gopher.system.model.User;

public interface UserService {
	
	public Integer insert(User user);
	
	public User findOne(final String account);
	
	public User getCurrentUser();

	int getCurrentUserId();
}
