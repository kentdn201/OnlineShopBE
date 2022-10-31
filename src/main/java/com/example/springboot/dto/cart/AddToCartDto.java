package com.example.springboot.dto.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
