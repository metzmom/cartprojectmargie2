package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.data.Page;
import com.metzm.cartprojectmargie2.models.data.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {

    @Autowired
    private PageRepository pageRepo;

//    public AdminPagesController(PageRepository pageRepo) {
//        this.pageRepo = pageRepo;

  //  }  autowired replaced this


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


//to redirect for errors

    @PostMapping("/add")
    public String add (@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if (bindingResult.hasErrors()) {
            return "admin/pages/add";  //contains error messages
        }

        redirectAttributes.addFlashAttribute("message", "PAGE added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

// this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
        String slug = page.getSlug() =="" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ","-");


   // this will check to make sure only one slug exists because they are URL
        Page slugExists = pageRepo.findBySlug(slug);

        if ( slugExists != null) {
//if slug doesnt exist  danger
            redirectAttributes.addFlashAttribute("message", "slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            page.setSlug(slug);
            page.setSorting(100);//sorts to last page of data

            pageRepo.save(page);//save the changes

        }
        return "redirect:/admin/pages/add"; //return back to the page
    }

}