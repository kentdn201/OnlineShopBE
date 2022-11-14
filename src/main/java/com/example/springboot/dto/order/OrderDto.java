package com.example.springboot.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String address;
    private String typePayment;
    private String note;
    private String phoneNumber;
    private List<ProductOrderDto> productOrderDtos;
}
