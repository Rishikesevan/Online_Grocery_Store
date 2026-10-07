package com.project.Grocery_Store.Controller;


import com.project.Grocery_Store.Entity.Category;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Service.BeveragesService;
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
public class BeveragesController
{
    @Autowired
    private BeveragesService beveragesService;

    @GetMapping("/beverages")
    public String everages(){
        return "Beverages";
    }

    @GetMapping("/api/product/Beverages")
    public String getAll(Model model)
    {
        Long ids =3l;
        List<Product> productList = new ArrayList<>();
        List<Product> pl = beveragesService.getAll();

        for(Product product: pl){
            Category category = product.getCategory();
            if(category.getId().equals(ids))
            {
                productList.add(product);
            }
        }
        model.addAttribute("beveragesProduct",productList);
        System.out.println(pl);
        return "Beverages.html";
    }

    @GetMapping("/Beverages/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        List<Product> productList = beveragesService.getAll();

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

    @GetMapping("/product/beverages/filter")
    public String search(@RequestParam("keyword") String keyword, Model model)
    {
        List<Product> product = beveragesService.search(keyword);
        if(product.isEmpty()|| product==null){
            return "redirect:/beverages?error";
        }
        model.addAttribute("beveragesProduct",product);
        return "Beverages.html";
    }
}
