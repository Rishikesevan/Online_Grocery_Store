package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Service.HomePageService;
import com.project.Grocery_Store.Service.StaplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomepageController {

    @Autowired
    private HomePageService homeService;

    @GetMapping("/api/Homepage")
    public String HomePage(Model model){
        List<Product> features = homeService.featuresProduct();
        model.addAttribute("snacksProduct",features);
        return "index";
    }


    @GetMapping("/filter/category")
    public String productCategory(@RequestParam(name = "category", required = false) String category,
                                  @RequestParam( name = "price", required = false) String price ,
                                  @RequestParam(name = "searchValue", required = false) String searchValue,
                                  Model model){
        model.addAttribute("pCategory", category);
        model.addAttribute("pPrice", price);
        model.addAttribute("searchProduct", searchValue);


        if(category.equals("categories") && price.equals("0-0") && searchValue.isEmpty()){
            List<Product> products = homeService.viewAll();
            model.addAttribute("allProducts",products);
            return "SearchBar";
        }
        List<Product> productsCategory = homeService.category(category, price, searchValue);

        if (!productsCategory.isEmpty()) {
            model.addAttribute("allProducts",productsCategory);
            return "SearchBar";
        }



        return "redirect:/api/search?error";
    }

    @GetMapping("/api/discount")
    public String viewDiscount(Model model){
        List<Product> discountProducts = homeService.discountList();
        model.addAttribute("discountProduct",discountProducts);
        return "Discount";
    }

    @GetMapping("/api/search")
    public String searchbar(){
        return "SearchBar";
    }
    @GetMapping("/search")
    public String searchProduct(@RequestParam(name = "price") String price,
                                @RequestParam(name = "category") String category ,
                                @RequestParam( name = "searchValues") String searchValues ,
                                @RequestParam(name = "searchValue") String searchValue,
                                Model model){

        model.addAttribute("pCategory", category);
        model.addAttribute("pPrice", price);
        model.addAttribute("searchProduct", searchValue);

        List<Product> productsCategory = homeService.category(category, price, searchValue);
        List<Product>products = new ArrayList<>();
        System.out.print(searchValue);
        for(Product product : productsCategory){
            String productname =product.getName().toLowerCase();
            if(productname.equals(searchValue.toLowerCase())){
                products.add(product);
            }
        }
        if(!products.isEmpty()) {
            model.addAttribute("allProducts", products);
            return "SearchBar";
        }
        return "redirect:/api/search?error";
    }




}
