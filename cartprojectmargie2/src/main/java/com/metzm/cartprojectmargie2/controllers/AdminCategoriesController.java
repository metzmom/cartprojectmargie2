package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
       // List<Category> categories = categoryRepo.findAll();

        //this is for the sorting  I don't want  this keep line above and remove all sorting
        List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();
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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Category category = categoryRepo.getOne(id);

        model.addAttribute("category", category);

        return "admin/categories/edit";

    }

    //post mapping edit here starts

    @PostMapping("/edit")
    public String edit(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Category categoryCurrent = categoryRepo.getOne(category.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryName", categoryCurrent.getName());
            return "admin/categories/edit";  //contains error messages
        }

        redirectAttributes.addFlashAttribute("message", "CATEGORY EDITED");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        // this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
        String slug = category.getName().toLowerCase().replace(" ", "-");

        // this will check to make sure only one slug exists because they are URL
        Category categoryExists = categoryRepo.findByName(category.getName());

        if (categoryExists != null) {
            //if slug doesn't exist  danger
            redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //  redirectAttributes.addFlashAttribute("categoryInfo", category);//makes sure input which causes errors sticks

        } else {
            category.setSlug(slug);
            //category.setSorting(100);//sorts to last

            categoryRepo.save(category);//save the changes

        }


        return "redirect:/admin/categories/edit/" + category.getId(); //return to the
    }

    @GetMapping("/delete/{id}")
    public String edit(@PathVariable int id, RedirectAttributes redirectAttributes) {
        //redirects page when page is deleted

        categoryRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Category Deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");//must add message to the view @index html


        // return "admin/pages/index";//not index because it is not a view that is being returned
        return "redirect:/admin/categories";
    }

    @PostMapping("/reorder")
    public @ResponseBody
    String reorder(@RequestParam("id[]") int[] id) {
        // public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {
        //reorder the categories
        int count = 1;
        Category category;
        //loop thru id and sort
        for (int categoryId : id) {
            category = categoryRepo.getOne(categoryId);
            category.setSorting(count);
            categoryRepo.save(category);
            count++;
        }

        return "ok";

    }

}