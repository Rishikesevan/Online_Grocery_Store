package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Category;
import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Service.StaplesService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
public class StapleController
{
    @Autowired
    private StaplesService staplesService;

    @GetMapping("/staples")
    public String staples(){
        return "Staples";
    }

    @GetMapping("/api/product/Staples")
    public String getAll(Model model)
    {
        Long ids =1l;
        List<Product> productList = new ArrayList<>();
        List<Product> pl = staplesService.getAll();

        for(Product product: pl){
            Category category = product.getCategory();
            if(category.getId().equals(ids))
            {
                productList.add(product);
            }
        }
        model.addAttribute("stapleProduct",productList);
        System.out.println(pl);
        return "Staples";
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        List<Product> productList = staplesService.getAll();

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

    @GetMapping("/product/staples/filter")
    public String search(@RequestParam("keyword") String keyword, Model model)
    {
        List<Product> product = staplesService.search(keyword);
        if(product.isEmpty()|| product==null){
            return "redirect:/staples?error";
        }
        model.addAttribute("stapleProduct",product);
        return "Staples.html";
    }


}

