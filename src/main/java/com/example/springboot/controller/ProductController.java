package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.ProductDto;
import com.example.springboot.dto.order.ProductDetailDto;
import com.example.springboot.model.Category;
import com.example.springboot.model.OrderProduct;
import com.example.springboot.model.Product;
import com.example.springboot.repository.CategoryRepository;
import com.example.springboot.repository.OrderProductRepository;
import com.example.springboot.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        String createMessage = productService.createProduct(productDto, categoryRepository.findBySlugOrId("", productDto.getCategoryId()));
        if (createMessage == "This product slug is available in website, please use other slug") {
            return new ResponseEntity<>(new ApiResponse(false, createMessage), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, createMessage), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //   Update a product using slug
    @PutMapping(path = "/update/{slug}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable(name = "slug") String slug, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "fail"), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, slug);
        return new ResponseEntity<>(new ApiResponse(true, "success"), HttpStatus.OK);
    }

    //   get a product using slug of product
    @GetMapping(path = "/{slug}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "slug") String slug) {
        ProductDto productDto = productService.getProductDtoBySlug(slug);
        if (productDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping(path = "/san-pham/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetailDtoById(@PathVariable(name = "productId") Integer productId) {
        ProductDetailDto productDetailDto = productService.getProductDetailDtoById(productId);
        if (productDetailDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productDetailDto, HttpStatus.OK);
    }


    @DeleteMapping(path = "/delete/{slug}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name = "slug") String slug) throws Exception {
        String deleteMsg = productService.deleteProduct(slug);
        if (deleteMsg == "This product is not available to delete") {
            return new ResponseEntity<>(new ApiResponse(false, deleteMsg), HttpStatus.NOT_FOUND);
        } else if (deleteMsg == "Can not delete this product has been ordered") {
            return new ResponseEntity<>(new ApiResponse(false, deleteMsg), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(true, deleteMsg), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductDto>> searchProducts(String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}
