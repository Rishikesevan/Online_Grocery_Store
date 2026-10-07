package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Category;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Service.HouseholdService;
import com.project.Grocery_Store.Service.SnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HouseholdController
{
    @Autowired
    private HouseholdService householdService;

    @GetMapping("/api/product/Household")
    public String getAll(Model model)
    {
        Long ids =4l;
        List<Product> productList = new ArrayList<>();
        List<Product> pl = householdService.getAll();
        for(Product product: pl){
            Category category = product.getCategory();
            if(category.getId().equals(ids))
            {
                productList.add(product);
            }
        }
        model.addAttribute("householdProduct",productList);
        System.out.println(pl);
        return "Household.html";
    }

    @GetMapping("/Household/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        List<Product> productList = householdService.getAll();

        Product  product = new Product();

        for(Product products:productList){
            if(id== products.getId()) {
                product = products;
            }
        }
        if (product == null || product.getImage() == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(product.getImage());
    }

    @GetMapping("/product/household/filter")
    public String search(@RequestParam("keyword") String keyword, Model model)
    {
        List<Product> product = householdService.search(keyword);
        model.addAttribute("householdProduct",product);
        return "Household.html";
    }
}
