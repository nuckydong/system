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
import com.gopher.system.model.vo.request.PriceCommodityPageRequest;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.service.PriceGroupService;

@RestController
@RequestMapping(path="/priceGroup")
public class PriceGroupController {
	@Autowired
	private PriceGroupService priceGroupService;
	
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute PriceGroupRequest priceGroupRequest){
		Result result = new Result();
		result.setData(priceGroupService.add(priceGroupRequest));
		return result;
	}
	
	@PostMapping(path="/update")
	public Result update(@ModelAttribute PriceGroupRequest priceGroupRequest){
		Result result = new Result();
		priceGroupService.update(priceGroupRequest);;
		return result;
	}
	@PostMapping(path="/delete")
	public Result delete(@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		priceGroupService.delete(id);
		return result;
	}
	
	@GetMapping(path="/get")
	public Result get(@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		result.setData(priceGroupService.get(id));
		return result;
	}
	
	@GetMapping(path="/export")
	public Result export(HttpServletResponse response,@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		priceGroupService.export(response,id);
		return result;
	}
	
	@GetMapping(path="/getPage")
	public Result getPage(PriceGroupPageRequst priceGroupPageRequst){
		Result result = new Result();
		result.setData(priceGroupService.getPage(priceGroupPageRequst));
		return result;
	}
	
	@GetMapping(path="/getCommodityPage")
	public Result getCommodityPage(@ModelAttribute PriceCommodityPageRequest priceCommodityPageRequest) {
		Result result = new Result();
		result.setData(priceGroupService.getCommodityPage(priceCommodityPageRequest));
		return result;
	}
	
	@GetMapping(path="/getList")
	public Result getList(PriceGroupPageRequst priceGroupPageRequst){
		Result result = new Result();
		result.setData(priceGroupService.getList(priceGroupPageRequst));
		return result;
	}


}
