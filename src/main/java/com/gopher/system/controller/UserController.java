package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.UserPageRequst;
import com.gopher.system.service.UserService;

@RestController
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute User user){
		Result result = new Result();
		userService.insert(user);
		return result;
	}
	@GetMapping(path="/getPage")
	public Result getPage(@ModelAttribute UserPageRequst userPageRequst){
		Result result = new Result();
		result.setData(userService.getPage(userPageRequst));
		return result;
	}
	@GetMapping(path="/get")
	public Result get(@RequestParam (name = "id",defaultValue ="0") int id){
		Result result = new Result();
		result.setData(userService.getUserDetail(id));
		return result;
	}
	@PostMapping(path="/update")
	public Result update(@ModelAttribute User user){
		Result result = new Result();
		userService.update(user);
		return result;
	}
	
	@PostMapping(path="/delete")
	public Result update(@RequestParam (name = "id",defaultValue ="0") int id){
		Result result = new Result();
		userService.delete(id);
		return result;
	}
	
	
	
	
	
}
