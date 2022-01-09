/*package com.metzm.cartprojectmargie2.controllers;

import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController{


        @Autowired
        private CategoryRepository categoryRepo;
        
        @GetMapping
        public String index(Model model) {

            List<Category> categories =categoryRepo.findAll();

            model.addAttribute("categories", categories);

            return "admin/categories/index";

        }
    //This get Mapping does same as live get add mapping that is live
    // @ModelAttribute("category")
    // public Category getCategory() {
    //     return new Category();
    // }

    //    @GetMapping("/add")
    //    public String add (@ModelAttribute Category category) {
    //
    //        return "admin/pages/index";
    //    }
    //if you do use this @ModelAttribute it can be share as noted below.




        @GetMapping("/add")
        public String add (Model model) {

        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }



}*/
package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
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
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    @Autowired
    private CategoryRepository categoryRepo;


//    public AdminPagesController(PageRepository pageRepo) {
//        this.pageRepo = pageRepo;

    //  }  autowired replaced this


    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryRepo.findAll();

        //this is for the sorting  I dont want  this keep line above and remove all sorting
     //   List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();


        model.addAttribute("categories", categories);

        return "admin/categories/index";
    }

    //This get Mapping does same as live get add mapping that is live
//    @GetMapping("/add")
//    public String add (@ModelAttribute Page page) {
//
//        return "admin/pages/index";
//    }
    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }


//to redirect for errors


    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/add";  //contains error messages
        }

        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

// this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
        String slug = category.getName().toLowerCase().replace(" ", "-");

        // this will check to make sure only one slug exists because they are URL
        Category categoryExists = categoryRepo.findByName(category.getName());

        if (categoryExists != null) {
//if slug doesnt exist  danger
            redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo", category);//makes sure input which causes errors sticks

        } else {
            category.setSlug(slug);
            category.setSorting(100);//sorts to last page of data

            categoryRepo.save(category);//save the changes

        }
        return "redirect:/admin/categories/add"; //return to the page

    }
}
//    @GetMapping("/edit/{id}")
//    public String edit (@PathVariable int id, Model model) {
//
//        Page page = pageRepo.getOne(id);
//
//        model.addAttribute("page", page);
//
//        return "admin/pages/edit";
//
//    }
////post mapping edit here starts
//
//    @PostMapping("/edit")
//    public String edit (@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
//        Page pageCurrent = pageRepo.getOne(page.getId());
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("pageTitle", pageCurrent.getTitle());
//            return "admin/pages/edit";  //contains error messages
//        }
//
//        redirectAttributes.addFlashAttribute("message", "PAGE EDITED");
//        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
//
//// this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
//        String slug = page.getSlug() =="" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ","-");
//
//
//        // this will check to make sure only one slug exists because they are URL
//        Page slugExists = pageRepo.findBySlug(page.getId(), slug);  //if slug exists but not for this page
//
//        if ( slugExists != null) {
////if slug doesn't exist  danger
//            redirectAttributes.addFlashAttribute("message", "slug exists, choose another");
//            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
//            redirectAttributes.addFlashAttribute("page", page);//makes sure input which causes errors sticks
//
//        } else {
//            page.setSlug(slug);
//            //  page.setSorting(100);//sorts to last page of data
//
//            pageRepo.save(page);//save the changes
//
//        }
//        return "redirect:/admin/pages/edit/" + page.getId(); //return to the page