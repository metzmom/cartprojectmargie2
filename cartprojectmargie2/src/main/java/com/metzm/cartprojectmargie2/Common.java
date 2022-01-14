package com.metzm.cartprojectmargie2;

//this page goes before all other controller and will make links to pages by creating cpages variable

import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import com.metzm.cartprojectmargie2.models.data.Page;
import com.metzm.cartprojectmargie2.models.data.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
  //  @SuppressWarnings("unchecked")
    public class Common {

    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @ModelAttribute
    public void sharedData(Model model) {

         List<Page> pages = pageRepo.findAllByOrderBySortingAsc();

         List<Category> categories = categoryRepo.findAll();

        model.addAttribute("cpages",pages);
        model.addAttribute("ccategories",categories);
    }
}
