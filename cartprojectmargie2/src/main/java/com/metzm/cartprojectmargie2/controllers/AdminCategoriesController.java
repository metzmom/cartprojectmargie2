package com.metzm.cartprojectmargie2.controllers;

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
        
        
}
