package com.metzm.cartprojectmargie2.controllers;


import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import com.metzm.cartprojectmargie2.models.data.Product;
import com.metzm.cartprojectmargie2.models.data.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String add(Product product, Model model) {
        //need both product and model to match up for category and product by 1dthru categoryRepo
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product, BindingResult bindingResult, MultipartFile file,
                      RedirectAttributes redirectAttributes, Model model)
                       throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/products/add";  //contains error messages
        }
/*MMMMMMMMMMMMMMMMMMMMMMMMMMMMM
        redirectAttributes.addFlashAttribute("message", "PAGE added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        // this will enable the comparison of slug variable. if it exists change to lower case and replace spaces with -.
        String slug = page.getSlug() =="" ? page.getTitle().toLowerCase().replace(" ", "-")
                : page.getSlug().toLowerCase().replace(" ","-");

        // this will check to make sure only one slug exists because they are URL
        Page slugExists = pageRepo.findBySlug(page.getId(), slug);

        if ( slugExists != null) {//if slug doesn't exist  danger
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
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM */

        List<Category> categories = categoryRepo.findAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png")) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepo.findBySlug(slug);

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else {
            product.setSlug(slug);
            product.setImage(filename);
            productRepo.save(product);

            Files.write(path, bytes);
        }

        return "redirect:/admin/products/add";
    }

}