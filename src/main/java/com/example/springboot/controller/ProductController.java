package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(path = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
        {
            return new ResponseEntity<>(new ApiResponse(false, "fail"), HttpStatus.BAD_REQUEST);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return  new ResponseEntity<>(new ApiResponse(true, "success"), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

//   Update 1 sản phẩm bằng slug
    @PutMapping(path = "/update/{slug}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable(name = "slug") String slug, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if(!optionalCategory.isPresent())
        {
            return new ResponseEntity<>(new ApiResponse(false, "fail"), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, slug);
        return  new ResponseEntity<>(new ApiResponse(true, "success"), HttpStatus.OK);
    }

//   Lấy ra 1 sản phẩm bằng slug
    @GetMapping(path = "/{slug}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "slug") String slug) {
        Product existProduct = productService.getProductBySlug(slug);
        if(existProduct == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(existProduct, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{slug}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name = "slug") String slug) throws Exception {
        Product existProduct = productService.getProductBySlug(slug);
        if(existProduct == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(slug);
        return new ResponseEntity<>(new ApiResponse(true, "Delete Successful Product: " + existProduct.getName()), HttpStatus.OK);
    }
}
