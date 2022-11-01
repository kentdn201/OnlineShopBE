package com.example.springboot.service;

import com.example.springboot.dto.cart.AddToCartDto;
import com.example.springboot.dto.cart.CartDto;
import com.example.springboot.dto.cart.CartItemDto;
import com.example.springboot.exceptions.CustomException;
import com.example.springboot.model.Cart;
import com.example.springboot.model.Product;
import com.example.springboot.model.User;
import com.example.springboot.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;
    public void addToCart(AddToCartDto addToCartDto, User user) {
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
       List<Cart> cartList = cartRepository.findAllByUser(user);

       List<CartItemDto> cartItemDtos = new ArrayList<>();
        double totalPrice = 0;
        for (Cart cart: cartList)
        {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalPrice += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItemDtos.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(totalPrice);
        cartDto.setCartItems(cartItemDtos);

        return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {

        Optional<Cart> existCart = cartRepository.findById(cartItemId);

        if(existCart.isEmpty())
        {
            throw new CustomException("Sản phẩm này đã bị xóa " + cartItemId);
        }

        Cart cart = existCart.get();
        if(cart.getUser() != user)
        {
            throw new CustomException("Sản phẩm này đã bị xóa từ trước " + cartItemId);
        }

        cartRepository.delete(cart);
    }
}
