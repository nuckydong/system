package com.gopher.system.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.service.OrderService;

@RestController
@RequestMapping(path="/order")
public class OrderController{
	@Autowired
	private OrderService orderService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute OrderRequst orderRequst) {
		Result result = new Result();
		orderService.insert(orderRequst);
		return result;
	}
	
	@PostMapping(path="/update")
	public Result update(@ModelAttribute OrderRequst orderRequst) {
		Result result = new Result();
		result.setData(orderService.updateOrder(orderRequst));
		return result;
	}
	
	@PostMapping(path="/delete")
	public Result delete(@RequestParam(name="id",defaultValue="0") int id) {
		Result result = new Result();
		orderService.deleteOrder(id);
		return result;
	}
	
	@PostMapping(path="/send")
	public Result send(@RequestParam(name="id",defaultValue="0") int id) {
		Result result = new Result();
		orderService.sending(id);
		return result;
	}
	
	@GetMapping(path="/getDetail")
	public Result getDetail(@RequestParam(name="id",defaultValue="0") int id) {
		Result result = new Result();
		result.setData(orderService.getOrderDetail(id));
		return result;
	}
	
	@GetMapping(path="/export")
	public Result export(@RequestParam(name="id",defaultValue="0") int id,HttpServletResponse response) {
		Result result = new Result();
		orderService.exportOrder(id, response);
		return result;
	}
	@GetMapping(path="/getList")
	public Result getList() {
		Result result = new Result();
		result.setData(orderService.getOrderListByCurrentUser());
		return result;
	}
	
	@GetMapping(path="/getPage")
	public Result getPage(@ModelAttribute OrderPageRequst orderPageRequst) {
		Result result = new Result();
		result.setData(orderService.getOrderPage(orderPageRequst));
		return result;
	}
}
