package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by myname on 2017/10/1.
 */
@Controller
public class IndexController {

    //跳转到门户网站的首页
    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        return "index";
    }

    public static void main(String[] args) {
        int count = 0;

        for (int i = 1; i <= 200; i++) {
            boolean b = false;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    b = false;
                    break;
                } else
                    b = true;

            }
            if (b == true) {
                count++;
                System.out.println("素数为" + i);
            }
        }



        String s1 = "你好,";
        String s2 = "世界!";
        String s3 = s1 + s2;
        System.out.println(s3);

    }
}


