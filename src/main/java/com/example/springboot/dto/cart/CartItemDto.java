package com.example.springboot.dto.cart;

import com.example.springboot.model.Cart;
import com.example.springboot.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Integer id;
    private int quantity;
    private Product product;

    public CartItemDto(Cart cart)
    {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
