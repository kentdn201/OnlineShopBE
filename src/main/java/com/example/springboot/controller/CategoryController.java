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
        Optional<Category> existCategory = Optional.ofNullable(categoryService.findBySlug(category.getSlug()));
        if (existCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Slug của danh mục đã tồn tại"),
                    HttpStatus.BAD_REQUEST);
        }
        categoryService.saveCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Thêm Danh Mục Mới Thành Công:" + " " + category.getName()),
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
        String slug = "";
        return categoryServiceImpl.findCategoryBySlugOrId(slug, id);
    }

    @GetMapping(path = "/danh-muc/{id}")
    public @ResponseBody Optional<Category> getOneCategoryById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @PutMapping(path = "/update/{slug}")
    public @ResponseBody ResponseEntity<ApiResponse> editCategory(@PathVariable String slug, @RequestBody Category category) {
        if (categoryService.findBySlug(slug) == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Không Tìm Thấy Danh Mục Nào Có Slug Là: " + slug),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.updateCategory(slug, category);
        return new ResponseEntity<>(new ApiResponse(true, "Sửa Thành Công Danh Mục:" + " " + category.getName()),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        String deleteCategoryById = categoryService.deleteCategory(id);
        return "Delete Success";
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