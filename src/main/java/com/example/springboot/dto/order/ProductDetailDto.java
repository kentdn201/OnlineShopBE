package com.example.springboot.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull String slug;
}
