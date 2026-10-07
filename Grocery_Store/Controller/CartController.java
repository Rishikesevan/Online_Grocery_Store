package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Cart;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.CartRepository;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController
{

    @Autowired
    private CartService cartService;

    @PostMapping("/save/cart")
    public String saveCartItem(@RequestParam("productId") Long productId, Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        if(userId==null){

            return "redirect:/login?erroritem";
        }

        cartService.addCart(userId,productId);

        return "redirect:/view/cart";
    }


    @GetMapping("/view/cart")
    public String viewCart(Model model,HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        if(userId==null){
            return "redirect:/login?errors";
        }
        List<Cart> carts = cartService.cartsDetails(userId);
        List<Product> productList = new ArrayList<>();
        for(Cart cart : carts){

            List<Product> products = cart.getProducts();
            for(Product product1 : products) {
                Product product = new Product();
                product.setId(product1.getId());
                product.setImage(product1.getImage());
                product.setCategory(product1.getCategory());
                product.setPrice(product1.getPrice());
                product.setName(product1.getName());
                productList.add(product);
            }

        }
        model.addAttribute("productList",productList);
        return "Cart";
    }
}
