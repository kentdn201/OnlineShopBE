package com.example.springboot.dto.order;

import com.example.springboot.model.Enum.OrderStatus;
import com.example.springboot.model.OrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto extends OrderShowDto {
    private List<OrderProductDto> orderProductDtos;
}
