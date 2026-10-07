package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Cart;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Entity.User;
import com.project.Grocery_Store.Repository.CartRepository;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addCart(Long userId, Long productId){

        User user = userRepository.findById(userId).orElse(null);

        Product product = productRepository.findById(productId).orElse(null);

        List<Cart> cart = cartRepository.findAll();

        boolean check=false;
        for (Cart cart1 : cart) {
            if (cart1.getUser().equals(user)) {
                cart1.getProducts().add(product);
                check=true;
                cartRepository.save(cart1);
                break;
            }
        }
        if(!check) {
            Cart cart2 = new Cart();
            cart2.setUser(user);
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            cart2.setProducts(productList);
            cartRepository.save(cart2);
        }

    }

    public List<Cart> cartsDetails (Long userId){
        List<Cart> carts= cartRepository.findAll();
        List<Cart> carts1 = new ArrayList<>();
        for(Cart cart: carts){
            if(cart.getUser().getId().equals(userId)){
                carts1.add(cart);
            }
        }

        return carts1;
    }
}
