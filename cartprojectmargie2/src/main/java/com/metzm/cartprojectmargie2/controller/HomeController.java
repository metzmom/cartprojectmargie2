package com.metzm.cartprojectmargie2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//just returns a view of home
public class HomeController {
    @GetMapping("/arandompage")
    public String home(){
        return "home";
    }

}
