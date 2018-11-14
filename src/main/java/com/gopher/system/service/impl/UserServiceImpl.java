package com.gopher.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.UserDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.UserPageRequst;
import com.gopher.system.model.vo.response.UserResponse;
import com.gopher.system.service.CustomerService;
import com.gopher.system.service.CustomerUserService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.Page;
import com.gopher.system.util.ThreadLocalUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CustomerUserService customerUserService;
	@Autowired
	private CustomerService customerService;

	@Override
	@Transactional
	public void insert(User user) {
		if (user == null) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String account = user.getAccount();
		final String password = user.getPassword();
		final String name = user.getName();
		if (!StringUtils.hasText(account)) {
			throw new BusinessRuntimeException("账号不能为空");
		}
		if (!StringUtils.hasText(password)) {
			throw new BusinessRuntimeException("密码不能为空");
		}
		if (!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("用户名不能为空");
		}
		final int customerId = user.getCustomerId();
		if (customerId > 0) {
			user.setUserType(User.CUSTOMER);
		} else {
			user.setUserType(User.MANAGER);
		}
		user.setCreateUser(this.getCurrentUserId());
		user.setUpdateUser(this.getCurrentUserId());
		userDAO.insert(user);
		final int userId = user.getId();
		if (customerId > 0) {
			CustomerUser customerUser = new CustomerUser();
			customerUser.setCustomerId(customerId);
			customerUser.setUserId(userId);
			customerUserService.insert(customerUser);
		}
	}
    @Transactional
	@Override
	public void update(User user) {
		if (user == null) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int id = user.getId();
		final String account = user.getAccount();
		final String name = user.getName();
		if (id <= 0) {
			throw new BusinessRuntimeException("ID不能都为空");
		}
		User userDB = null;
		User query = new User();
		query.setId(id);
		userDB = userDAO.findOne(query);
		if (null == userDB) {
			throw new BusinessRuntimeException("根据ID找不到对应得用户");
		}
		if (!StringUtils.hasText(account)) {
			throw new BusinessRuntimeException("账号不能为空");
		}
		if (!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("用户名不能为空");
		}
		final int customerId = user.getCustomerId();
		if (customerId > 0) {
			customerUserService.deleteByUser(id);
			CustomerUser customerUser = new CustomerUser();
			customerUser.setCustomerId(customerId);
			customerUser.setUserId(id);
			customerUserService.insert(customerUser);
			user.setUserType(User.CUSTOMER);
		} else {
			user.setUserType(User.MANAGER);
		}
		user.setUpdateUser(this.getCurrentUserId());
		userDAO.update(user);
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
		if (null != obj) {
			user_id = Integer.valueOf(obj.toString());
		}
		User query = new User();
		query.setId(user_id);
		return userDAO.findOne(query);
	}

	@Override
	public int getCurrentUserId() {
		Object obj = ThreadLocalUtils.getObject(ThreadLocalUtils.USER_KEY);
		if (null == obj) {
			throw new BusinessRuntimeException("您还没有登录,或者会话已经过期,请重新登录后再试");
		}
		int user_id = Integer.valueOf(obj.toString());
		return user_id;
	}

	@Override
	public void delete(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		userDAO.delete(id);
	}

	private Customer getCustomerByUserId(int userId) {
		Customer result = null;
		CustomerUser customerUser = customerUserService.get(userId);
		if (null != customerUser) {
			result = customerService.findById(customerUser.getCustomerId());
		}
		return result;
	}

	@Override
	public UserResponse getUserDetail(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		User user = new User();
		user.setId(id);
		user = userDAO.findOne(user);
		if (user == null) {
			throw new BusinessRuntimeException("根据ID找不到记录");
		}
		UserResponse result = new UserResponse();
		BeanUtils.copyProperties(user, result);
		Customer customer = getCustomerByUserId(id);
		if (customer != null) {
			result.setCustomerId(customer.getId());
			result.setCustomerName(customer.getName());
		}
		return result;
	}

	@Override
	public Page<UserResponse> getPage(UserPageRequst userPageRequst) {
		userPageRequst.setCurrentLoginUser(this.getCurrentUserId());
		List<User> list = userDAO.findPage(userPageRequst);
		final int totalCount = userDAO.count(userPageRequst);
		List<UserResponse> li = null;
		if (null != list && !list.isEmpty()) {
			li = new ArrayList<>(list.size());
			for (User user : list) {
				UserResponse rsp = new UserResponse();
				BeanUtils.copyProperties(user, rsp);
				Customer customer = this.getCustomerByUserId(user.getId());
				if (null != customer) {
					rsp.setCustomerId(customer.getId());
					rsp.setCustomerName(customer.getName());
				}
				li.add(rsp);
			}
		}
		Page<UserResponse> result = new Page<UserResponse>();
		result.setList(li);
		result.setTotalCount(totalCount);
		return result;
	}

}
