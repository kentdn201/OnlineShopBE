package com.example.springboot.service;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.exceptions.CustomException;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setSlug(productDto.getSlug());
        product.setCategory(category);
        productRepository.save(product);
    }

    public ProductDto getProductDto(Product product)
    {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageURL(product.getImageURL());
        productDto.setSlug(product.getSlug());
        productDto.setCategoryId(product.getCategory().getId());
        return  productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product: products) {
            productDtos.add(getProductDto(product));
        }

        return productDtos;
    }

    public void updateProduct(ProductDto productDto, String slug) throws Exception {
        Product existProduct = productRepository.findBySlug(slug);
        Optional<Product> existProductWithId = productRepository.findById(existProduct.getId());
        if(existProduct == null){
            throw new Exception("Sản phẩm không tồn tại");
        }
        Product product = existProductWithId.get();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageURL(productDto.getImageURL());
        product.setDescription(productDto.getDescription());
        product.setSlug(productDto.getSlug());
        product.setCategory(existProductWithId.get().getCategory());
        productRepository.save(product);
    }

    public Product getProductBySlug(String slug) {
        Product existProduct = productRepository.findBySlug(slug);
        return existProduct;
    }

    public void deleteProduct(String slug) throws Exception {
        Product existProduct = productRepository.findBySlug(slug);
        if(existProduct == null){
            throw new Exception("Sản phẩm không tồn tại");
        }
        productRepository.deleteById(existProduct.getId());
    }

    public Product findById(Integer productId) {
        Optional<Product> existProduct = productRepository.findById(productId);
        if(existProduct.isEmpty())
        {
            throw new CustomException("Sản phẩm có mã là: " + productId + " không tồn tại");
        }
        return existProduct.get();
    }
}
