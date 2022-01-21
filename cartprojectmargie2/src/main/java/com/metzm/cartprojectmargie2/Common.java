package com.metzm.cartprojectmargie2;

//this page goes before all other controller and will make links to pages by creating cpages variable

import com.metzm.cartprojectmargie2.models.Cart;
import com.metzm.cartprojectmargie2.models.CategoryRepository;
import com.metzm.cartprojectmargie2.models.data.Category;
import com.metzm.cartprojectmargie2.models.data.Page;
import com.metzm.cartprojectmargie2.models.data.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@SuppressWarnings("unchecked")
    public class Common {//&&&&&&& origional

    @Autowired
    private PageRepository pageRepo;//&&&&&&& origional

    @Autowired
    private CategoryRepository categoryRepo;//this is for the menu *********

    @ModelAttribute
    public void sharedData(Model model, HttpSession session) {//&&&&&&& origional just Model model

        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();//&&&&&&& origional

        List<Category> categories = categoryRepo.findAll();//this is for the menu//this is for the menu *********

        boolean cartActive = false;

        if (session.getAttribute("cart") != null) {

            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");

            int size = 0;
            double total = 0;

            for (Cart value : cart.values()) {
                size += value.getQuantity();
                total += value.getQuantity() * Double.parseDouble(value.getPrice());
            }

            model.addAttribute("csize", size);
            model.addAttribute("ctotal", total);
            cartActive = true;
        }

        model.addAttribute("cpages", pages);//&&&&&&& origional
        model.addAttribute("ccategories", categories);//this is for the menu *********then update page.html
        model.addAttribute("cartActive", cartActive);
    }


}