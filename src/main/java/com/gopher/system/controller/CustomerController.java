package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.CustomerPageRequst;
import com.gopher.system.model.vo.request.CustomerRequst;
import com.gopher.system.service.CustomerService;

@RestController
@RequestMapping(path="/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute CustomerRequst customer){
		Result result = new Result();
		customerService.add(customer);;
		return result;
	}
	@PostMapping(path="/update")
	public Result update(@ModelAttribute CustomerRequst customerPrice){
		Result result = new Result();
		customerService.update(customerPrice);;
		return result;
	}
	@PostMapping(path="/delete")
	public Result delete(@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		customerService.delete(id);
		return result;
	}
	
	@GetMapping(path="/get")
	public Result add(@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		result.setData(customerService.findById2(id));
		return result;
	}
	
	@GetMapping(path="/getPage")
	public Result getPage(@ModelAttribute CustomerPageRequst customerPageRequst){
		Result result = new Result();
		result.setData(customerService.getPage(customerPageRequst));
		return result;
	}

}
