package com.project.Grocery_Store.Controller;

import com.project.Grocery_Store.Entity.Product;
import com.project.Grocery_Store.Repository.ProductRepository;
import com.project.Grocery_Store.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/api/product/productViewDetails/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] loadImage(@PathVariable Long id) {

        return imageService.loadImage(id);
    }

    @GetMapping("/product/image/{id}")
    @ResponseBody
    public byte[] getProductImage(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? product.getImage() : null;
    }

}
