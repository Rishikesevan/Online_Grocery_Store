package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductDetailsService
{
    @Autowired
    private ProductRepository productRepository;

    public Product productBasedId(Long id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getRelatedProducts(Product product) {
        return productRepository.findByCategory_Name(
                product.getCategory().getName());
    };
}
