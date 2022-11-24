package com.example.springboot.dto.order;

import com.example.springboot.dto.ProductDto;
import com.example.springboot.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    private ProductDto product;
    private int quantity;
    private Double price;
}
