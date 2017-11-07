package com.taotao.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by myname on 2017/9/26.
 * 请求跳转页面
 */
@Controller
public class PageForwardController {

    @GetMapping("/page/{viewName}")
    public String forward(@PathVariable("viewName") String viewName){
        return viewName;
    }
}
