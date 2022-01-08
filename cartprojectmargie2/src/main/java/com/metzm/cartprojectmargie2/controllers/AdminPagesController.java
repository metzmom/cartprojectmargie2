package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.data.Page;
import com.metzm.cartprojectmargie2.models.data.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        Page slugExists = pageRepo.findBySlug(page.getId(), slug);

        if ( slugExists != null) {
//if slug doesnt exist  danger
            redirectAttributes.addFlashAttribute("message", "slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);//makes sure input which causes errors sticks

        } else {
            page.setSlug(slug);
            page.setSorting(100);//sorts to last page of data

            pageRepo.save(page);//save the changes

        }
        return "redirect:/admin/pages/add"; //return to the page

    }
        @GetMapping("/edit/{id}")
        public String edit (@PathVariable int id, Model model) {

            Page page = pageRepo.getOne(id);

            model.addAttribute("page", page);

            return "admin/pages/edit";

        }
//post mapping edit here starts

    @PostMapping("/edit")
    public String edit (@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        Page pageCurrent = pageRepo.getOne(page.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", pageCurrent.getTitle());
            return "admin/pages/edit";  //contains error messages
        }

        redirectAttributes.addFlashAttribute("message", "PAGE EDITED");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

// this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
        String slug = page.getSlug() =="" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ","-");


        // this will check to make sure only one slug exists because they are URL
        Page slugExists = pageRepo.findBySlug(page.getId(), slug);  //if slug exists but not for this page

        if ( slugExists != null) {
//if slug doesn't exist  danger
            redirectAttributes.addFlashAttribute("message", "slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);//makes sure input which causes errors sticks

        } else {
            page.setSlug(slug);
          //  page.setSorting(100);//sorts to last page of data

            pageRepo.save(page);//save the changes

        }
        return "redirect:/admin/pages/edit/" + page.getId(); //return to the page

    }
    @GetMapping("/delete/{id}")
    public String edit (@PathVariable int id, RedirectAttributes redirectAttributes) {
//redirects page when page is deleted

        pageRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Page Deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");//must add message to the view @index html


       // return "admin/pages/index";//not index because it is not a view that is being returned
        return "redirect:/admin/pages";
    }









//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable int id, Model model) {
//
//        Page page = pageRepo.getOne(id);
//
//        model.addAttribute("page",page);
//
//        return "admin/pages/edit";
//
//    }


}