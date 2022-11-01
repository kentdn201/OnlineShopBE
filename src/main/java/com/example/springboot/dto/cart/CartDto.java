package com.example.springboot.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    List<CartItemDto> cartItems;
    private double totalPrice;
}
