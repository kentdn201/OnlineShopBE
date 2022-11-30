package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Category;
import com.example.springboot.repository.ProductRepository;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.CategoryServiceImpl;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping(path = "/create")
    public @ResponseBody ResponseEntity<ApiResponse> addNewCategory(@RequestBody Category category) {
        Category existCategory = categoryService.findBySlug(category.getSlug());
        if (existCategory != null) {
            return new ResponseEntity<>(new ApiResponse(false, "You can not add a new category with same slug in data"),
                    HttpStatus.BAD_REQUEST);
        }
        categoryService.saveCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Add success category:" + " " + category.getName()),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }

    //   22/11/2022 - lay category theo slug hoac la id
    @GetMapping(path = "/{slug}")
    public @ResponseBody Category getOneCategoryBySlug(@PathVariable String slug) {
        return categoryServiceImpl.findCategoryBySlugOrId(slug, 0);
    }

    //   22/11/2022 - lay category theo slug hoac la id
    @GetMapping(path = "/get/{id}")
    public @ResponseBody Category getOneCategoryBySlug(@PathVariable Integer id) {
        return categoryServiceImpl.findCategoryBySlugOrId("", id);
    }

    @GetMapping(path = "/danh-muc/{id}")
    public @ResponseBody Optional<Category> getOneCategoryById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @PutMapping(path = "/update/{slug}")
    public @ResponseBody ResponseEntity<ApiResponse> editCategory(@PathVariable String slug, @RequestBody Category category) {
        String updateMsg = categoryService.updateCategory(slug, category);
        if (updateMsg == "Update fail") {
            return new ResponseEntity<>(new ApiResponse(false, "Can not found category slug to edit category"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, "Update success:" + " " + category.getName()),
                HttpStatus.OK);
    }

    //  Update deleteCategory using sql query
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        String deleteCategoryById = categoryService.deleteCategory(id);
        if (deleteCategoryById == "This category still exists products or has been deleted") {
            return new ResponseEntity<>(new ApiResponse(false, deleteCategoryById), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, deleteCategoryById), HttpStatus.OK);
    }

    //    Use sql query to get all product by category slug
    @GetMapping("{slug}/san-pham")
    public List<ProductDto> getProductsByCategory(@PathVariable(name = "slug") String slug) {
        return categoryService.getProductsByCategorySlug(slug);
    }

    //  22/11/2022 - tao tren controller thuc hien lay ra category theo slug va id
    @GetMapping("/get/{slug}/{id}")
    public Category findBySlugAndId(@PathVariable(name = "slug") String slug, @PathVariable(name = "id") Integer id) {
        return categoryServiceImpl.findCategoryBySlugAndId(slug, id);
    }
}