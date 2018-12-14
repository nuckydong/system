package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.model.GroupCommodity;
import com.gopher.system.service.GroupCommodityService;

/**
 * 客户 自定义菜单下的商品接口
 * 
 * @author dongyangyang
 *
 */
@RestController
@RequestMapping(path="/groupCommodity")
public class GroupCommodityController {
	@Autowired
	private GroupCommodityService groupCommodityService;
	
	@PostMapping(path="/add")
	public Result add(@ModelAttribute GroupCommodity groupCommodity){
		Result result = new Result();
		groupCommodityService.insert(groupCommodity);
		return result;
	}
	@PostMapping(path="/update")
	public Result update(@ModelAttribute GroupCommodity groupCommodity){
		Result result = new Result();
		groupCommodityService.update(groupCommodity);
		return result;
	}
	@PostMapping(path="/delete")
	public Result delete(@RequestParam(name="id",defaultValue="0") int id ){
		Result result = new Result();
		groupCommodityService.delete(id);
		return result;
	}

}
