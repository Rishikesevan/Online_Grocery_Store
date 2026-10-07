package com.project.Grocery_Store.Service;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HomePageService
{
    @Autowired
    private ProductRepository productRepository;

    public List<Product> featuresProduct(){
        int i=1;
        List<Product> productList = productRepository.findAll();
        List<Product> features= new ArrayList<>();
        for(Product product : productList){
            features.add(product);
            if(i==6){
                break;
            }
            i++;
        }

        return features;
    }
    public List<Product> viewAll(){
        return productRepository.findAll();
    }

    public List<Product> category(String pcategory, String pPrice ,String pSearch){
        List<Product> products = productRepository.findAll();

        if(!pSearch.isEmpty() && pcategory.equals("categories") && pPrice.equals("0-0")){
            List<Product> products1 = new ArrayList<>();
            String productName = pSearch.toLowerCase();
            for(Product product:products){
                String productNames = product.getName().toLowerCase();
                if(productNames.equals(productName)){
                    products1.add(product);
                }
            }
            return products1;
        }

        double minProduct;
        double maxProduct;
        if(pPrice!=null) {
            String[] productPrice = pPrice.split("-");

            minProduct = Double.parseDouble(productPrice[0]);
            maxProduct = Double.parseDouble(productPrice[1]);
        }else{
            minProduct=0.0;
            maxProduct=0.0;
        }

        List<Product> staplesProduct = new ArrayList<>();
        for(Product product :products){

            if (product.getCategory().getName().equals(pcategory) || pcategory.equals("categories")) {
                if (pPrice.equals("0-0") || product.getPrice() >= minProduct && product.getPrice() <= maxProduct) {
                    staplesProduct.add(product);
                }

            }
        }

        return staplesProduct;
    }

    public List<Product> discountList(){
        List<Product>productList = new ArrayList<>();
        Set<Long> discountProduct = new HashSet<>();
        discountProduct.add(1l);
        discountProduct.add(10l);
        discountProduct.add(15l);
        discountProduct.add(20l);
        discountProduct.add(30l);

        for(Long values :discountProduct ){
            Product product = productRepository.findById(values).orElse(null);
            if(product!=null){
                productList.add(product);
            }
        }
        return productList;

    }

    public List<Product>  viewallSearch(String search){
        List<Product> products = productRepository.findAll();
        List<Product> products1= new ArrayList<>();
        String psearch = search.toLowerCase();
        for(Product product : products){
            if (product.getName().toLowerCase().equals(psearch)) {
                if(products1.isEmpty()){
                    products1.add(product);
                }else{
                    for(Product product1: products1){
                        if (product1.getName().toLowerCase().equals(psearch)) {
                            products1.add(product1);
                        }
                    }
                }

            }

        }
        return products1;
    }

}
