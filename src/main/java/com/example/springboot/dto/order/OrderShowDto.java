package com.example.springboot.dto.order;

import com.example.springboot.model.OrderProduct;
import com.example.springboot.model.OrderStatus;
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
public class OrderShowDto {
    private Integer id;
    private Date createDate;
    private OrderStatus orderStatus;
    private Integer userId;
    private String address;
    private String typePayment;
    private String note;
    private String phoneNumber;
}
