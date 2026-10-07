package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Category;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Service.SnackService;
import com.project.Grocery_Store.Service.StaplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
public class SnacksController {

    @Autowired
    private SnackService snackService;

    @GetMapping("/snacks")
    public String snacks(){
        return "Snacks";
    }

    @GetMapping("/api/product/Snacks")
    public String getAll(Model model)
    {
        Long ids =2l;
        List<Product> productList = new ArrayList<>();
        List<Product> pl = snackService.getAll();

        for(Product product: pl){
            Category category = product.getCategory();
            if(category.getId().equals(ids))
            {
                productList.add(product);
            }
        }
        model.addAttribute("snackProduct",productList);
        System.out.println(pl);
        return "Snacks.html";
    }

    @GetMapping("/Snacks/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        List<Product> productList = snackService.getAll();

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

    @GetMapping("/product/snacks/filter")
    public String search(@RequestParam("keyword") String keyword, Model model)
    {
        List<Product> product = snackService.search(keyword);
        if(product.isEmpty()|| product==null){
            return "redirect:/snacks?error";
        }
        model.addAttribute("snackProduct",product);
        return "Snacks.html";
    }

}
