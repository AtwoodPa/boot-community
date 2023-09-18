package com.pp.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 * TODO 异步Controller
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/9/18 17:06
 */
@Controller
@RequestMapping("async")
public class CallableController {

    /**
     * 返回结果为java.util.concurrent.Callable即为异步Controller
     * @return
     */
    @RequestMapping("/testAsync")
    @ResponseBody
    public Callable<String> callable() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "Async Controller Result";
            }
        };
    }
}
