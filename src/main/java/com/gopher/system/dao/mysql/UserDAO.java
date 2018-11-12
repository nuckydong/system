package com.gopher.system.dao.mysql;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.UserPageRequst;
@Repository
public interface UserDAO{
	
    Integer insert(User user);

    void update(User user);

    void delete(int id);
    
    List<User> findPage(UserPageRequst userPageRequst);
    
    int count(UserPageRequst userPageRequst);

    List<User> findList(User user);
    
    User findOne(User user);

}
