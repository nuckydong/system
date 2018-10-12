package com.gopher.system.controller;

import com.gopher.system.controller.model.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class BaseController {
    @RequestMapping("/demo")
    public Result demo(){
        return new Result();
    }
}
