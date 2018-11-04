package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.Page;
import com.gopher.system.model.PriceGroup;
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
		priceGroupService.add(priceGroupRequest);;
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
	
	@GetMapping(path="/getPage")
	public Result getPage(Page<PriceGroup> page){
		Result result = new Result();
		result.setData(priceGroupService.getPage(page));
		return result;
	}


}
