package com.gopher.system.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.dao.mysql.CustomerDAO;
import com.gopher.system.dao.mysql.UserDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.LoginRequst;
import com.gopher.system.model.vo.request.LogoutRequst;
import com.gopher.system.model.vo.request.RegisterRequst;
import com.gopher.system.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CustomerDAO customerDAO;
    /**
     * 此注册为商户注册,需要添加一条用户信息(标注为商户,只允许登录APP)
     * 添加一条商户信息,如果存在则不添加
     */
	@Transactional
	@Override
	public void register(RegisterRequst registerRequst) {
		if(null == registerRequst){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String account = registerRequst.getAccount();
		final String password = registerRequst.getPassword();
		final String company = registerRequst.getCompany();
		final String phone = registerRequst.getPhone();
		
		if(!StringUtils.hasText(account)){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		if(!StringUtils.hasText(password)){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		if(!StringUtils.hasText(company)){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		if(!StringUtils.hasText(phone)){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		
		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		user.setPhone(phone);
		//TODO 
		user.setWechat("TEST");
		Customer customer = new Customer();
		customer.setName(company);
		Customer customerDB  = customerDAO.findOne(customer);
		if(null == customerDB){
			customer.setMobilePhone(phone);
			//初始一个 客户
			customerDAO.insert(customer);;
		}
		user.setUserType(User.CUSTOMER);
		User userDB = userDAO.findOne(user);
		if(null != userDB){
			throw new BusinessRuntimeException("当前用户已经存在,请重新输入账号");
		}
		userDAO.insert(user);
	}

	@Override
	public void login(LoginRequst loginRequest) {
		if(null == loginRequest){
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String account = loginRequest.getAccount();
		final String password = loginRequest.getPassword();
		User user = new User();
		user.setAccount(account);
		User userDB = userDAO.findOne(user);
		if(null == userDB){
			throw new BusinessRuntimeException("无效的账号,请检查账号后重新登录");
		}
		final String passwordDB = userDB.getPassword();
		if(!Objects.equals(password, passwordDB)){
			throw new BusinessRuntimeException("账号或密码错误,请检查账号密码后重新登录");
		}
	}

	@Override
	public void logout(LogoutRequst logoutRequst) {
		
	}

}
