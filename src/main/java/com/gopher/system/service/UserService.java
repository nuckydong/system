package com.gopher.system.service;

import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.UserPageRequst;
import com.gopher.system.model.vo.response.UserResponse;
import com.gopher.system.util.Page;

public interface UserService {
	
	public void insert(User user);
	
	public void update(User user);
	
	public User getUserByAccount(final String account);
	
	public User getCurrentUser();
	
	public User getUerById(int id);

	public int getCurrentUserId();
	
	public void delete(int id);
	
	public UserResponse getUserDetail(final int id);
	
	Page<UserResponse> getPage(UserPageRequst userPageRequst);
}
