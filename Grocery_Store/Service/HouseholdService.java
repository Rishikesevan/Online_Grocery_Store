package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseholdService
{
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll()
    {
        return productRepository.findAll();
    }

    public List<Product> search(String keyword)
    {
        List<Product> productList = productRepository.findAll();
        List<Product> searchproduct = new ArrayList<>();
        for (Product product :productList)
        {
            if (product.getName()!= null && product.getName().toLowerCase().contains(keyword.toLowerCase()) && product.getCategory().getName().equals("Household"))
            {
                searchproduct.add(product);
            }
        }
        return searchproduct;
    }
}
