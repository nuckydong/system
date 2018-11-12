package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.LoginRequst;
import com.gopher.system.model.vo.request.LogoutRequst;
import com.gopher.system.model.vo.request.RegisterRequst;
import com.gopher.system.service.AuthService;
@RestController
@RequestMapping(path="/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@RequestMapping(path="/register")
	public Result register(@ModelAttribute RegisterRequst registerRequst){
		authService.register(registerRequst);
		return new Result();
	}
	
	@PostMapping(path="/login")
	public Result login(@ModelAttribute LoginRequst loginRequst){
		Result result = new Result();
		result.setData(authService.login(loginRequst));
		return result;
	}
	
	@PostMapping(path="/logout")
	public Result logout(@ModelAttribute LogoutRequst logoutRequest) {
		Result result = new Result();
		authService.logout(logoutRequest);
		return result;
	}
	
	

}
