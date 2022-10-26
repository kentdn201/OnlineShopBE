package com.example.springboot.controller;

import com.example.springboot.model.Category;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path="/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(path="/add")
    public @ResponseBody Category addNewProduct (@RequestBody Category category)
    {
        return categoryService.saveCategory(category);
    }

    @GetMapping(path="/all")
    public @ResponseBody List<Category> getAllProducts() {
        return categoryService.getCategories();
    }


    @GetMapping(path="/{slug}")
    public @ResponseBody Category getOneCategoryBySlug(@PathVariable String slug) {
        // This returns a JSON or XML with the users
        return categoryService.findBySlug(slug);
    }

    @PutMapping("/edit")
    public Category editProduct(@RequestBody Category category)
    {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id)
    {
        return categoryService.deleteCategory(id);
    }

}