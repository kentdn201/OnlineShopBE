package com.example.springboot.service;

import com.example.springboot.model.Product;
import com.example.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts()
    {
        return productRepository.findAll();
    }

    public Product findProductById(int id)
    {
        return productRepository.findById(id).orElse(null);
    }

    public Product findProductBySlug(String slug)
    {
        var existProduct = productRepository.findBySlug(slug);
        if(existProduct == null){
            return null;
        }
        return existProduct;
    }

    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    public String deleteProduct(int id)
    {
        productRepository.deleteById(id);
        return  "Delete Sucess: Product Id" + id;
    }

    public Product updateProduct(Product product)
    {
        Product existProduct = productRepository.findById(product.getId()).orElse(null);
        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());
        existProduct.setSlug(product.getSlug());
        existProduct.setImage(product.getImage());
        existProduct.setCount(product.getCount());
        existProduct.setDescription(product.getDescription());
        return productRepository.save(existProduct);
    }



}
