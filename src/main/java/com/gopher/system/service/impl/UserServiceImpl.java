package com.gopher.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopher.system.dao.mysql.UserDAO;
import com.gopher.system.model.User;
import com.gopher.system.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserDAO userDAO;
	@Override
	public Integer insert(User t) {
		return userDAO.insert(t);
	}

	@Override
	public void update(User t) {
		userDAO.update(t);
		
	}

	@Override
	public void delete(Integer pk) {
		userDAO.delete(pk);
	}
	

	@Override
	public List<User> findList(User t) {
		return userDAO.findList(t);
	}

	@Override
	public User findOne(Integer pk) {
		return userDAO.findOne(pk);
	}

	@Override
	public User findOne(String account) {
		User query = new User();
		query.setAccount(account);
		return userDAO.findOne(query);
	}

}
