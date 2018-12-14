package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.service.CommodityPriceService;

@RestController
@RequestMapping("/commodityPrice")
public class CommodityPriceController {
	@Autowired
	private CommodityPriceService commodityPriceService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute CommodityPrice commodityPrice) {
		Result result = new Result();
		commodityPriceService.add(commodityPrice);
		return result;
	} 
	
	@PostMapping(path="/delete")
	public Result delete(@ModelAttribute CommodityPrice commodityPrice) {
		Result result = new Result();
		commodityPriceService.delete(commodityPrice);
		return result;
	} 

}
