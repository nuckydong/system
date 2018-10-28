package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.vo.request.CommodityListRequst;
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

}
