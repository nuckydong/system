package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.service.CommodityTypeService;

@RestController
@RequestMapping(path="/commodityType")
public class CommodityTypeController {
	@Autowired
	private CommodityTypeService commodityTypeService;
	@GetMapping(path="/getList")
	public Result getList(@RequestParam (name = "name",defaultValue="") String name) {
		Result result = new Result();
		result.setData(commodityTypeService.getCommodityTypeList(name));
		return result;
	}

}
