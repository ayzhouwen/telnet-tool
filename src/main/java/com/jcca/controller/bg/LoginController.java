package com.jcca.controller.bg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:
 * @Date: 2020/1/6
 * @Describe:
 */
@Controller
public class LoginController {
    @RequestMapping("/")
    public String login() {
        return "login";
    }
}
