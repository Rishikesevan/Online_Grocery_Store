package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Cart;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductDetailsController
{
    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/api/product/images/ProductDetails/{id}")
    public String productBasedId(@PathVariable Long id,Model model)
    {
        Product product = productDetailsService.productBasedId(id);

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts",
                productDetailsService.getRelatedProducts(product));

        return "ProductViewDetails.html";
    }
}
