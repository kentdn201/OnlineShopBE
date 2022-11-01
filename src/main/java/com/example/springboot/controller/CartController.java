package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.cart.AddToCartDto;
import com.example.springboot.dto.cart.CartDto;
import com.example.springboot.model.Product;
import com.example.springboot.model.User;
import com.example.springboot.service.AuthenticationSerivce;
import com.example.springboot.service.CartService;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationSerivce authenticationSerivce;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam(value = "token") String token)
    {
        try {
            authenticationSerivce.authenticate(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        User user = authenticationSerivce.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "Thêm Thành Công"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam(value = "token") String token)
    {
        try {
            authenticationSerivce.authenticate(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        User user = authenticationSerivce.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable(value = "cartItemId") Integer itemId,
                                                      @RequestParam(value = "token") String token)
    {
        try {
            authenticationSerivce.authenticate(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }

        User user = authenticationSerivce.getUser(token);

        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Xóa Thành Công"), HttpStatus.OK);
    }
}
