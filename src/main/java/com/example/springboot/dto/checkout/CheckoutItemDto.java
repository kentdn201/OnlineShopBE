package com.example.springboot.dto.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private long productId;
    private int userId;

}
