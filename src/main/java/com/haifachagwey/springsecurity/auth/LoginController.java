package com.haifachagwey.springsecurity.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("sign-in")
    public String login() {
        return "login";
    }

    @GetMapping("dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
