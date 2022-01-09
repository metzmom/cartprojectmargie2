package com.metzm.cartprojectmargie2.controllers;


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
    private ProductRepository productRepository;

    @GetMapping
    public String index(Model model) {

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        //passes to view index
        return "admin/products/index";



    }







}
