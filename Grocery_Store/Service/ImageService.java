package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private ProductRepository productRepository;

    public byte[] loadImage(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        System.out.println("image   "+product.getImage());
        return product.getImage();
    }

}
