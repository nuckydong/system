package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.CustomerCommodityGroupRequset;
import com.gopher.system.model.vo.request.GroupCommodityPageRequst;
import com.gopher.system.service.CustomerCommodityGroupService;

@RestController
@RequestMapping(path="/customerCommodityGroup")
public class CustomerCommodityGroupController {
	@Autowired
	private CustomerCommodityGroupService customerCommodityGroupService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute CustomerCommodityGroupRequset customerCommodityGroupRequset) {
		customerCommodityGroupService.add(customerCommodityGroupRequset);
		return new Result();
	}
	@PostMapping(path="/update")
	public Result update(@ModelAttribute CustomerCommodityGroupRequset customerCommodityGroupRequset) {
		customerCommodityGroupService.update(customerCommodityGroupRequset);
		return new Result();
	}
	
	@PostMapping(path="/delete")
	public Result update(@RequestParam (name = "id",defaultValue = "0") int id) {
		customerCommodityGroupService.delete(id);
		return new Result();
	}
	
	@GetMapping(path="/get")
	public Result get(@RequestParam (name = "id",defaultValue = "0") int id) {
		Result result = new Result();
		result.setData(customerCommodityGroupService.get(id));
		return result;
	}
	// TODO 前端给传的是id
	@GetMapping(path="/getList")
	public Result getList(@RequestParam (name = "id",defaultValue = "0") int customerId) {
		Result result = new Result();
		result.setData(customerCommodityGroupService.getList(customerId));
		return result;
	}
	
	@GetMapping(path="/getCommodityPage")
	public Result getCommodityPage(@ModelAttribute GroupCommodityPageRequst groupCommodityPageRequst ) {
		Result result = new Result();
		result.setData(customerCommodityGroupService.getCommodityPage(groupCommodityPageRequst));
		return result;
	}
	@GetMapping(path="/getListByCustomerLoginUser")
	public Result getListByCustomerUser() {
		Result result = new Result();
		result.setData(customerCommodityGroupService.getList());
		return result;
	}


}
