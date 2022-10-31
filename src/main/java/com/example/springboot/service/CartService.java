package com.example.springboot.service;

import com.example.springboot.dto.cart.AddToCartDto;
import com.example.springboot.model.Product;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private ProductService productService;
    public void addToCart(AddToCartDto addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProductId());
    }
}
