package com.example.springboot.controller;

import com.example.springboot.model.Product;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path="/product") // This means URL's start with / (after Application path)
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(path="/add")
    public @ResponseBody Product addNewProduct (@RequestBody Product product)
    {
        return productService.saveProduct(product);
    }

    @GetMapping(path="/all")
    public @ResponseBody List<Product> getAllProducts() {
        return productService.findAllProducts();
    }


    @GetMapping(path="/{slug}")
    public @ResponseBody Product getOneProductsBySlug(@PathVariable String slug) {
        // This returns a JSON or XML with the users
        return productService.findProductBySlug(slug);
    }

    @PutMapping("/edit")
    public Product editProduct(@RequestBody Product product)
    {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id)
    {
        return productService.deleteProduct(id);
    }

}