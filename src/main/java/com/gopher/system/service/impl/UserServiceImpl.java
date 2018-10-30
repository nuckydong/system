package com.gopher.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.UserDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.User;
import com.gopher.system.service.UserService;
import com.gopher.system.util.ThreadLocalUtils;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserDAO userDAO;
	@Override
	public Integer insert(User t) {
		return userDAO.insert(t);
	}
	@Override
	public User getUserByAccount(String account) {
		User query = new User();
		query.setAccount(account);
		return userDAO.findOne(query);
	}
	@Override
	public User getUerById(int id) {
		User query = new User();
		query.setId(id);
		return userDAO.findOne(query);
	}
	@Override
	public User getCurrentUser() {
		Object obj = ThreadLocalUtils.getObject(ThreadLocalUtils.USER_KEY);
		int user_id = 0;
		if(null !=obj) {
			user_id = Integer.valueOf(obj.toString());
		}
		User query = new User();
		query.setId(user_id);
		return userDAO.findOne(query);
	}
	

	@Override
	public int getCurrentUserId() {
		Object obj = ThreadLocalUtils.getObject(ThreadLocalUtils.USER_KEY);
		if(null == obj){
			throw new BusinessRuntimeException("您还没有登录,或者会话已经过期,请重新登录后再试");
		}
		int user_id = Integer.valueOf(obj.toString());
		return user_id;
	}

	
	

}
