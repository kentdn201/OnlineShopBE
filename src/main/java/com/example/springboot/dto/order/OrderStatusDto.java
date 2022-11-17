package com.example.springboot.dto.order;

import com.example.springboot.model.Enum.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private OrderStatus orderStatus;
}
