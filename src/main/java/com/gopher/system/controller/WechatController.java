package com.gopher.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gopher.system.controller.model.Result;
import com.gopher.system.service.WechatService;

@RestController
@RequestMapping(path="/wechat")
public class WechatController  {
	@Autowired
	private WechatService wechatService;
	
	@PostMapping(path="/getSession")
	public Result wechatLogin(@RequestParam(name="code") String code) {
		Result result = new Result();
		result.setData(wechatService.getSession(code));
		return result;
	}

}
