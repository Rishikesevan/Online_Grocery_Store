package com.project.Grocery_Store.Service;


import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SnackService
{
    @Autowired
    private ProductRepository snackRepository;


    public List<Product> getAll()
    {
        return snackRepository.findAll();
    }



    public List<Product> search(String pName){
        List<Product> productList = snackRepository.findAll();
        List<Product> productList1 = new ArrayList<>();
        String productName = pName.toLowerCase();
        for(Product product:productList){
            String productNames = product.getName().toLowerCase();
            if(productNames.equals(productName) && product.getCategory().getName().equals("Snacks")){
                productList1.add(product);
            }
        }
        return productList1;
    }
}
