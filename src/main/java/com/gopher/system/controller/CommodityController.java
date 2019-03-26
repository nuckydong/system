package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.vo.request.CommodityListRequst;
import com.gopher.system.model.vo.request.CommodityPageRequst;
import com.gopher.system.service.CommodityService;

@RestController
@RequestMapping("/commodity")
public class CommodityController {
	@Autowired
	private CommodityService commodityService;
	
	@GetMapping(path="/getList")
	public Result getList(@ModelAttribute CommodityListRequst commodityListRequst) {
		Result result = new Result();
		result.setData(commodityService.getCommodityList(commodityListRequst));
		return result;
	}
	@GetMapping(path="/getPage")
	public Result getPage(@ModelAttribute CommodityPageRequst commodityPageRequst) {
		Result result = new Result();
		result.setData(commodityService.getCommodityPage(commodityPageRequst));
		return result;
	}
	@PostMapping(path="/add")
	public Result add(@ModelAttribute Commodity commodity) {
		Result result = new Result();
		result.setData(commodityService.insert(commodity));
		return result;
	} 
	
	@PostMapping(path="/update")
	public Result update(@ModelAttribute Commodity commodity) {
		Result result = new Result();
		commodityService.update(commodity);
		return result;
	}
	
	@PostMapping(path="/delete")
	public Result delete(@RequestParam(name = "id",defaultValue="0") int id) {
		Result result = new Result();
		commodityService.delete(id);
		return result;
	}
	
	@GetMapping(path="/getListNotInGroup")
	public Result getListNotInGroup(@ModelAttribute Commodity commodity) {
		Result result = new Result();
		result.setData(commodityService.getListNotInGroup(commodity));
		return result;
	}
	@GetMapping(path="/get")
	public Result get(@RequestParam(name = "id",defaultValue="0") int id) {
		Result result = new Result();
		result.setData(commodityService.getCommodity(id));
		return result;
	}
	
}
