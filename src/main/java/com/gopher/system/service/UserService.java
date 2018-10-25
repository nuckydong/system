package com.gopher.system.service;

import com.gopher.system.model.User;

public interface UserService extends BaseService<User,Integer> {
	public User findOne(final String account);
}
