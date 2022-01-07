package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.data.Page;
import com.metzm.cartprojectmargie2.models.data.PageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    private PageRepository pageRepo;

    public AdminPagesController(PageRepository pageRepo) {
        this.pageRepo = pageRepo;

    }


    @GetMapping
    public String index(Model model) {
    List<Page> pages = pageRepo.findAll();

    model.addAttribute("pages",pages);

        return "admin/pages/index";
    }

//    @GetMapping("/add")
//    public String add (@ModelAttribute Page page) {
//
//     //   model.addAttribute("page", new Page());
//        return "admin/pages/index";
//    }
    @GetMapping("/add")
    public String add (Model model) {

        model.addAttribute("page", new Page());
        return "admin/pages/add";
    }

}