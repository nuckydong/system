package com.gopher.system.service;

import com.gopher.system.model.User;

public interface UserService {
	
	public Integer insert(User user);
	
	public User getUserByAccount(final String account);
	
	public User getCurrentUser();
	
	public User getUerById(int id);

	int getCurrentUserId();
}
