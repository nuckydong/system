package com.gopher.system.dao.mysql;

import org.springframework.stereotype.Repository;

import com.gopher.system.model.User;
@Repository
public interface UserDAO {
	
	void update(User user);
	
	void insert(User user);
	
	User findOne(User user);
	
	

}
