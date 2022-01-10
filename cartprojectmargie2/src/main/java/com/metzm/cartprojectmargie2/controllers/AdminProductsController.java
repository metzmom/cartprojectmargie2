package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import com.metzm.cartprojectmargie2.models.data.Product;
import com.metzm.cartprojectmargie2.models.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;
    //need to have this to connect product it with category id

    @GetMapping
    public String index(Model model) {

        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        //passes to view index
        return "admin/products/index";

    }
    @GetMapping("/add")
    public String add (Product product, Model model) {
    //need both product and model to match up for category and product by 1dthru categoryRepo
     List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        return "admin/products/add";
    }

}
