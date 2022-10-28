package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.model.Category;
import com.example.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path="/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(path="/create")
    public @ResponseBody ResponseEntity<ApiResponse> addNewCategory (@RequestBody Category category)
    {
        categoryService.saveCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Thêm Danh Mục Mới Thành Công:" + " " + category.getName()),
                HttpStatus.CREATED);
    }

    @GetMapping(path="/all")
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }


    @GetMapping(path="/{slug}")
    public @ResponseBody Category getOneCategoryBySlug(@PathVariable String slug) {
        // This returns a JSON or XML with the users
        return categoryService.findBySlug(slug);
    }

    @PutMapping(path="/update/{slug}")
    public @ResponseBody ResponseEntity<ApiResponse> editCategory(@PathVariable String slug, @RequestBody Category category)
    {
        if(categoryService.findBySlug(slug) == null)
        {
            return new ResponseEntity<>(new ApiResponse(false, "Không Tìm Thấy Danh Mục Nào Có Slug Là: " + slug),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.updateCategory(slug, category);
        return new ResponseEntity<>(new ApiResponse(true, "Sửa Thành Công Danh Mục:" + " " + category.getName()),
                HttpStatus.OK);
    }

    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody ResponseEntity<ApiResponse> deleteCategory(@PathVariable int id)
    {
        Optional<Category> existCategory = Optional.ofNullable(categoryService.findById(id).orElse(null));
        if(existCategory.isEmpty())
        {
            return new ResponseEntity<>(new ApiResponse(false, "Danh Mục Bạn Xóa Không Tồn Tại"),
                    HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành Công Danh Mục:" + " " + existCategory.get().getName()),
                HttpStatus.OK);
    }

}