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
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        Optional<Product> existProductSlug = Optional.ofNullable(productService.getProductBySlug(productDto.getSlug()));
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Danh Mục Này Đã Không Còn Trong Hệ Thống"), HttpStatus.BAD_REQUEST);
        }
        if (existProductSlug.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Slug này đã tồn tại trong hệ thống"), HttpStatus.BAD_REQUEST);
        }


        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "Thêm Thành Công"), HttpStatus.CREATED);
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
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "fail"), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, slug);
        return new ResponseEntity<>(new ApiResponse(true, "success"), HttpStatus.OK);
    }

    //   Lấy ra 1 sản phẩm bằng slug
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
        Product existProduct = productService.getProductBySlug(slug);
        if (existProduct == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Không Tìm Thấy Sản Phảm Mà Bạn Muốn Xóa"), HttpStatus.NOT_FOUND);
        }

        List<OrderProduct> orderProducts = orderProductRepository.findAll();
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getProduct().getId() == existProduct.getId()) {
                return new ResponseEntity<>(new ApiResponse(false, "Không Thể Xóa Sản Phẩm Đang Được Đặt Hàng"), HttpStatus.BAD_REQUEST);
            }
        }
        productService.deleteProduct(slug);
        return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành Công Sản Phẩm: " + existProduct.getName()), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductDto>> searchProducts(String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
}
