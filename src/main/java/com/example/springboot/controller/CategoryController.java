package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Category;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path="/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @PostMapping(path="/create")
    public @ResponseBody ResponseEntity<ApiResponse> addNewCategory (@RequestBody Category category)
    {
        Optional<Category> existCategory = Optional.ofNullable(categoryService.findBySlug(category.getSlug()));
        if(existCategory.isPresent())
        {
            return new ResponseEntity<>(new ApiResponse(false, "Slug của danh mục đã tồn tại"),
                    HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping(path = "/danh-muc/{id}")
    public @ResponseBody Optional<Category> getOneCategoryById(@PathVariable Integer id)
    {
        return categoryService.findById(id);
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
    public @ResponseBody ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id)
    {
        Optional<Category> existCategory = Optional.ofNullable(categoryService.findById(id).orElse(null));
        if(existCategory.isEmpty())
        {
            return new ResponseEntity<>(new ApiResponse(false, "Danh Mục Bạn Xóa Không Tồn Tại"),
                    HttpStatus.NOT_FOUND);
        }

        List<ProductDto> productDtos = productService.getAllProducts();
        for (ProductDto productDto: productDtos)
        {
            if(productDto.getCategoryId() == id)
            {
                return new ResponseEntity<>(new ApiResponse(false, "Danh Mục Bạn Xóa Còn Tồn Tại Sản Phẩm"),
                        HttpStatus.BAD_REQUEST);
            }
        }

        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành Công Danh Mục:" + " " + existCategory.get().getName()),
                HttpStatus.OK);
    }

    @GetMapping("{slug}/san-pham")
    public List<ProductDto> getProductsByCategory(@PathVariable(name = "slug") String slug)
    {
//      Danh sách hiện tại của sản phẩm
        List<ProductDto> listProductDto = productService.getAllProducts();

//      Một danh sách mới
        List<ProductDto> listProduct = new ArrayList<>();

//      Slug của category cần tìm
        Category existCategory = categoryService.findBySlug(slug);

//      Vòng lặp for tìm các sản phẩm có category_id = với id của danh mục cần tìm rồi thêm vào mảng mới
        for (ProductDto product: listProductDto)
        {
            if(product.getCategoryId() == existCategory.getId())
            {
                listProduct.add(product);
            }
        }

        return listProduct;
    }

}