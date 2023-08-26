package com.pp.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 18:07
 */
@Controller
@RequestMapping("/alpha")
public class HelloController {
    @RequestMapping("hello")
    @ResponseBody// 返回JSON格式的数据
    public String hello(){
        return "Hello Spring Boot";
    }
}
