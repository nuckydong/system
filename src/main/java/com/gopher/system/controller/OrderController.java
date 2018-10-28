package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.service.OrderService;

@RestController
@RequestMapping(path="/order")
public class OrderController{
	@Autowired
	private OrderService orderService;
	
	@PostMapping(path="/add")
	public Result getList(@ModelAttribute OrderRequst orderRequst) {
		Result result = new Result();
		orderService.insert(orderRequst);
		return result;
	}
	
	@GetMapping(path="/getDetail")
	public Result getList(@RequestParam(name="id",defaultValue="0") int id) {
		Result result = new Result();
		result.setData(orderService.getOrderDetail(id));
		return result;
	}
	
	@GetMapping(path="/getList")
	public Result getList() {
		Result result = new Result();
		result.setData(orderService.getOrderListByCurrentUser());
		return result;
	}

}
